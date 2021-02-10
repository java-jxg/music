package com.fastcase.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 歌手
 * </p>
 *
 * @author Reg
 * @since 2021-01-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("singer")
@ApiModel(value="Singer对象", description="歌手")
public class Singer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别（0女1男2组合3不明）")
    private Integer sex;

    @ApiModelProperty(value = "头像")
    private String pic;

    @ApiModelProperty(value = "生日")
    private Date birth;

    @ApiModelProperty(value = "地区")
    private String location;

    @ApiModelProperty(value = "简介")
    private String introduction;


}
