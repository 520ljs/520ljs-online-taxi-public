package com.ss.servicemap.controller;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicemap.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/4/4 - 04 - 04 - 11:12
 */
@RestController
public class DicDistrictController {

    @Resource
    private DicDistrictService dicDistrictService;

    @GetMapping("/dic-district")
    public ResponseResult initDicDistrict(String keywords) {

        return dicDistrictService.initDicDistrict(keywords);
    }

}
