/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_video.entity.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author BNTang
 * @version S2.3.2Dev
 * @program video_parent
 * @date Created in 2021/4/10 14:37
 * @description 小节信息VO
 **/
@Data
public class ContentVideoVO implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 小节标题
     */
    private String title;

    /**
     * 小节是否免费
     */
    private Integer isFree;

    /**
     * 视频sourcedId
     */
    private String videoSourceId;

    /**
     * 期间
     */
    private Float duration;
}