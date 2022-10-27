package com.example.modules.notice.service;

import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.CrudService;
import com.example.modules.notice.bo.SysNoticeBo;
import com.example.modules.notice.dto.SysNoticeDTO;
import com.example.modules.notice.entity.SysNoticeEntity;

/**
 * 通知管理
 */
public interface SysNoticeService extends CrudService<SysNoticeBo, SysNoticeEntity, SysNoticeDTO> {

    /**
     * 获取被通知的用户
     */
    PageData<SysNoticeDTO> getNoticeUserPage(SysNoticeBo params);

    /**
     * 获取我的通知列表
     */
    PageData<SysNoticeDTO> getMyNoticePage(SysNoticeBo params);
}
