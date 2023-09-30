FROM node:18.15.0-alpine

WORKDIR /APP

ENV APP_ENV=prod

RUN mkdir -p apps/bot/frontend \
    && mkdir -p packages/ui

COPY package.json .
COPY package-lock.json .
COPY apps/bot/frontend/package.json apps/bot/frontend/package.json
COPY packages/ui/package.json packages/ui/package.json

RUN npm install

COPY . .

RUN npm run -w packages/ui build \
    && chmod +x apps/bot/frontend/entrypoint.sh

EXPOSE 3001

CMD ["apps/bot/frontend/entrypoint.sh"]
