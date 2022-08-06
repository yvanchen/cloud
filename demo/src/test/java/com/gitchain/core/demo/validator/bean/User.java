package com.gitchain.core.demo.validator.bean;
import javax.validation.constraints.Min;

/**
 * @author chenyuwen
 * @date 2022/8/6
 */
public class User {

    @Min(7)
    private Boolean sex;
}
