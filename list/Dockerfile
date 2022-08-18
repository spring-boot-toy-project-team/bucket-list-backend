FROM openjdk:11-jdk

ARG JAR_FILE=./build/libs/list-0.0.1-SNAPSHOT.jar
ENV LC_ALL=C.UTF-8

WORKDIR /usr/src/app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean build

COPY ${JAR_FILE} app.jar

CMD ["java", "-jar", "app.jar"]
