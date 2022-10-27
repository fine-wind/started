package com.example.common.v0.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 基础树实体类，所有实体都需要继承
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseTreeEntity extends BaseEntity implements Serializable {

    private Long pid;
}
