package cn.mapway.openapi.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data

public class EastTaskRequest {
    @ApiModelProperty(value = "代码编号",required = true,notes = "这里填写说明",example = "12345")
    Integer code;
    @ApiModelProperty(value = "风险项列表",required = false,notes = "从哪里获取这些值呢?",example = "['reg','green','blue']")
    List<String>  risks;
    @ApiModelProperty(value="最后通牒日期",required = true,notes = "给我的最后期限",example = "2021-03-04")
    Date  deadline;
    List<SpecialData> specialDataList;
}
