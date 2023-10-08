package com.nimang.pupa.business.controller;

import com.alibaba.fastjson.JSON;
import com.nimang.pupa.base.model.gen.GenTemplate;
import com.nimang.pupa.business.service.impl.BizGenServiceImpl;
import com.nimang.pupa.common.pojo.IdBO;
import com.nimang.pupa.common.tool.webTool.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 项目/代码生成
 * @module pupa
 * @date 2023-04-28
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/gen")
@RequiredArgsConstructor
public class GenController {

    private final BizGenServiceImpl genService;

    /**
     * 全库生成
     * @param sourceId Long
     * @author LinLaichun
     * @date 2023-04-28
     * @status released
     */
    @GetMapping("/genAll")
    public void genAll(@Validated @RequestParam @NotNull Long sourceId) {
        log.info(" 代码生成-全库生成-开始 参数：{}",  sourceId);
        genService.genAll(sourceId);
        log.info(" 代码生成-全库生成-结束");
    }

    /**
     * 生成指定表
     * @param tableIds Long[]
     * @return R<Integer>
     * @author LinLaichun
     * @date 2023-04-28
     * @status released
     */
    @GetMapping("/genTables")
    public void genTables(@Validated @RequestParam Long[] tableIds) {
        log.info(" 代码生成-生成指定表-开始 参数：{}", JSON.toJSONString(tableIds));
        genService.genTables(Arrays.asList(tableIds));
        log.info(" 代码生成-生成指定表-结束 ");
    }

    /**
     * 预览指定表生成的内容
     * @param idBO IdBO
     * @return R<Integer>
     * @author LinLaichun
     * @date 2023-05-25
     * @status released
     */
    @PostMapping("/previewTable")
    public R<List<GenTemplate>> previewTable(@Validated @RequestBody IdBO idBO) {
        log.info(" 代码生成-预览指定表-开始 参数：{}",  JSON.toJSONString(idBO));
        List<GenTemplate> gtList = genService.previewTable(idBO.getId());
        return R.ok(gtList);
    }
}
