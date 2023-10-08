package com.nimang.pupa.base.model.gen;

import com.nimang.pupa.common.util.LDTUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class GenCommon {

    /**
     * 日期
     * yyyy-MM-dd
     */
    private String date;

    /**
     * 时间
     * yyyy-MM-dd HH:mm:ss
     */
    private String dateTime;

    public GenCommon() {
        // 表名常用处理
        this.date = LDTUtils.formatNow("yyyy-MM-dd");
        this.dateTime = LDTUtils.formatNow("yyyy-MM-dd HH:mm:ss");
    }

    public void putGenData(Map<String, Object> genData) {
        genData.put("common", this);
    }

    public static void showInfo(List<GenDataInfo> infoList){
        String start = "common";
        infoList.add(new GenDataInfo(start + ".date", "日期", "yyyy-MM-dd"));
        infoList.add(new GenDataInfo(start + ".dateTime", "时间", "yyyy-MM-dd HH:mm:ss"));
    }
}
