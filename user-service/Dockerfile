FROM maven:3.9.6-eclipse-temurin-22

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN mvn dependency:go-offline

COPY src ./src

CMD ["mvn","spring-boot:run"]
