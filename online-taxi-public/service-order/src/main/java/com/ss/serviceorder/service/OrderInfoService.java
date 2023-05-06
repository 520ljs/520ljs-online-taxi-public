package com.ss.serviceorder.service;

import com.ss.internalcommon.dto.OrderInfo;
import com.ss.serviceorder.mapper.OrderInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 520ljs
 * @since 2023-05-06
 */
@Service
public class OrderInfoService {

    @Resource
    OrderInfoMapper orderInfoMapper;

    /**
     * 测试添加数据
     *
     * @return
     */
    public String testMapper() {
        OrderInfo o = new OrderInfo();
        o.setAddress("110000");
        orderInfoMapper.insert(o);
        return "";
    }

}
