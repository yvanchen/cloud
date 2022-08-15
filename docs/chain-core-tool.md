# chain-core-tool

api层主要是定义全局包装返回对象R，R的静态生成方法主要是入参IResultCode接口，IResultCode接口有一个枚举实现ResultCode

beans层应该是做对象属性拷贝使用 // TODO 以后再分析

**@AutoConfigureBefore**

config层请求参数：枚举与字符串互转、json做默认配置处理、消息转换器处理和日期转换

constant层定义一些全局常量

convert层类型转换

function层自定义函数，主要增加了抛出异常处理

jackson层主要做json的一些序列化反序列化处理

node层主要是将列表数据转换成树结构

spel层针对spel做缓存

ssl层不校验证书并信任所有主机

support层工具类支持

tuple层

utils层工具类

yml层获取yml文件转换成配置信息


