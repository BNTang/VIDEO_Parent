/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_video.entity.vo;

import lombok.Data;

/**
 * @author BNTang
 * @version S2.3.2Dev
 * @program video_parent
 * @date Created in 2021/4/11 12:50
 * @description 预览数据模型 VO
 **/
@Data
public class ContentPreviewVO {
    private String title;
    private String cover;
    private Integer contentNum;
    private String oneCategory;
    private String twoCategory;
    private String author;

    /**
     * 价格只用于显示所以为String
     */
    private String price;
}