# chain-core-launch

作为启动器主流程做了如下事情：
1. 保证appName不为空
2. 获取命令行参数、系统参数、系统变量
3. 获取环境变量保证必须匹配dev、test、prod其中最多一个，如果没有则设置为默认的dev
4. 打印输出环境变量和当前路径
5. 配置默认参数
6. 将 **LauncherService** 所有SPI实现类按照排序循环遍历执行

**StartEventListener**执行启动完成后打印输出appName、启动端口、环境变量

**ChainLaunchConfiguration**无意义

**ChainPropertyConfiguration**创建**ChainPropertySourcePostProcessor**属性加载执行器

**ChainPropertySourcePostProcessor**执行流程
1. 判断有无bean使用 **@ChainPropertySource**注解
2. 将 **@ChainPropertySource**注解信息抽取出来组成资源列表
3. 去重、排序
4. 如果 **@ChainPropertySource**配置了loadActiveProfile属性为true（默认就是true），则加载value属性-profile.extension文件
5. 加载value属性.extension文件
6. 将获取的配置信息设置到环境变量配置中
