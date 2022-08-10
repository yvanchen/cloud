# chain-core-launch

作为启动器主流程做了如下事情：
1. 保证appName不为空
2. 获取命令行参数、系统参数、系统变量
3. 获取环境变量保证必须匹配dev、test、prod其中最多一个，如果没有则设置为默认的dev
4. 打印输出环境变量和当前路径
5. 配置默认参数
6. 将 **LauncherService** 所有SPI实现类按照排序循环遍历执行

**StartEventListener**执行启动完成后打印输出appName、启动端口、环境变量

