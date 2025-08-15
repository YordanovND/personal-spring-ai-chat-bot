# ---------- Angular build ----------
FROM node:20-alpine AS web
WORKDIR /web
COPY web/package*.json ./
RUN npm ci
COPY web/ ./
RUN npm run build -- --configuration=production

# ---------- Spring build ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN rm -rf src/main/resources/static/* || true
COPY --from=web /web/dist/web/browser/ src/main/resources/static/

RUN ./gradlew bootJar --no-daemon

# ---------- Runtime ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]