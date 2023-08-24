# Usada pekora
Usada pekora discord bot, notify new videos from usada pekora youtube channel and reacts to text messages HA↗HA↘HA↗HA↘HA↗HA↘

[Add pekora to discord server](https://discord.com/api/oauth2/authorize?client_id=755159995720532028&permissions=3668032&scope=bot)

![pekora](https://64.media.tumblr.com/5c773af807b50ce0f223032570aae9fe/8c875e418b8e2de3-1e/s512x512u_c1/1eb8ddee17e52516a4e1f697715ef03ddb86424a.png)

# ⚠️ Disclaimer ⚠️

This project is developed in mind of learning design patterns, hexagonal architecture and a basic approach to DDD.
So I plan to release some day a stable release with basic features but the development speed is too slow by learning things and the overengineering caused by using the design patterns mentioned above.

## Build and run

Requeriments
* Make
* NodeJS >= 18
* pnpm
* Turborepo
* Openjdk 17
* Gradle

### Gradle and Pnpm

Create the ```.env``` file from the ```.env.example``` files on the directories under ```apps``` directory.

```sh
$ make env-files
```

For build the applications run the ```gradle build``` command and ```pnpm install && turbo build``` for JavaScript based applications.

You need a running instance of MongoDB, Redis and RabbitMQ. You can run this instances with the provided `docker-compose.yml`

### Docker

Create .env file for containers and put its contents

```sh
$ make env-files # if you don't run it
```

Edit the `.env.docker` for the application containers and `.env.infrastructure` for the infrastructure containers such redis, rabbitmq and mongodb.

Run all containers

```sh
$ docker compose up -d --build
```

Run tests for each application

```sh
$ make docker-backend-test # for run backend tests
$ make docker-discord-bot-test # for run discord bot tests
```

## Commands
* peko!feed (on|off) - Enable or disable YouTube notifications on the current channel

## Pekora voice
The sound triggers are configured in the new frontend (in development)
