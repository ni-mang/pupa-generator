// 加载monaco编辑器
var editor;
layui.use(['form'], function () {
    var layer = layui.layer

    window.renderMonaco = function(elemId, value, lang, readOnly){
        // 添加遮罩层
        this.layerIndex = layer.load(0, {time: 30*1000, shade: [0.5, '#393D49'] });

        require.config({ paths: { 'vs': '/js/monaco/vs' }});
        require.config({'vs/nls': {availableLanguages: {'*':'zh-cn'}}});
        require(['vs/editor/editor.main'],function(){
            //console.log('monaco.editor', monaco.editor)
            editor = monaco.editor.create(document.getElementById(elemId), {
                value: [value].join('\n'),
                language: lang,
                theme:'vs-dark',
                readOnly:readOnly
            });
            // 关闭遮罩层
            layer.close(this.layerIndex);
        });
    }


});


//写入值
//var con = 'package com.abc.pupa.business.controller;';
//editor.setValue(con);

//获取内容
// editor.getValue()

//更改为只读
// editor.updateOptions({ readOnly: true });

// 切换语言
//monaco.editor.setModelLanguage(editor.getModel(), lang);