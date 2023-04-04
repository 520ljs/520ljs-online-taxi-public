package com.ss.servicemap.service;

import com.ss.internalcommon.constant.AmapConfigConstants;
import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.dto.DicDistrict;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicemap.mapper.DicDistrictMapper;
import com.ss.servicemap.remote.MapDicDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    private MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    public ResponseResult initDicDistrict(String keywords) {

        // 请求地图
        String dicDistrictRsult = mapDicDistrictClient.dicDistrict(keywords);
        System.out.println(dicDistrictRsult);

        // 解析结果
        JSONObject dicDistrictjsonObject = JSONObject.fromObject(dicDistrictRsult);
        int status = dicDistrictjsonObject.getInt(AmapConfigConstants.STATUS);
        // 最开始判断状态，如果status不等于1，返回错误
        if (status != 1) {
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(), CommonStatusEnum.MAP_DISTRICT_ERROR.getValue());
        }
        // 拿到districts的JSON数组
        JSONArray countryJsonArray = dicDistrictjsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
        for (int country = 0; country < countryJsonArray.size(); country++) {
            // 第一层 国家 从districts取
            JSONObject countryJsonObject = countryJsonArray.getJSONObject(country);
            // 获取addressCode
            String countryAddressCode = countryJsonObject.getString(AmapConfigConstants.ADCODE);
            // 获取addressName
            String countryAddressName = countryJsonObject.getString(AmapConfigConstants.NAME);
            // 父地区编码
            String countryParentAddressCode = "0";
            // 获取级别 0 country,1 province,2 city,3 district，来表示对应的级别
            String countryLevel = countryJsonObject.getString(AmapConfigConstants.LEVEL);

            // 插入数据库
            insertDicDistrict(countryAddressCode, countryAddressName, countryLevel, countryParentAddressCode);

            JSONArray provinceJsonArray = countryJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
            for (int p = 0; p < provinceJsonArray.size(); p++) {
                // 第二层
                JSONObject provinceJsonObject = provinceJsonArray.getJSONObject(p);
                String provinceAddressCode = provinceJsonObject.getString(AmapConfigConstants.ADCODE);
                String provinceAddressName = provinceJsonObject.getString(AmapConfigConstants.NAME);
                String provinceParentAddressCode = countryAddressCode;
                String provinceLevel = provinceJsonObject.getString(AmapConfigConstants.LEVEL);
                insertDicDistrict(provinceAddressCode, provinceAddressName, provinceLevel, provinceParentAddressCode);

                JSONArray cityJsonArray = provinceJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
                for (int city = 0; city < cityJsonArray.size(); city++) {
                    // 第三层
                    JSONObject cityJsonObject = cityJsonArray.getJSONObject(city);
                    String cityAddressCode = cityJsonObject.getString(AmapConfigConstants.ADCODE);
                    String cityAddressName = cityJsonObject.getString(AmapConfigConstants.NAME);
                    String cityParentAddressCode = provinceAddressCode;
                    String cityLevel = cityJsonObject.getString(AmapConfigConstants.LEVEL);
                    insertDicDistrict(cityAddressCode, cityAddressName, cityLevel, cityParentAddressCode);

                    JSONArray districtJsonArray = cityJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
                    for (int d = 0; d < districtJsonArray.size(); d++) {
                        // 第四层
                        JSONObject districtJsonObject = districtJsonArray.getJSONObject(d);
                        String districtAddressCode = districtJsonObject.getString(AmapConfigConstants.ADCODE);
                        String districtAddressName = districtJsonObject.getString(AmapConfigConstants.NAME);
                        String districtParentAddressCode = cityAddressCode;
                        String districtLevel = districtJsonObject.getString(AmapConfigConstants.LEVEL);

                        // 如果是街道，就不插入了
                        if (districtLevel.equals(AmapConfigConstants.STREET)) {
                            continue;
                        }

                        insertDicDistrict(districtAddressCode, districtAddressName, districtLevel, districtParentAddressCode);
                    }
                }
            }
        }

        return ResponseResult.success();
    }

    /**
     * 属性插入到数据库
     *
     * @param addressCode
     * @param addressName
     * @param level
     * @param parentAddressCode
     */
    public void insertDicDistrict(String addressCode, String addressName, String level, String parentAddressCode) {
        // 设置对应的属性，数据库对象
        DicDistrict district = new DicDistrict();
        district.setAddressCode(addressCode);
        district.setAddressName(addressName);
        int levelInt = generateLevel(level);// 转换级别
        district.setLevel(levelInt);
        district.setParentAddressCode(parentAddressCode);

        // 插入数据库
        dicDistrictMapper.insert(district);
    }

    /**
     * 等级转换
     *
     * @param level 国家-省份-城市-地区
     * @return
     */
    public int generateLevel(String level) {
        int levelInt = 0;
        if (level.trim().equals("country")) {
            levelInt = 0;
        } else if (level.trim().equals("province")) {
            levelInt = 1;
        } else if (level.trim().equals("city")) {
            levelInt = 2;
        } else if (level.trim().equals("district")) {
            levelInt = 3;
        }
        return levelInt;
    }

}
