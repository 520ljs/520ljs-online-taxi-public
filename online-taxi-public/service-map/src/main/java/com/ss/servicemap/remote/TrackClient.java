package com.ss.servicemap.remote;

import com.ss.internalcommon.constant.AmapConfigConstants;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.TrackResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/4 - 05 - 04 - 22:40
 */
@Service
@Slf4j
public class TrackClient {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 创建轨迹
     * @param tid
     * @return
     */
    public ResponseResult<TrackResponse> add(String tid){

        // &key=<用户的key>
        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.TRACK_ADD);
        url.append("?");
        url.append("key="+amapKey);
        url.append("&");
        url.append("sid="+amapSid);
        url.append("&");
        url.append("tid="+tid);

        log.info("高德地图创建轨迹请求："+url);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        String body = stringResponseEntity.getBody();

        log.info("高德地图创建轨迹响应："+body);

        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        // 轨迹id
        String trid = data.getString("trid");
        // 轨迹名称
        String trname = "";
        if (data.has("trname")){
            trname = data.getString("trname");
        }

        TrackResponse trackResponse = new TrackResponse();
        trackResponse.setTrid(trid);
        trackResponse.setTrname(trname);


        return ResponseResult.success(trackResponse);
    }
}
