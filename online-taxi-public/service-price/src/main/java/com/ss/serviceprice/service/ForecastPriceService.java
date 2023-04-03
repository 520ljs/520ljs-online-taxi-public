package com.ss.serviceprice.service;

import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.dto.PriceRule;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.ForecastPriceDTO;
import com.ss.internalcommon.response.DirectionResponse;
import com.ss.internalcommon.response.ForecastPriceResponse;
import com.ss.serviceprice.mapper.PriceRuleMapper;
import com.ss.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

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
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("city_code", "110000");
        queryMap.put("vehicle_type", "1");
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(queryMap);
        if (priceRules.size() == 0) {// 如果不存在，返回计价规则不存在错误
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        // 取出计价规则
        PriceRule priceRule = priceRules.get(0);
        log.info("priceRules：" + priceRules + "priceRule：" + priceRule);

        log.info("根据距离、时长和计价规则，计算价格");

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.34);
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
    public double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        // BigDecimal   Java在java.math包中提供的API类BigDecimal，用来对超过16位有效位的数进行精确的运算。
        BigDecimal price = new BigDecimal(0);

        // 起步价
        Double startFare = priceRule.getStartFare();
        BigDecimal startFareDecimal = new BigDecimal(startFare);
        price = price.add(startFareDecimal);

        // 里程费
        // 总里程 最初为 m
        BigDecimal distanceDecimal = new BigDecimal(distance);
        // 总里程 转换为 km   四舍五入
        BigDecimal distanceMileDecimal = distanceDecimal.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
        // 起步里程
        Integer startMile = priceRule.getStartMile();
        BigDecimal startMileDecimal = new BigDecimal(startMile);
        // 总里程减去起步里程(里程的差值)    转换成double
        double distanceSubtract = distanceMileDecimal.subtract(startMileDecimal).doubleValue();
        // 最终收费里程数 km  如果小于0，就返回0，大于0，就返回它自己
        Double mile = distanceSubtract < 0 ? 0 : distanceSubtract;
        BigDecimal mileDecimal = new BigDecimal(mile);
        // 计程单价 元/km
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        BigDecimal unitPricePerMileDecimal = new BigDecimal(unitPricePerMile);
        // 里程价格
        BigDecimal mileFare = mileDecimal.multiply(unitPricePerMileDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
        price = price.add(mileFare);

        // 时长费

        return 0;
    }

}
