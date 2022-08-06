**hibernate-validator-annotation-processor**用于在编译期检查**hibernate-validator**注解使用是否正确，避免运行时抛出异常

比如使用**hibernate-validator**
引入依赖
```xml
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>${hibernate-validator.version}</version>
</dependency>
<dependency>
    <groupId>javax.el</groupId>
    <artifactId>javax.el-api</artifactId>
    <version>${javax.el.version}</version>
</dependency>
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.el</artifactId>
    <version>${javax.el.version}</version>
</dependency>
<!--用于单元测试-->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>${junit.version}</version>
    <scope>test</scope>
</dependency>
```
编写代码
```java
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
```
```java
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
```
编译正常,执行会报错
```log
八月 06, 2022 11:53:45 下午 org.hibernate.validator.internal.util.Version <clinit>
INFO: HV000001: Hibernate Validator 6.1.6.Final

javax.validation.UnexpectedTypeException: HV000030: No validator could be found for constraint 'javax.validation.constraints.Min' validating type 'java.lang.Boolean'. Check configuration for 'sex'

	at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree.getExceptionForNullValidator(ConstraintTree.java:116)
```
很明显Boolean类型的校验不能使用@Min注解

我们加上**hibernate-validator-annotation-processor**依赖再试试
```xml
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator-annotation-processor</artifactId>
    <version>${hibernate-validator.version}</version>
</dependency>
```
这时就会发现在编译期就会报错
```log
java: The annotation @Min is disallowed for this data type.
```
很好的避免了在运行期才会发现的校验注解对应类型和实际类型不一致的问题


