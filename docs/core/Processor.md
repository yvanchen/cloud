**javax.annotation.processing.Processor**是编译期注解处理器,可以利用它生成文件或class增强

因为编译期就会执行注解处理器，但是注解处理器此时还未编译会导致报错。

所以使用时需要拆分成两个项目:

包含注解处理器的项目需要在pom.xml配置``<proc>none</proc>``禁用注解处理器

在另一个项目正常使用即可

maven配置

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>${compiler.version}</version>
            <configuration>
                <proc>none</proc>
            </configuration>
        </plugin>
    </plugins>
</build>
```
注解定义
```java
@Documented
@Target(TYPE)
@Retention(SOURCE)
public @interface MyGet {
}
```
注解处理器配置
```java
@SupportedAnnotationTypes("com.gitchain.core.demo.processor.anno.MyGet")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GetProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // TODO:对注解的类进行处理
        return true;
    }
}
```
在resources资源文件夹下面添加META-INF/services/javax.annotation.processing.Processor文件，文件内容：
```text
com.gitchain.core.demo.processor.GetProcessor
```
在新项目编写一个使用类
```java
@MyGet
public class Cat {
    private String name;
}
```
对项目进行编译，查看target下的class文件
```java
public class Cat {
    private String name;

    public void getName(String name) {
        this.name = name;
    }

    public Cat() {
    }
}
```
