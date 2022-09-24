FROM node:16.17.0-alpine

WORKDIR /app

COPY . .

RUN yarn install && yarn build

CMD ["node", ".output/server/index.mjs"]
