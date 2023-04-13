package com.ss.servicemap.remote;

import com.ss.internalcommon.constant.AmapConfigConstants;
import com.ss.internalcommon.response.DirectionResponse;
import javafx.util.Builder;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author: ljy.s
 * @Date: 2023/3/29 - 03 - 29 - 17:51
 */
@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String amapKey;

    @Resource
    private RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        // 组装请求调用url
        /*
         * https://restapi.amap.com/v3/direction/driving?
         * origin=116.481028,39.989643&
         * destination=116.465302,40.004717&
         * extensions=all&   all改为base
         * output=json&
         * key=cd43a1c199b9c868b12353f874ca50d6
         * */
        StringBuilder urlBuild = new StringBuilder();
        urlBuild.append(AmapConfigConstants.DIRECTION_URL);
        urlBuild.append("?");
        urlBuild.append("origin=" + depLongitude + "," + depLatitude);
        urlBuild.append("&");
        urlBuild.append("destination=" + destLongitude + "," + destLatitude);
        urlBuild.append("&");
        urlBuild.append("extensions=base");
        urlBuild.append("&");
        urlBuild.append("output=json");
        urlBuild.append("&");
        urlBuild.append("key=" + amapKey);
        log.info("urlBuild：" + urlBuild.toString());

        // 调用高德接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuild.toString(), String.class);
        String directionString = directionEntity.getBody();
        log.info("高德地图，路径规划，返回信息：" + directionString);

        // 解析接口
        DirectionResponse directionResponse = parseDirectionEntity(directionString);

        return directionResponse;
    }

    public DirectionResponse parseDirectionEntity(String directionString) {
        // 定义结果，初始null
        DirectionResponse directionResponse = null;

        try {
            // 最外层 转成JSON
            JSONObject result = JSONObject.fromObject(directionString);
            if (result.has(AmapConfigConstants.STATUS)) {// 如果有status
                int status = result.getInt(AmapConfigConstants.STATUS);// 提取状态
                if (status == 1) {// 0：请求失败，1：请求成功
                    if (result.has(AmapConfigConstants.ROUTE)) {// 如果有route
                        JSONObject routeObject = result.getJSONObject(AmapConfigConstants.ROUTE);// 得到route json
                        JSONArray pathsArray = routeObject.getJSONArray(AmapConfigConstants.PATHS);// 得到paths 数组
                        JSONObject pathObject = pathsArray.getJSONObject(0);// 取第一个,以速度优先来做判断
                        // 解析出distance和duration,放到返回值里
                        directionResponse = new DirectionResponse();
                        if (pathObject.has(AmapConfigConstants.DISTANCE)) {
                            int distance = pathObject.getInt(AmapConfigConstants.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if (pathObject.has(AmapConfigConstants.DURATION)) {
                            int duration = pathObject.getInt(AmapConfigConstants.DURATION);
                            directionResponse.setDuration(duration);
                        }
                        //return directionResponse;
                    }
                }
            }
        } catch (Exception e) {
        }
        return directionResponse;
    }

}
