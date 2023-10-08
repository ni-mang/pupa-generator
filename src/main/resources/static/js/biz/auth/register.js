layui.use(['form','jquery'],function(){
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;

    // 自定义验证规则
    form.verify({
        // 确认密码
        confirmPassword: function(value, item){
            var passwordValue = $('#reg-password').val();
            if(value !== passwordValue){
                return '两次密码输入不一致';
            }
        }
    });

    //提交
    form.on('submit(register-submit)', function(obj){
        layer.msg('正在提交...', {
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
        var url = serverPath + "/auth/register";
        ajax(url,"POST",data,
            {
                beforeSendFn:function(){
                    this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
                },
                successFn:function(res) {
                    layer.msg('注册成功', {
                        icon: 6,
                        time: 1000,
                        shade: 0.3
                    }, function () {
                        location.href = pagePath + '/auth/login.html'; //登录页
                    });
                },
                completeFn:function(){
                    layer.close(this.layerIndex);
                }
            })
        return false;
    });

});