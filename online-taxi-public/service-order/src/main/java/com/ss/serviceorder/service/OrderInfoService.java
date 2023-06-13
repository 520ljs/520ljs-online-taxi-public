package com.ss.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.constant.OrderConstants;
import com.ss.internalcommon.dto.Car;
import com.ss.internalcommon.dto.OrderInfo;
import com.ss.internalcommon.dto.PriceRule;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.OrderRequest;
import com.ss.internalcommon.request.PriceRuleIsNewRequest;
import com.ss.internalcommon.response.OrderDriverResponse;
import com.ss.internalcommon.response.TerminalResponse;
import com.ss.internalcommon.util.RedisPrefixUtils;
import com.ss.serviceorder.mapper.OrderInfoMapper;
import com.ss.serviceorder.remote.ServiceDriverUserClient;
import com.ss.serviceorder.remote.ServiceMapClient;
import com.ss.serviceorder.remote.ServicePriceClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 520ljs
 * @since 2023-05-06
 */
@Service
@Slf4j
public class OrderInfoService {

    @Resource
    OrderInfoMapper orderInfoMapper;

    @Resource
    ServicePriceClient servicePriceClient;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    ServiceDriverUserClient serviceDriverUserClient;

    @Resource
    ServiceMapClient serviceMapClient;

    /**
     * 测试添加数据
     *
     * @return
     */
    public String testMapper() {
        OrderInfo o = new OrderInfo();
        o.setAddress("110000");
        orderInfoMapper.insert(o);
        return "";
    }

