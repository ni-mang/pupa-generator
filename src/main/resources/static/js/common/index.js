var loginUser = getLocalItem("loginUser");
var isAdmin = superAdminId == loginUser.id
layui.use(['jquery', 'layer', 'miniAdmin', 'miniTongji'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        miniAdmin = layui.miniAdmin,
        miniTongji = layui.miniTongji;



    $('#showNickName').text(loginUser.nickName);

    let iniUrl = "/api/init.json";
    if(isAdmin){
        iniUrl = "/api/init_admin.json";
    }

    var options = {
        iniUrl: iniUrl,    // 初始化接口
        clearUrl: "/api/clear.json", // 缓存清理接口
        renderPageVersion: true,    // 初始化页面是否加版本号
        bgColorDefault: false,      // 主题默认配置
        multiModule: false,          // 是否开启多模块
        menuChildOpen: false,       // 是否默认展开菜单
        loadingTime: 0,             // 初始化加载时间
        pageAnim: true,             // 切换菜单动画
    };
    miniAdmin.render(options);





    $('.login-out').on("click", function () {
        layer.msg('正在退出登录...', {
            offset: '250px',
            icon: 16,
            time:1000000,
            shade:0.3
        });
        var data = {};
        //请求退出登录接口
        var url = serverPath + "/auth/logout";
        ajax(url,"PUT",data,
        {
            beforeSendFn:function(){
                this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
            },
            completeFn:function(){
                layer.close(this.layerIndex);
                setLocalItem("loginUser", {});
                layer.msg('退出登录成功', {
                    icon: 6,
                    time: 1000,
                    shade: 0.3
                },function () {
                    location.href = pagePath + '/auth/login.html'; //后台主页
                });
            }
        });
    });
});

/**
 * 渲染枚举下拉选项
 * @param form form对象
 * @param tagName 下拉框命名
 * @param label 标签
 * @param val 默认选定值
 */
function renderEnums(form, tagName, label, val){
    renderEnumsSort(form, tagName, label, "", val)
}

/**
 * 渲染枚举下拉选项
 * @param form form对象
 * @param tagName 下拉框命名
 * @param label 标签
 * @param sort 排序（V0：按值正序，V1：按值逆序，D0：按注释正序，D1：按注释逆序）
 * @param val 默认选定值
 */
function renderEnumsSort(form, tagName, label, sort, val){
    let select = "select[name=" + tagName + "]";
    renderSelect(
        $(select),
        "/enum/getEnums",
        {label:label,sort:sort},
        "desc", "value",
        val,
        function (){
            form.render("select")
        }
    );
}

function checkLogin(res){
    if (res.code == "401") {
        layer.msg("登录失效，请重新登录！", {
            icon: 5,
            time: 1000
        }, function () {
            top.location.href = pagePath + '/auth/login.html'; //登录
        });
    }
}