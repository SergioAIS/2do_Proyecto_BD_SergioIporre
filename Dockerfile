# Etapa 1: build con Maven y Java 17
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos pom y código fuente
COPY pom.xml .
COPY src ./src

# Construimos el jar (dentro del contenedor, con Maven)
RUN mvn -q -DskipTests package

# Etapa 2: imagen liviana solo para ejecutar
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiamos el jar generado desde la etapa de build
ARG JAR_FILE=target/lms-1.0.0.jar
COPY --from=build /app/${JAR_FILE} app.jar

# Render usará la variable PORT; por defecto 8080
ENV PORT=8080
EXPOSE 8080

# Arrancamos Spring Boot usando el puerto que nos da Render
CMD ["sh","-c","java -Dserver.port=${PORT} -jar app.jar"]
