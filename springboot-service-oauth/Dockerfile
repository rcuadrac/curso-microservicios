FROM openjdk:8
VOLUME /tmp
EXPOSE 9100
ADD ./target/springboot-service-oauth-0.0.1-SNAPSHOT.jar oauth-service.jar
ENTRYPOINT ["java", "-jar", "/oauth-service.jar"]