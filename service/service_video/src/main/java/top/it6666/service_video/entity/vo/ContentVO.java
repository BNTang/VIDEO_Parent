package top.it6666.service_video.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author BNTang
 */
@Data
public class ContentVO {
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 二级分类ID
     */
    private String categoryId;

    /**
     * 一级分类ID
     */
    private String categoryParentId;

    /**
     * 作者id
     */
    private String authorId;

    /**
     * 总视频数
     */
    private Integer contentNum;

    /**
     * 简介
     */
    private String description;

    /**
     * 封面
     */
    private String cover;

    /**
     * 价格
     */
    private BigDecimal price;
}