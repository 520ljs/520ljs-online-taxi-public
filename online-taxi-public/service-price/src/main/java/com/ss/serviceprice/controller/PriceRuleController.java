package com.ss.serviceprice.controller;


import com.ss.internalcommon.dto.PriceRule;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 520ljs
 * @since 2023-05-08
 */
@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {

    @Resource
    PriceRuleService priceRuleService;

    /**
     * 添加计价规则
     *
     * @param priceRule
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRule priceRule) {

        return priceRuleService.add(priceRule);
    }

    /**
     * 修改计价规则
     *
     * @param priceRule
     * @return
     */
    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody PriceRule priceRule) {

        return priceRuleService.edit(priceRule);
    }

    /**
     * 查询最新的计价规则
     *
     * @param fareType
     * @return
     */
    @GetMapping("/get-newest-version")
    public ResponseResult<PriceRule> getNewestVersion(@RequestParam String fareType) {

        return priceRuleService.getNewestVersion(fareType);
    }

    /**
     * 判断规则是否是最新
     *
     * @param fareType
     * @param fareVersion
     * @return
     */
    @GetMapping("/is-new")
    public ResponseResult<Boolean> isNew(@RequestParam String fareType, @RequestParam Integer fareVersion) {

        return priceRuleService.isNew(fareType, fareVersion);
    }

    /**
     * 判断该城市和对应车型的计价规则是否存在
     *
     * @param priceRule
     * @return
     */
    @PostMapping("/if-exists")
    public ResponseResult<Boolean> ifExists(@RequestBody PriceRule priceRule) {

        return priceRuleService.ifExists(priceRule);
    }

}
