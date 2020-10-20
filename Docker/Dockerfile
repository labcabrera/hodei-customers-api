FROM openjdk:11.0.6-jre-slim
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/app.jar
