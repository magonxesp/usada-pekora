FROM gradle:7.5.0-jdk18-alpine AS build

VOLUME "/build/.gradle"
WORKDIR /build

COPY . .

RUN gradle clean :app:discord-bot:build --no-daemon -x test

FROM openjdk:18-bullseye AS app

WORKDIR /app

COPY --from=build /build/app/discord-bot/build/libs/discord-bot-all.jar discord-bot.jar

EXPOSE 8081

CMD ["java", "-jar", "discord-bot.jar", "--add-opens java.base/jdk.internal.misc=ALL-UNNAMED", "-Dio.netty.tryReflectionSetAccessible=true"]
