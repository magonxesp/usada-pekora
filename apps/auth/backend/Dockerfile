FROM gradle:7.5.0-jdk18-alpine AS build

VOLUME "/build/.gradle"
WORKDIR /build

COPY . .

RUN gradle clean :apps:auth:backend:build --no-daemon -x test

FROM openjdk:18-bullseye AS app

WORKDIR /app

COPY --from=build /build/apps/auth/backend/build/libs/backend.jar backend.jar

EXPOSE 8080

CMD ["java", "-jar", "backend.jar"]
