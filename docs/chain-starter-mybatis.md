# chain-starter-mybatis

base层BaseEntity用于所有数据库实体基类继承，BaseServiceImpl是对ServiceImpl拓展增强

config层MybatisPlusConfiguration用于配置多租户、分页、sql日志输出拦截器和默认sql注入、分页参数解析器

injector层增加sql注入：InsertIgnore、Replace、InsertBatchSomeColumn
