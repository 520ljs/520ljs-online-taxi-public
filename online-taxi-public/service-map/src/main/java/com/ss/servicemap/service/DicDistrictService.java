package com.ss.servicemap.service;

import com.ss.internalcommon.constant.AmapConfigConstants;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicemap.remote.MapDicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author:ljy.s
 * @Date:2023/4/4 - 04 - 04 - 11:14
 */
@Service
public class DicDistrictService {

    @Autowired
    public MapDicDistrictClient mapDicDistrictClient;

    public ResponseResult initDicDistrict(String keywords) {

        // 请求地图
        String dicDistrict = mapDicDistrictClient.dicDistrict(keywords);
        System.out.println(dicDistrict);

        // 解析结果

        // 插入数据库

        return ResponseResult.success();
    }

}
