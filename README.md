# dingtalk-robot
基于logback的钉钉日志告警推送

对接指南
pom依赖配置

	<dependency>
	    <groupId>com.meeruu.dingtalk</groupId>
	    <artifactId>dingtalk-robot</artifactId>
	    <version>1.0.0-SNAPSHOT</version>
	</dependency>
logback-spring.xml 配置 在每个项目的logback-spring.xml文件的

	<springProperty scope="context" name="logging.path" source="logging.path"/>
	<springProperty scope="context" name="logging.level" source="logging.level"/> 
后面新增

	<springProperty scope="context" name="springProfile" source="spring.profiles.active"/>
	
	<appender name="DING-TALK" class="com.meeruu.dingtalk.DingTalkAppender">
	    <!--输出格式-->
	    <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%c%L] -%-5level [%thread] %msg%n</pattern>
	    <!--钉钉机器人token-->
	    <dingdingToken>钉钉机器人生成的token</dingdingToken>
	    <appName>测试验证应用</appName>
	    <env>${springProfile}</env>
	</appender>
在需要使用日志推送的环境下配置即可

	<appender-ref ref="DING-TALK"/>

设置钉钉机器人操作
https://help.aliyun.com/document_detail/112831.html

----------------------------------------------
#### 听说，打赏我的人最后都找到了真爱。O(∩_∩)O~
![Alt text](src/main/resources/images/image.png)
