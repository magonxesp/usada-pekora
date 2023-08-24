FROM gradle:7.5.0-jdk17-alpine AS build

VOLUME "/build/.gradle"
WORKDIR /build

COPY . .

RUN gradle clean :apps:bot:discord-bot:build --no-daemon -x test

FROM openjdk:17-bullseye AS app

WORKDIR /app

COPY --from=build /build/apps/bot/discord-bot/build/libs/discord-bot-all.jar discord-bot.jar

EXPOSE 8082

CMD ["java", "-jar", "discord-bot.jar", "--add-opens java.base/jdk.internal.misc=ALL-UNNAMED", "-Dio.netty.tryReflectionSetAccessible=true"]
