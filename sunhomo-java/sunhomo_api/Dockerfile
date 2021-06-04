# 引入 openjdk 镜像
FROM openjdk:8-jdk-alpine
RUN apk add tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone && \
    apk del tzdata
# 声明作者
LABEL AUTHOR=Nickel OG=sunhomo.cn
# 挂载几个有用的文件夹 比如日志
VOLUME ["/tmp","/logs"]
# 声明一个环境参数用来动态启用配置文件 默认dev
ENV ACTIVE=prod
# 暴露端口
EXPOSE 80
# 复制并修改应用打包后的jar文件名称
ARG JAR_FILE
ADD target/${JAR_FILE} sunhomo_api.jar
# 容器启动时第一个运行的命令 用来启动应用
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${ACTIVE}","-Duser.timezone=GMT+08","-jar","sunhomo_api.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${ACTIVE}","-Duser.timezone=Asia/Shanghai","-jar","sunhomo_api.jar"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${ACTIVE}","-jar","sunhomo_api.jar"]