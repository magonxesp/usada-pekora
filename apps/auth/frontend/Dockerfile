FROM node:18.15.0-alpine

WORKDIR /APP

ENV APP_ENV=prod

RUN corepack prepare pnpm@latest --activate
RUN corepack enable
RUN npm install turbo --global

COPY . .

RUN pnpm install && chmod +x apps/auth/frontend/entrypoint.sh

EXPOSE 3000

CMD ["apps/auth/frontend/entrypoint.sh"]
