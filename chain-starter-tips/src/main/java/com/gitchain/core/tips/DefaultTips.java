package com.gitchain.core.tips;

import lombok.AllArgsConstructor;

/**
 * @author chenyuwen
 * @date 2022/7/1
 */
@AllArgsConstructor
public enum DefaultTips implements Tips {
    /**
     *
     */
    SUCCESS(TipsLevel.NORMAL, 200, "zh", "正常"),
    FAIL(TipsLevel.ERROR, 500, "zh", "系统异常");
    private final TipsLevel level;
    private final int code;
    private final String language;

    private final String content;


    @Override
    public TipsLevel level() {
        return level;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String language() {
        return language;
    }

    @Override
    public String content() {
        return content;
    }
}
