package com.ss.servicemap.remote;

import com.ss.internalcommon.constant.AmapConfigConstants;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.ServiceResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/4 - 05 - 04 - 15:36
 */
@Service
public class ServiceClient {

    @Value("${amap.key}")
    private String amapKey;

    @Resource
    private RestTemplate restTemplate;

    public ResponseResult add(String name) {
        // &key=<用户的key>
        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.SERVICE_ADD_URL);
        url.append("?");
        url.append("key=" + amapKey);
        url.append("&");
        url.append("name=" + name);

        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        String body = forEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String sid = data.getString("sid");
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setSid(sid);

        return ResponseResult.success(serviceResponse);
    }

}
