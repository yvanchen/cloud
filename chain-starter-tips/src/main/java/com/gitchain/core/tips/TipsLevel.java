package com.gitchain.core.tips;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenyuwen
 * {@code @date} 2022/7/1
 */
@Getter
@AllArgsConstructor
public enum TipsLevel {

    /**
     *
     */
    NORMAL(1,"正常"),
    WARNING(2,"警告"),
    SERIOUS(3,"严重"),
    ERROR(4,"错误");

    private final int code;

    private final String desc;
}
