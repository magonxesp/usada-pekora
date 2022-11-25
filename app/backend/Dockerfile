FROM gradle:7.5.0-jdk18-alpine AS build

VOLUME "/build/.gradle"
WORKDIR /build

COPY . .

RUN gradle clean :app:backend:build --no-daemon -x test

FROM openjdk:18-bullseye AS app

COPY --from=build /build/app/backend/build/libs/backend.jar backend.jar

EXPOSE 8080

CMD ["java", "-jar", "backend.jar"]
