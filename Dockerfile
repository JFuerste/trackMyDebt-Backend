FROM maven:3-openjdk-11 AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

FROM openjdk:11-jre-slim

WORKDIR /app

EXPOSE 5000
COPY --from=MAVEN_BUILD /build/target/trackmydebt-backend-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "trackmydebt-backend-0.0.1-SNAPSHOT.jar"]