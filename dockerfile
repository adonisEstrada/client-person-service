FROM openjdk

COPY build/libs/client-person-service-0.0.1-SNAPSHOT.jar /opt/application.jar
ENTRYPOINT java -jar /opt/application.jar
# CMD ["tail", "-f", "/dev/null"]
