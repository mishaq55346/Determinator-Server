FROM openjdk:8
COPY . /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-version"]
#ENTRYPOINT ["java", "-jar", "./target/server-1.0.0.jar"]