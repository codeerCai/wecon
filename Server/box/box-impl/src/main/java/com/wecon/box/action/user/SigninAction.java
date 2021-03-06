package com.wecon.box.action.user;

import com.alibaba.fastjson.JSONObject;
import com.wecon.box.constant.ConstKey;
import com.wecon.box.entity.Account;
import com.wecon.box.enums.ErrorCodeOption;
import com.wecon.box.util.CheckUtil;
import com.wecon.restful.annotation.WebApi;
import com.wecon.restful.core.AppContext;
import com.wecon.restful.core.BusinessException;
import com.wecon.restful.core.Client;
import com.wecon.restful.core.Output;
import com.wecon.restful.doc.Label;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by zengzhipeng on 2017/8/2.
 */
@Label("登录")
@RestController
public class SigninAction extends UserBaseAction {
    @RequestMapping("user/signin")
    @WebApi(forceAuth = false, master = true)
    public Output signin(@Valid SigninParam param) {
        Client client = AppContext.getSession().client;

        Account user = accountApi.getAccount(param.alias);
        if (user == null) {
            throw new BusinessException(ErrorCodeOption.Login_NotAccount.key, ErrorCodeOption.Login_NotAccount.value);
        }
        String pwd = DigestUtils.md5Hex(param.password + user.secret_key);
        if (!pwd.equals(user.password)) {
            throw new BusinessException(ErrorCodeOption.Login_PwdError.key, ErrorCodeOption.Login_PwdError.value);
        }

        int expire = ConstKey.SESSION_EXPIRE_TIME;
        if (param.isremeber == 1) {
            expire = 3600 * 24 * 30;
        }
        String sid = accountApi.createSession(user, client.appid, client.fuid, client.ip, client.timestamp, expire);
        JSONObject data = new JSONObject();
        data.put("sid", sid);
//        data.put("uid", user.account_id);
        return new Output(data);
    }
}

class SigninParam {
    @Label("用户名、邮箱、电话")
    @NotNull
    @Length(max = 32, min = 1)
    public String alias;
    @Label("密码md5")
    @NotNull
    @Length(max = 32, min = 32)
    public String password;
    @Label("是否记住密码")
    @NotNull
    @Range(min = 0, max = 1)
    public Integer isremeber;

    public void setIsremeber(Integer isremeber) {
        this.isremeber = isremeber;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
