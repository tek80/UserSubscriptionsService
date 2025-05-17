FROM maven:3.8.5-openjdk-17-slim
WORKDIR /build/
COPY pom.xml ./
COPY src ./src/
RUN mvn package -DskipTests
RUN mkdir -p /opt/usersubs/
RUN cp $(find ./target/ -name '*.jar') /opt/usersubs/usersubs.jar
ENTRYPOINT [ "java", "-jar", "/opt/usersubs/usersubs.jar" ]