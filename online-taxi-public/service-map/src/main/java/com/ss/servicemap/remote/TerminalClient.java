package com.ss.servicemap.remote;

import com.ss.internalcommon.constant.AmapConfigConstants;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.TerminalResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/4 - 05 - 04 - 16:41
 */
@Service
public class TerminalClient {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 创建终端
     * @param name
     * @param desc
     * @return
     */
    public ResponseResult<TerminalResponse> add(String name , String desc){

        // &key=<用户的key>
        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.TERMINAL_ADD);
        url.append("?");
        url.append("key="+amapKey);
        url.append("&");
        url.append("sid="+amapSid);
        url.append("&");
        url.append("name="+name);
        url.append("&");
        url.append("desc="+desc);
        System.out.println("创建终端请求："+url.toString());
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        System.out.println("创建终端响应："+stringResponseEntity.getBody());
        /**
         * {
         *     "data": {
         *         "name": "车辆2",
         *         "tid": 583145283,
         *         "sid": 797498
         *     },
         *     "errcode": 10000,
         *     "errdetail": null,
         *     "errmsg": "OK"
         * }
         */
        String body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String tid = data.getString("tid");

        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);

        return  ResponseResult.success(terminalResponse);
    }
}
