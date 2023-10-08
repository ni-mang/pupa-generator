layui.use(['form','miniPage','jquery'],function(){
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        miniPage = layui.miniPage;

    /**
     * 初始化表单，要加上，不然刷新部分组件可能会不加载
     */
    form.render();

    //表单初始赋值
    var loginUser = getLocalItem("loginUser");
    form.val('setting', {
        "loginName": loginUser.loginName
        , "nickName": loginUser.nickName
    })


    //提交
    form.on('submit(saveBtn)', function(obj){
        layer.msg('正在提交...', {
            offset: '250px',
            icon: 16,
            time:1000000,
            shade:0.3
        });
        var data = {
            nickName : obj.field.nickName
        };
        loginUser.nickName = obj.field.nickName;

        var url = serverPath + "/auth/modify";
        ajax(url,"POST",data,
            {
                beforeSendFn:function(){
                    this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
                },
                successFn:function(res) {
                    layer.msg('修改成功', {
                        icon: 6,
                        time: 1000,
                        shade: 0.3
                    }, function () {
                        $('#showNickName').text(loginUser.nickName);
                        setLocalItem("loginUser", loginUser);
                        miniPage.hashHome();
                    });
                },
                completeFn:function(){
                    layer.close(this.layerIndex);
                }
            })
        return false;
    });

});