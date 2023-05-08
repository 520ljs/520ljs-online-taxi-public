package com.ss.serviceorder.service;

import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.constant.OrderConstants;
import com.ss.internalcommon.dto.OrderInfo;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.OrderRequest;
import com.ss.serviceorder.mapper.OrderInfoMapper;
import com.ss.serviceorder.remote.ServicePriceClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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

    @Resource
    ServicePriceClient servicePriceClient;

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

    /**
     * 创建订单
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult add(OrderRequest orderRequest) {

        // 需要判断计价规则的版本是否为最新
        ResponseResult<Boolean> aNew = servicePriceClient.isNew(orderRequest.getFareType(), orderRequest.getFareVersion());
        if (!aNew.getData()) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(), CommonStatusEnum.PRICE_RULE_CHANGED.getValue(), "");
        }

        // 创建订单
        OrderInfo orderInfo = new OrderInfo();
        // 请求参数,实体类
        BeanUtils.copyProperties(orderRequest, orderInfo);
        // 设置状态
        orderInfo.setOrderStatus(OrderConstants.ORDER_START);
        // 设置时间
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        orderInfoMapper.insert(orderInfo);

        return ResponseResult.success("");
    }

}
