FROM eclipse-temurin:17-jdk
RUN apt update && apt install -y dos2unix

ARG RUNTIME_DIR
WORKDIR /app
RUN mkdir -p ${RUNTIME_DIR}
VOLUME ${RUNTIME_DIR}
ENV RUNTIME_DIR=${RUNTIME_DIR}

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN dos2unix ./mvnw
RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]