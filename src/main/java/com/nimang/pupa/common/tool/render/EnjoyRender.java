package com.nimang.pupa.common.tool.render;

import com.jfinal.template.Engine;
import com.jfinal.template.Template;

import java.util.Map;

public class EnjoyRender extends Render{

    private final Engine engine;



    public EnjoyRender() {
        this.ofName = "Enjoy";
        this.engine = Engine.use();
    }

    public EnjoyRender(Map<String, Object> genData) {
        this.ofName = "Enjoy";
        this.genData = genData;
        this.engine = Engine.use();
    }

    /**
     * 字符串渲染
     * @param tempStr
     * @param genData
     * @return
     */
    public String render(String tempStr, Map<String, Object> genData){
        // 渲染模板
        Template template = engine.getTemplateByString(tempStr);
        return template.renderToString(genData);
    }

    @Override
    public String render(String tempStr){
        return render(tempStr, genData);
    }
}
