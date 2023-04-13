package com.ss.servicemap.controller;

import com.ss.internalcommon.dto.DicDistrict;
import com.ss.servicemap.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ljy.s
 * @Date: 2023/3/29 - 03 - 29 - 16:19
 */
@RestController
public class TestController {

    @Resource
    DicDistrictMapper mapper;

    @GetMapping("/test")
    public String test() {
        return "service map";
    }


    @GetMapping("/test-map")
    public String testMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("address_code", "110000");
        List<DicDistrict> dicDistricts = mapper.selectByMap(map);
        System.out.println(dicDistricts);

        return "test-map";
    }

}
