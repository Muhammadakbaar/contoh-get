FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
WORKDIR /app
COPY . .
COPY src/main/resources/application.yml.example src/main/resources/application.yml
RUN ./gradlew clean assemble
CMD java -Dcom.sun.management.jmxremote -noverify -jar build/libs/steamdom-master-*-all.jar
