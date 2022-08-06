package com.gitchain.core.demo.validator;

import com.gitchain.core.demo.validator.bean.User;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;

/**
 * @author chenyuwen
 * @date 2022/8/6
 */
public class ValidatorTest {

    @Test
    public void validator() {
        User user = new User();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validator.validate(user);
    }

}
