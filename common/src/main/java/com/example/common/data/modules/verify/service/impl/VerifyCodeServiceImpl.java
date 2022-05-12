package com.example.common.data.modules.verify.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.data.bo.VerifyCodeBo;
import com.example.common.data.dto.VerifyCodeDTO;
import com.example.common.data.entity.VerifyCodeEntity;
import com.example.common.data.modules.verify.dao.VerifyCodeDao;
import com.example.common.data.modules.verify.service.VerifyCodeService;
import com.example.common.data.service.impl.CrudServiceImpl;
import com.example.common.exception.ServerException;
import com.example.common.validator.AssertUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码
 *
 * @since 1.0.0 2020-07-06
 */
@Service
public class VerifyCodeServiceImpl extends CrudServiceImpl<VerifyCodeBo, VerifyCodeDao, VerifyCodeEntity, VerifyCodeDTO> implements VerifyCodeService {


    @Value("${appSettings.admin.hosturl: http://127.0.0.1:8080/admin}")
    private String hosturl;

    @Override
    public LambdaQueryWrapper<VerifyCodeEntity> getQueryWrapper(VerifyCodeBo params) {

        LambdaQueryWrapper<VerifyCodeEntity> wrapper = super.getQueryWrapper(params);

        return wrapper;
    }

    @Override
    @Transactional
    public void send(String loginName) {
        AssertUtils.isBlank(loginName);
        /* TODO 用户有邮箱*/
        // UserEntity user = null;//userService.getByEmail(loginName);
//        if (user == null) {
//            throw new MyException("邮箱不存在！");
//        }

        String code = createRandomVcode();
        //存储验证码信息
        VerifyCodeEntity vfyCode = new VerifyCodeEntity();
        vfyCode.setCreateDate(new Date());
        vfyCode.setCode(code);
        vfyCode.setState(0);
        vfyCode.setLoginName(loginName);
        insert(vfyCode);
        String param = JSONObject.toJSONString(vfyCode);
        Map map = new HashMap();
        map.put("id", 1L);
        map.put("mailTo", loginName);
        map.put("params", param);
//        hosturl = "http://localhost:8087/admin";
        String res = "";//HttpUtil.post(hosturl + "/sys/mailtemplate/send", map);
        JSONObject jsonObject = JSONObject.parseObject(res);
        int rescode = jsonObject.getIntValue("code");
        String msg = jsonObject.getString("msg");
        if (0 != rescode) {
            throw new ServerException(rescode, msg);
        }
    }

    public static String createRandomVcode() {
        //验证码
        StringBuilder vcode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            vcode.append((int) (Math.random() * 9));
        }
        return vcode.toString();
    }

    @Transactional(readOnly = false)
    public boolean checkCode(String code, String account) {
        VerifyCodeEntity validate = baseDao.selectOne(new LambdaQueryWrapper<VerifyCodeEntity>()
                .eq(VerifyCodeEntity::getLoginName, account)
                .eq(VerifyCodeEntity::getCode, code)
                .eq(VerifyCodeEntity::getState, "0"));

        if (validate != null) {
            long ok = validate.getCreateDate().getTime();
            long nk = System.currentTimeMillis();
            float ay = (nk - ok) / (300 * 1000);//300秒超时
            if (ay > 1f) {
                return false;
            }
            validate.setState(1);
            this.updateById(validate);
            return true;
        }
        return false;
    }
}
