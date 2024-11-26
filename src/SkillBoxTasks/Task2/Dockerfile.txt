FROM maven:3.8.7-openjdk-18-slim as mvn_packager
COPY . .
RUN mvn --no-transfer-progress clean package -Dmaven.test.skip
FROM openjdk:18
COPY --from=mvn_packager /target/mod2_skillbox-0.0.1-SNAPSHOT.jar /shell_app.jar
EXPOSE 2004/tcp
ENTRYPOINT ["java", "-jar", "/shell_app.jar"]