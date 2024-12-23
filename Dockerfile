FROM java:8
ADD my-server-1.0-SNAPSHOT.jar app.jar
EXPOSE 7001 7002
##容器时区和宿主机不一致,需要调整容器时区
ENV TZ=Asia/Shanghai
CMD ["java","-jar","app.jar","--spring.profiles.active=pro","--logging.file.path=/home/docker/my-server/msg.log"]
