# chain-core-auto

知识点：注解处理器**Processor**

主要功能：

1. 将 **@AutoService** 注解的类生成 **META-INF/services/** 下的文件用于 **SPI**
2. 将 **@AutoIgnore** 注解的类跳过生成
3. 将 **@FeignClient** 、 **@ChainFeignAutoConfiguration** 、 **@AutoContextInitializer** 、 **@AutoListener** 、 **@AutoRunListener** 、 **@AutoFailureAnalyzer** 、 **@Component** 注解的类生成 **META-INF/spring.factories** 和 **META-INF/spring-devtools.properties** 文件用于自动装配和配合 **spring-devtools** 自动重启
