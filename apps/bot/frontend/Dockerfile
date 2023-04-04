FROM node:18.15.0-alpine

WORKDIR /app

ENV APP_ENV=prod

COPY apps/bot/frontend/package.json package.json
COPY apps/bot/frontend/yarn.lock yarn.lock

RUN yarn install

COPY apps/bot/frontend .

RUN yarn run build

CMD ["yarn", "run", "start"]
