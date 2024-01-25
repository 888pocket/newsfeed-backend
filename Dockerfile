FROM openjdk:17

VOLUME /tmp

EXPOSE 5000

ARG JAR_FILE=build/libs/order-system-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} order-system.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/order-system.jar"]