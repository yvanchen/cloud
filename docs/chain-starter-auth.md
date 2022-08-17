# chain-starter-auth

主要是**AuthUtil**提供的静态方法获取认证、用户信息

1. 判断是否是http请求，不是返回null
2. 判断HttpServletRequest是否存在属性_CHAIN_USER_REQUEST_ATTR_，存在则直接返回对应属性值
3. 判断header是否存在chain-Auth，不存在则判断请求参数是否存在chain-Auth，将值都赋值给局部变量token，token存在且值长度大于7，以bearer开头，截取前7位后的字符串，否则返回null
4. token不为空则进行jwt解析，解析异常返回null赋值给局部变量claims，为空则返回null
5. claims不为空且配置参数chain.token.state为ture，将从claims解析得到的tenantId+userId+token作为key，获取redis存储的value，如果和token不一致则返回null
