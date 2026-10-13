# syntax=docker/dockerfile:1
# Construcción multi-etapa. Las imágenes base se fijan por digest: el tag es solo informativo
# y Dependabot (ecosistema docker) propone el digest nuevo cuando la imagen se reconstruye.

# --- Etapa 1: compilar con el Gradle Wrapper (versión y checksum fijados en gradle-wrapper.properties).
FROM eclipse-temurin:21-jdk-alpine-3.24@sha256:0bfc69a4758a86710e5c474032d28400a8bd00874766f9e8b1642ac2fd293159 AS build
WORKDIR /src
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
RUN ./gradlew --no-daemon dependencies > /dev/null
COPY src ./src
RUN ./gradlew --no-daemon bootJar

# --- Etapa 2: solo el JRE y el jar.
FROM eclipse-temurin:21-jre-alpine-3.24@sha256:51ab5e3302e7141ce665ca3ea85e8b5cd648eafbc3c0c90dd79d6537684e4555
WORKDIR /app
COPY --from=build /src/build/libs/springboot-ci-*.jar /app/springboot-ci.jar
# DEMO: sin instrucción USER, el contenedor corre como root.
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD ["wget", "-q", "-O", "/dev/null", "http://127.0.0.1:8080/actuator/health"]
# Sin -Djava.security.egd: en JDK modernos SecureRandom ya usa /dev/urandom sin bloquear.
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/springboot-ci.jar"]
