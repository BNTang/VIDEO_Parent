/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_video.entity.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BNTang
 * @version S2.3.2Dev
 * @program video_parent
 * @date Created in 2021/4/4 11:53
 * @description 分类管理列表树形数据类模型
 **/
@Data
public class LevelCategory {
    private String id;
    private String title;
    private List<LevelCategory> children = new ArrayList<>();
}