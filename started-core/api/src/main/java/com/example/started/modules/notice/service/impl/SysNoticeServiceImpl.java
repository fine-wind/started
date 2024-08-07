package com.example.started.modules.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.started.auth.Se;
import com.example.started.modules.notice.bo.SysNoticeBo;
import com.example.started.modules.notice.dao.SysNoticeDao;
import com.example.started.modules.notice.dto.SysNoticeDTO;
import com.example.started.modules.notice.entity.SysNoticeEntity;
import com.example.started.modules.notice.entity.SysNoticeUserEntity;
import com.example.started.modules.notice.enums.NoticeReadStatusEnum;
import com.example.started.modules.notice.enums.NoticeStatusEnum;
import com.example.started.modules.notice.enums.ReceiverTypeEnum;
import com.example.started.modules.notice.service.SysNoticeUserService;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.CrudServiceImpl;
import com.example.common.v0.utils.ConvertUtils;
import com.example.started.modules.notice.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.common.v0.constant.Constant.TABLE.CREATE_DATE;

/**
 * 通知管理
 */
@Service
public class SysNoticeServiceImpl extends CrudServiceImpl<SysNoticeBo, SysNoticeDao, SysNoticeEntity, SysNoticeDTO> implements SysNoticeService {
    @Autowired
    private SysNoticeUserService sysNoticeUserService;
//    @Autowired
//    @Lazy
//    private UserServiceV1 sysUserService;

    // @Override
    public QueryWrapper<SysNoticeEntity> getWrapper(SysNoticeEntity params) {
        Integer type = params.getType();

        QueryWrapper<SysNoticeEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(type != null, "type", type);
        wrapper.orderByDesc(CREATE_DATE);
        return wrapper;
    }

    @Override
    public PageData<SysNoticeDTO> getNoticeUserPage(SysNoticeBo params) {
        //分页
        IPage<SysNoticeEntity> page = getPage(params, null, false);

        //查询
        Long id = params.getId();
        List<SysNoticeEntity> list = baseDao.getNoticeUserList(id);

        return getPageData(0, 0, page.getTotal(), list, SysNoticeDTO.class);
    }

    @Override
    public PageData<SysNoticeDTO> getMyNoticePage(SysNoticeBo params) {
        //分页
        IPage<SysNoticeEntity> page = getPage(params, null, false);

        //查询
        List<SysNoticeEntity> list = baseDao.getMyNoticeList(Se.getUserId().toString());

        return getPageData(0, 0, page.getTotal(), list, SysNoticeDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysNoticeDTO dto) {
        SysNoticeEntity entity = ConvertUtils.sourceToTarget(dto, SysNoticeEntity.class);

        //更新发送者信息
        if (dto.getStatus() == NoticeStatusEnum.SEND.value()) {
            entity.setSenderName(Se.getUserName());
            entity.setSenderDate(new Date());
        }

        baseDao.insert(entity);

        //发送通知
        dto.setId(entity.getId());
        sendNotice(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysNoticeDTO dto) {
        SysNoticeEntity entity = ConvertUtils.sourceToTarget(dto, SysNoticeEntity.class);

        //更新发送者信息
        if (dto.getStatus() == NoticeStatusEnum.SEND.value()) {
            entity.setSenderName(Se.getUserId().toString());
            entity.setSenderDate(new Date());
        }

        this.updateById(entity);

        //发送通知
        sendNotice(dto);
    }

    /**
     * 发送通知
     */
    public void sendNotice(SysNoticeDTO notice) {
        //如果是草稿，在不发送通知
        if (notice.getStatus() == NoticeStatusEnum.DRAFT.value()) {
            return;
        }

        //全部用户
        if (notice.getReceiverType() == ReceiverTypeEnum.ALL.value()) {
            //发送给全部用户
            sendAllUser(notice);

            //通过WebSocket，提示全部用户，有新通知
            // MessageData<String> message = new MessageData<String>().msg(notice.getTitle());
            // todo webSocketServer.sendMessageAll(message);

        } else {  //选中用户
            List<String> userIdList = new ArrayList<>();// todo sysUserService.getUserIdListByDeptId(notice.getReceiverTypeList());
            if (userIdList.size() == 0) {
                return;
            }

            //发送给选中用户
            sendUser(notice, userIdList);

            //通过WebSocket，提示选中用户，有新通知
            // MessageData<String> message = new MessageData<String>().msg(notice.getTitle());
            // todo webSocketServer.sendMessage(userIdList, message);
        }
    }

    /**
     * 发送给全部用户
     */
    public void sendAllUser(SysNoticeDTO notice) {
        SysNoticeUserEntity noticeUser = new SysNoticeUserEntity();
        noticeUser.setNoticeId(notice.getId());
        noticeUser.setReadStatus(NoticeReadStatusEnum.UNREAD.value());
        sysNoticeUserService.insertAllUser(noticeUser);
    }

    /**
     * 发送给选中用户
     */
    public void sendUser(SysNoticeDTO notice, List<String> userIdList) {
        List<SysNoticeUserEntity> list = new ArrayList<>();
        userIdList.forEach(userId -> {
            SysNoticeUserEntity noticeUser = new SysNoticeUserEntity();
            noticeUser.setNoticeId(notice.getId());
            noticeUser.setReceiverId(userId);
            noticeUser.setReadStatus(NoticeReadStatusEnum.UNREAD.value());
            list.add(noticeUser);
        });
        sysNoticeUserService.insertBatch(list);
    }

}
