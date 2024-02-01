package com.example.started.modules.table.to;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class ShowDataTo {
    /**
     * 当前表的数据
     */
    private Map<String, String> body = new LinkedHashMap<>();

    private List<ShowDataTo> child = new LinkedList<>();
}
