FROM gradle:8.3.0-jdk17-alpine AS build

VOLUME "/build/.gradle"
WORKDIR /build

COPY . .

RUN gradle clean :apps:bot:backend:build --no-daemon -x test

FROM openjdk:17-bullseye AS app

WORKDIR /app

COPY --from=build /build/apps/bot/backend/build/libs/backend-all.jar backend.jar

EXPOSE 8080

CMD ["java", "--add-opens java.base/jdk.internal.misc=ALL-UNNAMED", "-Dio.netty.tryReflectionSetAccessible=true", "-jar", "backend.jar"]
