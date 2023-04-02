FROM node:18.15.0-alpine

WORKDIR /app

ENV APP_ENV=prod

COPY app/frontend/package.json package.json
COPY app/frontend/yarn.lock yarn.lock

RUN yarn install

COPY app/frontend .

RUN yarn run build

CMD ["yarn", "run", "start"]
