FROM openjdk:11-jdk

ENV LC_ALL=C.UTF-8

WORKDIR /usr/src/app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean build

CMD ["java", "-jar", "./build/libs/list-0.0.1-SNAPSHOT.jar"]
