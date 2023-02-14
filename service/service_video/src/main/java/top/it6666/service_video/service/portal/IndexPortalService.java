/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_video.service.portal;

/**
 * @author BNTang
 **/
public interface IndexPortalService {
    /**
     * 获取作品
     *
     * @return 作品列表
     */
    Object getContentIndexList();

    /**
     * 获取创作者
     *
     * @return 创作者
     */
    Object getAuthorIndexList();

    /**
     * 获取作品分类
     *
     * @return 分类
     */
    Object getCategoryIndexList();
}