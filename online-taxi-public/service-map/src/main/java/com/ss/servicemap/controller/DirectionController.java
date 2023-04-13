package com.ss.servicemap.controller;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.ForecastPriceDTO;
import com.ss.servicemap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: ljy.s
 * @Date: 2023/3/29 - 03 - 29 - 16:35
 */
@RestController
@RequestMapping("/direction")
public class DirectionController {

    @Resource
    private DirectionService directionService;

    @GetMapping("/driving")
    public ResponseResult driving(@RequestBody ForecastPriceDTO forecastPriceDTO) {

        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();

        return directionService.driving(depLongitude, depLatitude, destLongitude, destLatitude);
    }

}
