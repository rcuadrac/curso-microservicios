FROM openjdk:8
VOLUME /tmp
ADD ./target/product-service-1.0.0.jar product-service.jar
ENTRYPOINT ["java", "-jar", "/product-service.jar"]