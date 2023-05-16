# Usada pekora
Usada pekora discord bot, notify new videos from usada pekora youtube channel and reacts to text messages HA↗HA↘HA↗HA↘HA↗HA↘

[Add pekora to discord server](https://discord.com/api/oauth2/authorize?client_id=755159995720532028&permissions=3668032&scope=bot)

![pekora](https://64.media.tumblr.com/5c773af807b50ce0f223032570aae9fe/8c875e418b8e2de3-1e/s512x512u_c1/1eb8ddee17e52516a4e1f697715ef03ddb86424a.png)

## Build and run

Requeriments
* Make
* NodeJS >= 18
* pnpm
* Openjdk 18
* Gradle

### Gradle and Yarn

Create the ```.env``` file from the ```.env.example``` files on the directories under ```apps``` directory.

```sh
$ make env-files
```

For build the applications run the ```gradle build``` command and ```yarn install && yarn build``` for JavaScript based applications.

You need a running instance of MongoDB and Redis.

### Docker

Create secrets files and put its contents

```sh
$ make secrets
```

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
* peko!feed (on|off) - Enable or disable Youtube notifications on the current channel

## Pekora voice
The sound triggers are configured in the new frontend (in development)

## Environment variables

### For each application

| Name        | Description                         | Example values |
|-------------|-------------------------------------|----------------|
| **APP_ENV** | The application current environment | develop, prod  |

### For each application excluding frontend

Supports append the _FILE suffix for docker secrets

| Name                             | Description                                                                                                 | Example values                               |
|----------------------------------|-------------------------------------------------------------------------------------------------------------|----------------------------------------------|
| **STRAPI_BASE_URL** (deprecated) | The Strapi base url                                                                                         | http://localhost:1337                        |
| **STRAPI_TOKEN** (deprecated)    | The Strapi api token                                                                                        | -                                            |
| **DISCORD_BOT_TOKEN**            | The discord bot token                                                                                       | -                                            |
| **BACKEND_BASE_URL**             | The backend application base url                                                                            | http://localhost:8080                        |
| **YOUTUBE_CHANNEL_ID**           | The Youtube channel id on will subscribe to its notifications                                               | UC1DCedRgGHBdm81E1llLhOQ                     |
| **MONGODB_URL**                  | The MongoDB jdbc connection url                                                                             | mongodb://example:example@localhost:27017    |
| **MONGODB_DATABASE**             | The MongoDB database name                                                                                   | usada_pekora                                 |
| **REDIS_HOST**                   | The Redis server hostname                                                                                   | localhost                                    |
| **REDIS_PORT**                   | The Redis server port                                                                                       | 6379                                         |
| **STORAGE_DIR_PATH**             | The application storage directory path, relative directory path to the application workdir or absolute path | storage, data, /path/to/usada_pekora/storage |

### For specific application

* Frontend

| Name                                                        | Description                                                        | Example values                        |
|-------------------------------------------------------------|--------------------------------------------------------------------|---------------------------------------|
| **DISCORD_CLIENT_ID** or **DISCORD_CLIENT_ID_FILE**         | The Discord OAUTH client id or file path containing the secret     | -                                     |
| **DISCORD_CLIENT_SECRET** or **DISCORD_CLIENT_SECRET_FILE** | The Discord OAUTH client secret or file path containing the secret | -                                     |
| **NEXTAUTH_URL**                                            | The canonical url of the website                                   | https://example.com, http://localhost |
| **NEXTAUTH_SECRET**                                         | The secret value for encrypt the NextAuth.js JWT                   | - (base64 string or something random) |
| **NEXT_PUBLIC_BACKEND_BASE_URL**                            | The base url of the backend API                                    | http://localhost:8080                 |
