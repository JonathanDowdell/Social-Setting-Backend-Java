FROM openjdk:11
VOLUME /tmp
EXPOSE 8086
ADD target/server-0.0.1-SNAPSHOT.jar server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "server-0.0.1-SNAPSHOT.jar"]