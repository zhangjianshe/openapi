package cn.mapway.openapi.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("接口请求参数")
public class EastTaskRequest {
    @ApiModelProperty(value = "代码编号", required = true, notes = "这里填写说明", example = "12345")
    Integer code;
    @ApiModelProperty(value = "风险项列表", required = false, notes = "从哪里获取这些值呢?", example = "['reg','green','blue']")
    List<String> risks;
    @ApiModelProperty(value = "最后通牒日期", required = true, notes = "给我的最后期限", example = "2021-03-04")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date deadline;
    @ApiModelProperty(value = "特殊的数据")
    List<SpecialData> specialDataList;
}
