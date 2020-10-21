# Usada pekora
Usada pekora discord bot HA↗HA↘HA↗HA↘HA↗HA↘

[Add pekora to discord server](https://discord.com/api/oauth2/authorize?client_id=755159995720532028&permissions=3668032&scope=bot)

![pekora](https://64.media.tumblr.com/5c773af807b50ce0f223032570aae9fe/8c875e418b8e2de3-1e/s512x512u_c1/1eb8ddee17e52516a4e1f697715ef03ddb86424a.png)

## Build and run

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
Run on production mode
```sh
$ docker-compose up -d --build
```
Run as development mode for remote debug with pycharm
```sh
$ docker-compose up -f docker-compose.dev.yml -d --build
```

## Commands
* peko!live - Get if pekora is streaming now
* peko!feed (on|off) - Enable or disable youtube notifications on the current channel

## Pekora voice
See the ```pekora/pekora.yml``` for the voice configuration.