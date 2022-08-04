package com.gitchain.core.tips;

/**
 * @author chenyuwen
 * @date 2022/7/1
 */
public interface Tips {

    TipsLevel level();

    int code();

    String language();

    String content();
}
