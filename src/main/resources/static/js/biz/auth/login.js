layui.config({
    base: '/static/' //静态资源所在路径
}).use(['form','jquery'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;

    // 进行登录操作
    form.on('submit(login)', function (obj) {
        layer.msg('正在验证登录中...', {
            offset: '250px',
            icon: 16,
            time:1000000,
            shade:0.3
        });
        var data = {
            loginName : obj.field.loginName,
            password : md5(obj.field.password)
        };
        //请求登入接口
        var url = serverPath + "/auth/login";
        ajax(url,"PUT",data,
            {
                beforeSendFn:function(){
                    this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
                },
                successFn:function(res) {
                    setLocalItem("loginUser", res.data);

                    layer.msg('登入成功', {
                        icon: 6,
                        time: 1000,
                        shade: 0.3
                    }, function () {
                        location.href = '/index.html#' + pagePath + '/ad.html';
                    });
                },
                completeFn:function(){
                    layer.close(this.layerIndex);
                }
            });
        return false;
    });
});