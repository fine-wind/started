package com.example.started.modules.notice.service;

import com.example.started.modules.notice.bo.SysNoticeBo;
import com.example.started.modules.notice.dto.SysNoticeDTO;
import com.example.started.modules.notice.entity.SysNoticeEntity;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.CrudService;

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
