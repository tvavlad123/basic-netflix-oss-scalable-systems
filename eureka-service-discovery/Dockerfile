FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
COPY target/*.jar app.jar
EXPOSE 8089

ENV JAVA_OPTS=""
ENV SPRING_PROFILE=""

ENTRYPOINT exec java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILE -jar app.jar