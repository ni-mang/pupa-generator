// 加载monaco编辑器
var editor;

function renderMonaco(elemId, value, lang, readOnly){
    load(elemId);
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
    });
}
function load(elemId){
    var p=document.createElement('p');
    p.id = "prompt"
    p.style.color = "red"
    p.innerHTML='由于下载Monaco编辑器控件需要耗费一定时间，首次打开代码编辑或查看页面时，可能无法正常展开编辑器窗口，请稍作等待，或重复打开当前窗口！';
    document.getElementById(elemId).after(p)
}

//写入值
//var con = 'package com.abc.pupa.business.controller;';
//editor.setValue(con);

//获取内容
// editor.getValue()

//更改为只读
// editor.updateOptions({ readOnly: true });

// 切换语言
//monaco.editor.setModelLanguage(editor.getModel(), lang);