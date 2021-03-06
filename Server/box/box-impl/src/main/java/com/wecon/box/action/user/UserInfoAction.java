package com.wecon.box.action.user;

import com.alibaba.fastjson.JSONObject;
import com.wecon.box.entity.Account;
import com.wecon.restful.annotation.WebApi;
import com.wecon.restful.core.AppContext;
import com.wecon.restful.core.Output;
import com.wecon.restful.doc.Label;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zengzhipeng on 2017/8/4.
 */
@Label("查询账号信息")
@RestController
public class UserInfoAction extends UserBaseAction {
    @RequestMapping("user/userinfo")
    @WebApi(forceAuth = true, master = true)
    public Output getUserInfo() {
        JSONObject data = new JSONObject();
        data.put("username", AppContext.getSession().client.account);
//        data.put("uid", AppContext.getSession().client.userId);
        return new Output(data);
    }

    @RequestMapping("user/userinfod")
    @WebApi(forceAuth = true, master = true)
    public Output getUserInfoDetail() {
        Account userInfo=accountApi.getAccount( AppContext.getSession().client.userId);
        JSONObject data = new JSONObject();
        data.put("userInfo", userInfo);
        return new Output(data);
    }
}