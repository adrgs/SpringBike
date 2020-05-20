FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/springbike.jar /app/
ENTRYPOINT ["java", "-jar", "springbike.jar"]
