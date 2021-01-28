package cn.mapway.openapi.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * SpecialData
 *
 * @author zhangjianshe@gmail.com
 */
@ApiModel(description = "Specia数据结构")
@Data
public class SpecialData {

    @ApiModelProperty(value = "重量", notes = "必须是大象的重量 单位吨", example = "34.5")
    Double weight;

    @ApiModelProperty(value = "大象标签列表", notes = "随便填写", example = "big,small,large")
    List<String> tags;
}
