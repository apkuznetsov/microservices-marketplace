FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 10050
ENV JVM_OPTIONS ""
ENTRYPOINT ["sh","-c","java $JVM_OPTIONS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
