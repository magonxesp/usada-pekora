FROM gradle:7.5.0-jdk18-alpine AS build

VOLUME "/build/.gradle"
WORKDIR /build

COPY . .

RUN gradle clean build --no-daemon -x test

FROM openjdk:18-bullseye AS app

COPY --from=build /build/app/build/libs/app.jar .

EXPOSE 8080
EXPOSE 5005
EXPOSE 9001

CMD ["java", "-jar", "app.jar", "--add-opens java.base/jdk.internal.misc=ALL-UNNAMED", "-Dio.netty.tryReflectionSetAccessible=true"]
