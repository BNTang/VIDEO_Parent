package top.it6666.service_video.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 创作者
 * </p>
 *
 * @author BNTang
 * @since 2021-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("video_author")
@ApiModel(value = "Author对象", description = "创作者")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创作者ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "创作者姓名")
    private String name;

    @ApiModelProperty(value = "创作者简介")
    private String intro;

    @ApiModelProperty(value = "创建者经历介绍")
    private String career;

    @ApiModelProperty(value = "1:普通创作者 2:特约创作者")
    private Integer level;

    @ApiModelProperty(value = "创作者头像")
    private String avatar;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}