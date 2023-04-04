package com.ss.servicemap.remote;

import com.ss.internalcommon.constant.AmapConfigConstants;
import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author:ljy.s
 * @Date:2023/4/4 - 04 - 04 - 14:36
 */
@Service
public class MapDicDistrictClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    public String dicDistrict(String keywords) {
        // https://restapi.amap.com/v3/config/district?
        // keywords=中国&
        // subdistrict=3&
        // key=<用户的key>
        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.DISTRICT_URL);
        url.append("?");
        url.append("keywords=" + keywords);
        url.append("&");
        url.append("subdistrict=3");
        url.append("&");
        url.append("key=" + amapKey);

        // getForEntity的第一个参数为要调用的服务的地址，这里调用了服务提供者提供的/hello接口
        // 注意这里是通过服务名调用而不是服务地址，如果写成服务地址就没法实现客户端负载均衡了。
        // getForEntity第二个参数String.class表示希望返回的body类型是String类型
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);

        return forEntity.getBody();
    }

}
