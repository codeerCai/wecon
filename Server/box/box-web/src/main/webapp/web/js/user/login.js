/**
 * Created by zengzhipeng on 2017/8/2.
 */
$(function () {
    T.common.user.checkAuth();
    $("#login").bind('click', function () {
        $("#loadingModal").modal("show");
        var params =
        {
            alias: $('#alias').val().trim(),
            password: $('#password').val().trim(),
            isremeber: "0"
        };

        if (params.alias == '' || params.password == '') {
            alert("请输入帐号密码");
            $("#loadingModal").modal("hide");
            return;
        }
        if ($("#cbremeber").is(":checked")) {
            params.isremeber = "1";
        }
        params.password = T.common.util.md5(params.password);
        T.common.ajax.request("WeconBox", "user/signin", params, function (data, code, msg) {
            $("#loadingModal").modal("hide");
            if (code == 200) {
                T.common.user.setSid(data.sid);
                location = "../main.html";
            }
            else {
                alert(code + " " + msg);
            }
        });

    });
})