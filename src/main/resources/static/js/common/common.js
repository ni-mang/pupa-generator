//api服务地址
const serverPath = "http://" + document.location.host + "/pupa";
//页面服务地址
const pagePath = "/view";
const tableName = "pupa";
const superAdminId = 1;

// var tokenName = "access_token";
// var rolePowerName = "role_power";

// var token = getToken();
// var indexAccount = getLocalItem("loginName");
// var indexNickName = getLocalItem("nickName");

document.write("<script src=\"/js/utils/uuid.js\"></script>");
document.write("<script src=\"/js/utils/formatDate.js\"></script>");


/*layui.use(['form'], function(){
    var form = layui.form;
    //自定义校验规则
    form.verify(checkRule);
    //自定义跨ifram调用方法
    window.tools = _tools;

    //复选框取值
    form.on('checkbox()', function(data){
        var eleName = $(this).attr("name");
        if(eleName.indexOf("ck_")==0){
            var name = eleName.substr(3,eleName.length);
            var arr = new Array();
            var fdName ="input:checkbox[name='"+eleName+"']:checked";
            var setFd = "#"+name;
            $(fdName).each(function(i){
                arr[i] = $(this).val();
            });
            var fdVal = arr.join(",");//将数组合并成字符串
            $(setFd).val(fdVal);
        }
    });

});*/
//自定义跨ifram调用方法
var _tools = {
    //防止重复点击提交按钮
    noUp: function(){
        $(".layui-layer-btn0").css({"border-color": "#d6d6d6","background-color": "#d6d6d6"});
        $(".layui-layer-btn0").attr("canup",0);
    },
    doUp: function(){
        $(".layui-layer-btn0").removeAttr("style");
        $(".layui-layer-btn0").attr("canup",1);
    },
    canUp: function(){
        var canup = $(".layui-layer-btn0").attr("canup");
        return canup==0?false:true;
    }
};
//校验规则
var checkRule = {
    // 单选、复选必填验证
    otherReq: function(value,item){
        var verifyName=$(item).attr('name')
            , verifyType=$(item).attr('type')
            ,formElem=$(item).parents('.layui-form')//获取当前所在的form元素，如果存在的话
            ,verifyElem=formElem.find('input[name='+verifyName+']')//获取需要校验的元素
            ,isTrue= verifyElem.is(':checked')//是否命中校验
            ,focusElem = verifyElem.next().find('i.layui-icon');//焦点元素
        if(!isTrue || !value){
            //定位焦点
            focusElem.css(verifyType=='radio'?{"color":"#FF5722"}:{"border-color":"#FF5722"});
            //对非输入框设置焦点
            focusElem.first().attr("tabIndex","1").css("outline","0").blur(function() {
                focusElem.css(verifyType=='radio'?{"color":""}:{"border-color":""});
            }).focus();
            return '必填项不能为空';
        }
    }
};

//全局设置ajax消息头
// if(token!=null){
//     $.ajaxSetup({
//         headers:{token:token}
//     });
// }


// if(token==null){
//     layer.msg("登录状态无效，请重新登录！",{
//         icon: 5
//         ,time: 1000
//     },function(){
//         location.href = './user/login.html'; //登录
//     })
// }

const requestType = ["get", "delete", "GET", "DELETE"];

/**
 * 发起ajax请求
 * @param url 请求地址
 * @param type 请求类型
 * @param data 请求参数
 * @param funtions 方法集合
 * @param async 是否异步（默认true）
 * @param contentType 请求内容类型（默认application/json;charset=utf-8）
 */
function ajax(url,type,data,funtions,async,contentType) {
    let requestData;
    if(async==null){
        async=true;
    }
    if(contentType==null){
        contentType = "application/json;charset=utf-8";
    }
    if(requestType.indexOf(type)>0){
        // get、delete 请求参数拼接
        url = obj2Param(url, data)
    }else {
        // post、put 公共参数封装
        var version = "V1.0";
        var uuid = getUuid(10, 10);
        var timestamp = formatDate(new Date().getTime());
        var baseData = {
            "version":version,
            "requestNo":uuid,
            "timestamp":timestamp,
            "data":data
        }
        requestData = JSON.stringify(data);
    }
    let ajaxSetting = {
        type : type,
        dataType: "json",
        async : async,
        data: requestData,
        url: url,
        contentType:contentType,
        beforeSend:function () {
            var beforeSendFn = funtions.beforeSendFn;
            if(beforeSendFn){
                if (typeof(beforeSendFn) == "function") {
                    beforeSendFn();
                }else if(typeof(beforeSendFn) == "string"){
                    eval(beforeSendFn+'()');
                }
            }
        },
        success: function(res){
            if(res.success==true) {
                var successFn = funtions.successFn;
                if(successFn){
                    if (typeof(successFn) == "function") {
                        successFn(res);
                    }else if(typeof(successFn) == "string"){
                        eval(successFn+'(res)');
                    }
                }
            }else {
                var errorCode = res.code;
                if (errorCode == "401") {
                    layer.msg("登录失效，请重新登录！", {
                        icon: 5,
                        time: 1000
                    }, function () {
                        top.location.href = pagePath + '/auth/login.html'; //登录
                    });
                } else {
                    layer.msg(res.msg, {
                        icon: 5
                        , time: 3000
                    },function(){
                        var erroFn = funtions.erroFn;
                        if(erroFn){
                            if (typeof(erroFn) == "function") {
                                erroFn(res);
                            }else if(typeof(erroFn) == "string"){
                                eval(erroFn+'(res)');
                            }
                        }
                    });//失败的表情
                }
            }
        },
        error:function (res) {
            var erroFn = funtions.erroFn;
            if(erroFn){
                if (typeof(erroFn) == "function") {
                    erroFn(res);
                }else if(typeof(erroFn) == "string"){
                    eval(erroFn+'(res)');
                }
            }else {
                layer.msg(res.msg, {
                    icon: 5
                    , time: 3000
                },function(){
                    var erroFn = funtions.erroFn;
                    if(erroFn){
                        if (typeof(erroFn) == "function") {
                            erroFn(res);
                        }else if(typeof(erroFn) == "string"){
                            eval(erroFn+'(res)');
                        }
                    }
                });
            }
        },
        complete:function () {
            var completeFn = funtions.completeFn;
            if(completeFn){
                if (typeof(completeFn) == "function") {
                    completeFn();
                }else if(typeof(completeFn) == "string"){
                    eval(completeFn+'()');
                }
            }
        }
    }
    $.ajax(ajaxSetting);
}

