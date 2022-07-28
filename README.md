# Usada pekora
Usada pekora discord bot HA↗HA↘HA↗HA↘HA↗HA↘

[Add pekora to discord server](https://discord.com/api/oauth2/authorize?client_id=755159995720532028&permissions=3668032&scope=bot)

![pekora](https://64.media.tumblr.com/5c773af807b50ce0f223032570aae9fe/8c875e418b8e2de3-1e/s512x512u_c1/1eb8ddee17e52516a4e1f697715ef03ddb86424a.png)

## Build and run

### Backend

#### Dependencies

* Node <= 16

#### Setup

Copy the ```.env.example``` file to ```.env```

```sh
$ cp backend/.env.example backend/.env
```

And replace all secrets and keys by a random base64 string.

NOTE: The base64 can be generated by node

```sh
$ node -e "console.log(crypto.randomBytes(16).toString('base64'))"
```

Install dependencies and run the dev server.

```sh
$ npm install
$ npm run develop
```

### Discord bot

Create the ```.env``` file 
```sh
$ cp .env.example .env
```
Set the discord bot token
```sh
# .env file
TOKEN=your_discord_bot_token
GOOGLE_API_KEY=your_google_api_key # for the peko!live streaming status command
HTTP_BASE_URL=http://example.com # domain for the http server (youtube feed webhook)
```

### Docker

For local development just copy the ```docker-compose.local.yml``` located in ```examples/docker``` to ```docker-compose.override.yml``` on the repository root directory.
```sh
$ cp examples/docker/docker-compose.local.yml docker-compose.override.yml
```

And then up all containers
```sh
$ docker-compose up -d --build
```

**NOTE:** If you don't have the ```docker-compose.override.yml``` file the containers will run on production mode by default

## Commands
* peko!live - Get if pekora is streaming now
* peko!feed (on|off) - Enable or disable youtube notifications on the current channel

## Pekora voice
See the ```pekora/pekora.yml``` for the voice configuration.