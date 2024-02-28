package com.example.common.v0.utils.to;

import lombok.Data;

@Data
public class BeanDiff {
    private String fieldName;
    private Object fieldContent;
    private Object newFieldContent;
    private String handerType;
}
