package com.nimang.pupa.common.tool.mp.query;

import com.nimang.pupa.common.tool.mp.enums.Compare;
import lombok.Data;

import java.util.List;

@Data
public class MPCondition {
    private List<String> columns;
    private List<Compare> rules;
    private Compare logic;
    private Object val;
    private String after;
}
