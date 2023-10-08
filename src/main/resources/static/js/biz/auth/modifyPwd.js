layui.use(['form','miniPage','jquery'],function(){
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        miniPage = layui.miniPage;

    /**
     * 初始化表单，要加上，不然刷新部分组件可能会不加载
     */
    form.render();

    // 自定义验证规则
    form.verify({
        // 确认密码
        confirmPassword: function(value, item){
            var passwordValue = $('#newPassword').val();
            if(value !== passwordValue){
                return '两次密码输入不一致';
            }
        }
    });

    //提交
    form.on('submit(saveBtn)', function(obj){
        layer.msg('正在提交...', {
            offset: '250px',
            icon: 16,
            time:1000000,
            shade:0.3
        });
        var data = {
            password : md5(obj.field.password),
            newPassword : md5(obj.field.newPassword),
            confirmPassword : md5(obj.field.confirmPassword)
        };

        //请求登入接口
        var url = serverPath + "/auth/modifyPwd";
        ajax(url,"PUT",data,
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