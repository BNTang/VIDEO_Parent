/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_video.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author BNTang
 * @version S2.3.2Dev
 * @program video_parent
 * @date Created in 2021/4/10 14:36
 * @description 作品章节VO，作品章节信息
 **/
@Data
public class ChapterVO {
    private String id;
    private String title;

    /**
     * 章节下的小节信息
     */
    private List<ContentVideoVO> children;
}