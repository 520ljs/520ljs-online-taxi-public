package com.ss.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.dto.PriceRule;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.ForecastPriceDTO;
import com.ss.internalcommon.response.DirectionResponse;
import com.ss.internalcommon.response.ForecastPriceResponse;
import com.ss.internalcommon.util.BigDecimalUtils;
import com.ss.serviceprice.mapper.PriceRuleMapper;
import com.ss.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ljy.s
 * @Date: 2023/3/29 - 03 - 29 - 15:25
 */
@Service
@Slf4j
public class ForecastPriceService {

    @Resource
    private ServiceMapClient serviceMapClient;

    @Resource
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult forecastPrice(String depLongitude,
                                        String depLatitude,
                                        String destLongitude,
                                        String destLatitude,
                                        String cityCode,
                                        String vehicleType) {

        log.info("出发地经度：" + depLongitude);
        log.info("出发地维度：" + depLatitude);
        log.info("目的地经度：" + destLongitude);
        log.info("目的地维度：" + destLatitude);

        log.info("调用地图服务，查询距离和时长");
        // 自己的参数自己来，每个接口参数单独封装一下
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        ResponseResult<DirectionResponse> direction = serviceMapClient.direction(forecastPriceDTO);
        Integer distance = direction.getData().getDistance();
        Integer duration = direction.getData().getDuration();

        log.info("距离：" + distance + "，时长：" + duration);

        log.info("读取计价规则");

        // 取最新版本的数据
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if (priceRules.size() == 0) {// 如果不存在，返回计价规则不存在错误
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        // 取出计价规则
        PriceRule priceRule = priceRules.get(0);
        log.info("priceRules：" + priceRules + "priceRule：" + priceRule);

        log.info("根据距离、时长和计价规则，计算价格");
        double price = getPrice(distance, duration, priceRule);

        // 返回响应内容
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        forecastPriceResponse.setCityCode(cityCode);
        forecastPriceResponse.setVehicleType(vehicleType);

        return ResponseResult.success(forecastPriceResponse);
    }

    /**
     * 根据距离、时长、和计价规则，计算最终价格
     *
     * @param distance  距离
     * @param duration  时长
     * @param priceRule 计价规则
     * @return 最终价格
     */
    public static double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        // BigDecimal   Java在java.math包中提供的API类BigDecimal，用来对超过16位有效位的数进行精确的运算。
        double price = 0;

        // 起步价
        double startFare = priceRule.getStartFare();
        BigDecimalUtils.add(price, startFare);

        // 里程费
        // 总里程 最初为 m
        // 总里程 转换为 km（数据库里单位是km     除1000   小数点2）   四舍五入
        double distanceMile = BigDecimalUtils.divide(distance, 1000);
        // 起步里程
        double startMile = (double) priceRule.getStartMile();
        // 总里程减去起步里程(里程的差值)    转换成double
        double distanceSubtract = BigDecimalUtils.subtract(distanceMile, startMile);
        // 最终收费里程数 km  如果小于0，就返回0，大于0，就返回它自己
        Double mile = distanceSubtract < 0 ? 0 : distanceSubtract;
        // 计程单价 元/km
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        // 里程价格（最终里程数*计程单价）
        double mileFare = BigDecimalUtils.multiply(mile, unitPricePerMile);
        price = BigDecimalUtils.add(price, mileFare);

        // 时长费
        // 时长分钟数    从秒转换为分钟（数据库里单位是分钟   除60    小数点2）
        double timeMinute = BigDecimalUtils.divide(duration, 60);
        // 计时单价（每分钟多少钱）
        Double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        // 时长费用（分钟数*计时单价）
        double timeFare = BigDecimalUtils.multiply(timeMinute, unitPricePerMinute);
        price = BigDecimalUtils.add(price, timeFare);

        BigDecimal priceBigDecimal = BigDecimal.valueOf(price);
        priceBigDecimal = priceBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);// 2位小数，四舍五入

        return priceBigDecimal.doubleValue();
    }

    public static void main(String[] args) {
        // city_code 110000, vehicle_type 1, start_fare 10, start_mile 3,
        // unit_price_per_mile 1.8, unit_price_per_minute
        PriceRule priceRule = new PriceRule();
        priceRule.setUnitPricePerMile(1.8);
        priceRule.setUnitPricePerMinute(0.5);
        priceRule.setStartFare(10.0);
        priceRule.setStartMile(3);

        System.out.println(getPrice(6500, 1800, priceRule));
    }
}