    /**
     * 创建订单
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult add(OrderRequest orderRequest) {

        // 测试当前城市是否有可用的司机
        ResponseResult<Boolean> availableDriver = serviceDriverUserClient.isAvailableDriver(orderRequest.getAddress());
        log.info("测试城市是否有司机结果：" + availableDriver.getData());
        if (!availableDriver.getData()) {
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_EMPTY.getCode(), CommonStatusEnum.CITY_DRIVER_EMPTY.getValue(), "");
        }

        // 需要判断计价规则的版本是否为最新
        PriceRuleIsNewRequest priceRuleIsNewRequest = new PriceRuleIsNewRequest();
        priceRuleIsNewRequest.setFareType(orderRequest.getFareType());
        priceRuleIsNewRequest.setFareVersion(orderRequest.getFareVersion());
        ResponseResult<Boolean> aNew = servicePriceClient.isNew(priceRuleIsNewRequest);
        if (!aNew.getData()) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(), CommonStatusEnum.PRICE_RULE_CHANGED.getValue(), "");
        }

        // 需要判断 下单的设备是否是 黑名单设备
        if (isBlackDevice(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getValue(), "");
        }

        // 判断：下单的城市和计价规则是否正常
        if (!isPriceRuleExists(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getCode(), CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getValue(), "");
        }

        // 判断乘客 是否有进行中的订单
        if (isPassengerOrderGoing(orderRequest.getPassengerId()) > 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(), CommonStatusEnum.ORDER_GOING_ON.getValue(), "");
        }

        // 创建订单
        OrderInfo orderInfo = new OrderInfo();
        // 请求参数,实体类
        BeanUtils.copyProperties(orderRequest, orderInfo);
        // 设置状态
        orderInfo.setOrderStatus(OrderConstants.ORDER_START);
        // 设置时间
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        orderInfoMapper.insert(orderInfo);

        // 派单
        dispatchRealTimeOrder(orderInfo);

        return ResponseResult.success("");
    }

    /**
     * 判断乘客 是否有正在进行中的订单
     *
     * @param passengerId
     * @return
     */
    private int isPassengerOrderGoing(Long passengerId) {
        // 判断有正在进行的订单不允许下单
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id", passengerId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstants.ORDER_START)
                .or().eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.PASSENGER_GETOFF)
                .or().eq("order_status", OrderConstants.TO_START_PAY)
        );

        Integer validOrderNumber = orderInfoMapper.selectCount(queryWrapper);

        return validOrderNumber;
    }

    /**
     * 判断司机 是否有正在进行中的订单
     *
     * @param driverId
     * @return
     */
    private int isDriverOrderGoing(Long driverId) {
        // 判断 司机 有正在进行的订单不允许下单
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverId);
        queryWrapper.and(wrapper -> wrapper
                .eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)

        );

        Integer validOrderNumber = orderInfoMapper.selectCount(queryWrapper);
        log.info("司机的Id：" + driverId + "，正在进行的订单的数量：" + validOrderNumber);

        return validOrderNumber;
    }

    /**
     * 是否是黑名单
     *
     * @param orderRequest
     * @return
     */
    private boolean isBlackDevice(OrderRequest orderRequest) {
        String deviceCode = orderRequest.getDeviceCode();
        // 生成key
        String deviceCodeKey = RedisPrefixUtils.blackDeviceCodePrefix + deviceCode;
        Boolean aBoolean = stringRedisTemplate.hasKey(deviceCodeKey);
        if (aBoolean) {
            String s = stringRedisTemplate.opsForValue().get(deviceCodeKey);
            int i = Integer.parseInt(s);
            if (i >= 2) {
                // 当前设备超过下单次数
                return true;
            } else {
                stringRedisTemplate.opsForValue().increment(deviceCodeKey);
            }
        } else {
            stringRedisTemplate.opsForValue().setIfAbsent(deviceCodeKey, "1", 1L, TimeUnit.HOURS);
        }
        return false;
    }

    /**
     * 计价规则是否存在
     *
     * @param orderRequest
     * @return
     */
    private boolean isPriceRuleExists(OrderRequest orderRequest) {
        String fareType = orderRequest.getFareType();
        int index = fareType.indexOf("$");
        String cityCode = fareType.substring(0, index);
        String vehicleType = fareType.substring(index + 1);

        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);

        ResponseResult<Boolean> booleanResponseResult = servicePriceClient.ifPriceExists(priceRule);

        return booleanResponseResult.getData();
    }

    /**
     * 实时订单派单逻辑
     *
     * @param orderInfo
     */
    public synchronized void dispatchRealTimeOrder(OrderInfo orderInfo) {

        // 2km
        String depLatitude = orderInfo.getDepLatitude();
        String depLongitude = orderInfo.getDepLongitude();
        // 轨迹
        String center = depLatitude + "," + depLongitude;

        List<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);

        // 搜索结果
        ResponseResult<List<TerminalResponse>> listResponseResult = null;
        radius:
        for (int i = 0; i < radiusList.size(); i++) {
            Integer radius = radiusList.get(i);
            listResponseResult = serviceMapClient.terminalAroundSearch(center, radius);

            log.info("在半径为" + radius + "的范围内，寻找车辆,结果：" + JSONArray.fromObject(listResponseResult.getData()).toString());

            // 获得终端     [{carId":"1654466519742111745","tid":683060559}]

            // 解析终端
            List<TerminalResponse> data = listResponseResult.getData();
            for (int j = 0; j < data.size(); j++) {
                TerminalResponse terminalResponse = data.get(j);
                Long carId = terminalResponse.getCarId();
                log.info("carId：" + carId);

                // 经纬度
                String longitude = terminalResponse.getLongitude();
                String latitude = terminalResponse.getLatitude();

                // 查询是否有对应的可派单司机
                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                if (availableDriver.getCode() == CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode()) {
                    log.info("没有车辆ID为：" + carId + "对应的司机");
                    continue radius;
                } else {
                    log.info("找到了正在出车的司机，它的车辆ID为：" + carId);

                    // 获取需要的数据
                    OrderDriverResponse orderDriverResponse = availableDriver.getData();
                    Long driverId = orderDriverResponse.getDriverId();
                    String driverPhone = orderDriverResponse.getDriverPhone();
                    String licenseId = orderDriverResponse.getLicenseId();
                    String vehicleNo = orderDriverResponse.getVehicleNo();
                    // String vehicleTypeFromCar = orderDriverResponse.getVehicleType();

                    // 判断司机 是否有进行中的订单
                    if (isDriverOrderGoing(driverId) > 0) {
                        continue;
                    }

                    // 订单直接匹配司机
                    // 查询当前车辆信息
                    QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
                    carQueryWrapper.eq("id", carId);

                    // 设置订单中和司机车辆相关的信息
                    orderInfo.setDriverId(driverId);
                    orderInfo.setDriverPhone(driverPhone);
                    orderInfo.setCarId(carId);
                    // 从地图中得到的经纬度
                    orderInfo.setReceiveOrderCarLongitude(longitude);
                    orderInfo.setReceiveOrderCarLatitude(latitude);

                    orderInfo.setReceiveOrderTime(LocalDateTime.now());
                    orderInfo.setLicenseId(licenseId);
                    orderInfo.setVehicleNo(vehicleNo);
                    orderInfo.setOrderStatus(OrderConstants.DRIVER_RECEIVE_ORDER);

                    orderInfoMapper.updateById(orderInfo);

                    break radius;
                }

            }

            // 根据解析出来的终端，查询车辆信息

            // 找到符合的车辆，进行派单

            // 如果派单成功，则退出循环
        }

    }
}
