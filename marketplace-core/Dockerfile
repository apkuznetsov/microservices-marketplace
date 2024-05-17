FROM openjdk:17-slim as build
LABEL maintainer="Andrey Kuznetsov <a.p.kuznetsov@vk.com>"
COPY target/marketplace-core-0.1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