/**
 * 获取数据填充下拉选择项
 * @param element 下拉框对象
 * @param url 数据来源地址
 * @param param 请求参数
 * @param nameTag 名称标签
 * @param valueTag 值标签
 * @param selectedVal 默认选中值
 * @param completeFn 回调方法
 * @param defOption
 */
function renderSelect(element, url, param, nameTag, valueTag, selectedVal, completeFn, defOption){
    ajax(serverPath + url,"GET",param,
    {
        successFn:function(res) {
            const data = res.data;
            renderSelectWithData(element, data, nameTag, valueTag, selectedVal, completeFn, defOption)
        }
    });
}

/**
 * 使用已有数据填充下拉选择项
 * @param element
 * @param data
 * @param nameTag
 * @param valueTag
 * @param selectedVal
 * @param completeFn
 * @param defOption
 */
function renderSelectWithData(element, data, nameTag, valueTag, selectedVal, completeFn, defOption){
    // 默认空选项
    if(defOption === undefined || defOption === true){
        element.append(new Option("--请选择--",""));
    }
    // 循环插入选项
    data.forEach(function(val,index,arr){
        if(val[valueTag] == selectedVal){
            // 选中默认值
            element.append(new Option(val[nameTag], val[valueTag], false, true));
        }else {
            element.append(new Option(val[nameTag], val[valueTag]));
        }
    });
    // 执行回调方法
    if(completeFn){
        if (typeof(completeFn) == "function") {
            completeFn();
        }else if(typeof(completeFn) == "string"){
            eval(completeFn+'()');
        }
    }
}

/**
 * ajax_加密
 * @param url
 * @param type
 * @param data
 * @param funtions
 * @param async
 */
function ajax_encrypt(url,type,data,funtions,async) {
    if(requestType.indexOf(type)>0){
        layer.msg("请求类型错误，请使用post或put", {
            icon: 5,
            time: 1000
        });
        return;
    }
    var encryptData = Base64.encodeURI(JSON.stringify(data));
    var edata = {};
    edata.encryptData = encryptData;
    edata.encrypt = true;
    ajax(url,type,edata,funtions,async);
}

/**
 * 将数据写入localStorage
 * @param name
 * @param value
 */
function setLocalItem(name,value) {
    var tableJson = window.localStorage.getItem(tableName);
    var table = JSON.parse(tableJson);
    if(table==null){
        table = {};
    }
    table[name]=value;
    window.localStorage.setItem(tableName,JSON.stringify(table));
}

/**
 * 获取localStorage数据
 * @param name
 * @returns {*}
 */
function getLocalItem(name) {
    var tableJson = window.localStorage.getItem(tableName);
    var table = JSON.parse(tableJson);
    if(table==null){
        return {};
    }
    return table[name];
}

// 获取token
function getToken() {
    return getLocalItem(tokenName);
}

// 保存token
function setToken(tokenVal) {
    return setLocalItem(tokenName,tokenVal);
}

// 获取角色权限
function getRolePower() {
    return getLocalItem(rolePowerName);
}

// 保存角色权限
function setRolePower(rolePower) {
    return setLocalItem(rolePowerName,rolePower);
}


//获取含有权限关键字power属性的元素
$("[power]").each(function () {
    var p = $(this).attr("power");
    var hasp = hasPower(p);
    if(!hasp){
        $(this).html("");
    }
});
//权限判断
function hasPower(p) {
    var powers = getRolePower();
    var hasp = powers.findIndex(function(value, index, arr) {
        return value.indexOf(p)==0;
    });
    return hasp==-1?false:true
}

/**
 * 解析url中的参数
 * @param strSearch
 * @returns {{}}
 */
function param2Obj(strSearch) {
    if(strSearch === undefined){
        strSearch = location.search;
    }
    let arr = strSearch.replace('?', '').split('&');
    let obj = arr.reduce(function (prev, elem) {
        // 打散
        let [key, value] = elem.split('=');
        // 判断没有添加新数组
        if (prev[key] === undefined) {
            prev[key] = [value];
        } else {
            prev[key].push(value);
        }
        return prev;
    }, {});
    // 简化对象的值，保留长度等于1的值从数组中取出
    for (const key in obj) {
        if (obj[key].length === 1) {
            obj[key] = obj[key][0];
        }
    }
    return obj;
}

/**
 * 拼接url参数
 * @param url
 * @param data
 * @returns {string}
 */
function obj2Param(url, data){

    let param = url.indexOf("?")>-1?"&":"?";
    for (let key in data) {
        if(data[key] !== undefined && data[key].constructor === Array){
            data[key].forEach(function(val,index,arr){
                param += key + "=" + val + "&";
            });
        }else {
            param += key + "=" + data[key] + "&";
        }
    }
    return url + param.substring(0,param.length-1);
}