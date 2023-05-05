package com.ss.servicemap.controller;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.TerminalResponse;
import com.ss.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:ljy.s
 * @Date:2023/5/4 - 05 - 04 - 16:39
 */
@RestController
@RequestMapping("/terminal")
public class TerminalController {

    @Resource
    private TerminalService terminalService;

    /**
     * 添加终端
     * @param name
     * @param desc
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<TerminalResponse> add(String name , String desc){
        return terminalService.add(name , desc);
    }

    /**
     * 终端搜索
     * @param center
     * @param radius
     * @return
     */
    @PostMapping("/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundsearch(String center , Integer radius){

        return terminalService.aroundsearch(center,radius);
    }

}
