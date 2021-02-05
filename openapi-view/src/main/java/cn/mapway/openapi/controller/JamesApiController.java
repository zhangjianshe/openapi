package cn.mapway.openapi.controller;

import cn.mapway.openapi.controller.model.EastTaskRequest;
import cn.mapway.openapi.controller.model.EastTaskResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Api("Cont肉哦了人 的注解")
@Controller
public class JamesApiController {

    @ApiOperation(value = "寻找任务", tags = {"/人民/卫生/出版社", "不可能"},
            extensions = {
                    @Extension(name = "ziroom", properties = {
                            @ExtensionProperty(name = "author", value = "张建设")
                    })
            },
            notes = "感谢一年来各位领导和全体老师对学校德育处工作的支持。最后祝各位领导和老师们身体健康,阖家幸福, 万事如意，并预祝您能度过一个平安、快乐、文明、祥和、有意义的春节和假期。\n")
    @RequestMapping(value = "/tasks/find", method = RequestMethod.POST)
    @ResponseBody
    EastTaskResponse taskFind(@RequestBody EastTaskRequest request) {
        return new EastTaskResponse();
    }


    @ApiOperation(value = "太空行走", tags = {"/人民/卫生/出版社"},
            notes = "Unique string used to identify the operation.")
    @RequestMapping(value = "/tasks/find3/{code}", method = RequestMethod.POST)
    @ResponseBody
    EastTaskRequest taskFind3(@PathVariable("code") String code,
                              @RequestParam(value = "intValues") Integer[] intValues,
                              @RequestParam(value = "count", required = true) Integer count,
                              @RequestHeader(value = "X-ZIROOM_TOKEN") String ziroomToken,
                              @RequestBody EastTaskRequest request
    ) {
        return new EastTaskRequest();
    }


    @ApiOperation(value = "来各位领导和全体老师对学校德育", tags = {"不可能"},
            notes = "感谢一年来各位领导和全体老师对学校德育处工作的支持。最后祝各位领导和老师们身体健康,阖家幸福, 万事如意，并预祝您能度过一个平安、快乐、文明、祥和、有意义的春节和假期。\n")
    @RequestMapping(value = "/tasks/find2/{code}/{age}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    EastTaskResponse taskFind2(
            @PathVariable("code") String code,
            @PathVariable("age") Integer age
    ) {
        EastTaskResponse eastTaskResponse = new EastTaskResponse();
        eastTaskResponse.setName(code);
        return eastTaskResponse;
    }
}
