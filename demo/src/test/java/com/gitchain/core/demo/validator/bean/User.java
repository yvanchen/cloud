package com.gitchain.core.demo.validator.bean;

import javax.validation.constraints.AssertTrue;

/**
 * @author chenyuwen
 */
public class User {

    @AssertTrue
    private Boolean sex;
}
