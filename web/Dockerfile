FROM eclipse-temurin:17-jdk as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jre
ARG RUNTIME_DIR
WORKDIR /app
RUN mkdir -p ${RUNTIME_DIR}
VOLUME ${RUNTIME_DIR}
ENV RUNTIME_DIR=${RUNTIME_DIR}
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]