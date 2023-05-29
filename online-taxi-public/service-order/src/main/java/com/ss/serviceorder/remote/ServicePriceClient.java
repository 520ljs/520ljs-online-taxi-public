package com.ss.serviceorder.remote;

import com.ss.internalcommon.dto.PriceRule;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.PriceRuleIsNewRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:ljy.s
 * @Date:2023/5/9 - 05 - 09 - 1:25
 */
@FeignClient("service-price")
public interface ServicePriceClient {

    @PostMapping("/price-rule/is-new")
    public ResponseResult<Boolean> isNew(@RequestBody PriceRuleIsNewRequest priceRuleIsNewRequest);

    @RequestMapping(method = RequestMethod.GET, value = "/price-rule/if-exists")
    public ResponseResult<Boolean> ifPriceExists(@RequestBody PriceRule priceRule);

}
