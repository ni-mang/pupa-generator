package com.nimang.pupa.common.tool.render;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Map;

public class VelocityRender extends Render{
    private final VelocityContext context;

    public VelocityRender(Map<String, Object> genData) {
        this.ofName = "Velocity";
        this.genData = genData;
        this.context = new VelocityContext(genData);
    }


    /**
     * 字符串渲染
     * @param tempStr
     * @return
     */
    public String render(String tempStr){
        // 渲染模板
        StringWriter sw = new StringWriter();
        Velocity.evaluate(context, sw, "Velocity", tempStr);
        return sw.toString();
    }

}
