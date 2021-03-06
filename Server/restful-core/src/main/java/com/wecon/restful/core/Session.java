package com.wecon.restful.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wecon.restful.authotiry.RightsHelper;
import com.wecon.restful.test.TestBase;

/**
 * 请求会话
 *
 * @author sean
 */
public class Session {
    private static final Logger logger = LogManager.getLogger(Session.class);

    /**
     * http协议访问对象
     */
    public HttpServletRequest request;
    public HttpServletResponse response;

    protected Map<String, String[]> params = new HashMap<>();

    /**
     * 读写分离是否访问master
     */
    public boolean master = true;

    /**
     * 客户端认证信息
     */
    public Client client;

    public Session() {
    }

    public Session(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.request = request;
        this.response = response;

        // 该参数在spring mock
        String test = request.getServletContext().getInitParameter("offline.test");

        // 非离线本地单元测试
        if (!"true".equals(test)) {
            this.fillParam();

            client = new Client();
            client.init(this);

            String baseUrl = request.getRequestURI();
            int begin = baseUrl.indexOf("/api/");
            baseUrl = baseUrl.substring(begin + 4);
            logger.debug("request url = " + baseUrl);

            String[] tmp = baseUrl.split("/");
            String url = Arrays.toString(tmp).replace(",", "/");
            url = url.replace(" ", "").replace("[", "").replace("]", "");
            WebApiInstance api = AppContext.getWebApi(url);
            if (api != null) {
                // 该参数在web.xml
                String skipSignWeb = request.getServletContext().getInitParameter("sign.skip");
                boolean skipSign = false;
                if ("true".equals(skipSignWeb) || api.skipSign) {
                    skipSign = true;
                }
                String customSignKeyName = Config.getCustomSignKeyNameFrom() == null ? null : this.getString(Config.getCustomSignKeyNameFrom());
                this.auth(client, api, skipSign, Config.getSignKeyV1(customSignKeyName));

                // 读写分离标记
                this.master = api.master;
            } else {
                throw new RuntimeException("restful api not exists");
            }
        }
        // 离线本地单元测试， 创建client
        else {
            this.client = TestBase.client;
            if (client.reqid == null) {
                client.reqid = System.currentTimeMillis() + "";
            }
            request.setAttribute("reqid", this.client.reqid);
        }
    }

    /**
     * 填充参数
     */
    private void fillParam() {
        // 读取header参数
        String common = request.getHeader("common");
        if (!StringUtils.isEmpty(common)) {
            JSONObject jo = JSON.parseObject(common);
            for (String key : jo.keySet()) {
                params.put(key, new String[]{jo.getString(key)});
            }
        }

        // 读取请求参数
        params.putAll(request.getParameterMap());

        // 删除表单数据
        String contentType = request.getContentType();
        if (contentType != null) {
            contentType = contentType.toLowerCase();
            if (contentType.contains("multipart/form-data")) {
                try {
                    Collection<Part> parts = request.getParts();
                    if (parts != null && !parts.isEmpty()) {
                        for (Part it : parts) {
                            params.remove(it.getName());
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 客户端认证
     *
     * @param client
     * @param api
     * @param skipSign
     * @param key
     * @throws IOException
     */
    private void auth(Client client, WebApiInstance api, boolean skipSign, String key) throws IOException {
        // 用户校验
        if (api.forceAuth) {
            if (client.userId <= 0) {
                throw new SessionInvalidException("session timeout");
            }
            // 延长session时间
            else {
                SessionManager.expireSid(client.sid, 3600 * 12);// * 24 * 30
            }
        }

        // 生产和生产测试环境验证用户接口权限验证
        /*需要等权限系统做完才能使用--zp 2017.08.03*/
        /*if (RestfulContextListener.isServer()) {
            if (client.userId > 0 && api.authority.length > 0) {
                boolean authpass = false;
                for (String authority : api.authority) {
                    if (RightsHelper.isRight(client.account, authority)) {
                        authpass = true;
                        break;
                    }
                }
                if (!authpass) {
                    throw new DeniedException("access denied");
                }
            }
        }*/

        if (!skipSign) {
            try {
                String serverSign = this.sign(key, this.params);
                String clientSign = client.sign;
                if (!serverSign.equals(clientSign)) {
                    throw new SignErrorException("sign error");
                }
            } catch (Throwable e) {
                throw e;
            }
        }
    }

    /**
     * 服务端请求签名
     *
     * @param primaryKey
     * @param map
     * @return
     */
    public String sign(String primaryKey, Map<String, String[]> map) {
        logger.debug("开始签名验证...");
        logger.debug("key=" + primaryKey + ", 参数列表:");
        for (String it : map.keySet()) {
            logger.debug(it + "=" + Arrays.toString(map.get(it)));
        }

        String[] seed = map.get("ts");
        if (seed == null || seed.length == 0 || seed[0] == null || seed[0].isEmpty()) {
            throw new SignErrorException("ts is null");
        }

        Set<String> keyset = new TreeSet<>();
        keyset.addAll(map.keySet());
        keyset.remove("sign");

        StringBuilder buf = new StringBuilder();
        boolean pass = false;
        String[] vals;
        for (String key : keyset) {
            vals = map.get(key);
            pass = false;

            if (vals == null) {
                continue;
            }
            for (String it : vals) {
                if (it == null || it.isEmpty()) {
                    pass = true;
                    break;
                }
            }
            if (pass) {
                continue;
            }

            buf.append(key).append("=");
            for (String val : map.get(key)) {
                buf.append(val).append(",");
            }
            buf.deleteCharAt(buf.length() - 1);
            buf.append("&");
        }
        buf.append("key=").append(primaryKey);

        logger.debug("服务端签名前: " + buf.toString());
        String sign = DigestUtils.md5Hex(buf.toString());
        logger.debug("服务端签名后: " + sign);
        return sign;
    }

    private String getString(String key) {
        String[] val = params.get(key);
        if (val != null && val.length > 0) {
            return val[0];
        }
        return "";
    }
}
