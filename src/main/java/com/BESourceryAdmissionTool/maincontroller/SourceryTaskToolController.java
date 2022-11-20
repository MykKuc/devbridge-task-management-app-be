package com.BESourceryAdmissionTool.maincontroller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SourceryTaskToolController {

    @GetMapping("/ok")
    @ApiOperation(value = "Returns an OK response 200")
    public String getOkResponse() {
        return "Ok Response";
    }
}
