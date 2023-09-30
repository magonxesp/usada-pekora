FROM node:18.15.0-alpine

WORKDIR /APP

ENV APP_ENV=prod

RUN mkdir -p apps/auth/frontend \
    && mkdir -p packages/ui

COPY package.json .
COPY package-lock.json .
COPY apps/auth/frontend/package.json apps/auth/frontend/package.json
COPY packages/ui/package.json packages/ui/package.json

RUN npm install

COPY . .

RUN npm run -w packages/ui build \
    && chmod +x apps/auth/frontend/entrypoint.sh

EXPOSE 3000

CMD ["apps/auth/frontend/entrypoint.sh"]
