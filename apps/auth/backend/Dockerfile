FROM gradle:8.3.0-jdk17-alpine AS build

VOLUME "/build/.gradle"
WORKDIR /build

COPY . .

RUN gradle clean :apps:auth:backend:build --no-daemon -x test

FROM openjdk:17-bullseye AS app

WORKDIR /app

COPY --from=build /build/apps/auth/backend/build/libs/backend-all.jar backend.jar

EXPOSE 8081

CMD ["java", "-jar", "backend.jar"]
