.PHONY: secrets env-files create-keys

secrets:
	@if [ ! -d "secrets" ]; then mkdir secrets; fi; \
    if [ ! -f "secrets/discord_bot_token.txt" ]; then touch secrets/discord_bot_token.txt; fi; \
    if [ ! -f "secrets/discord_client_id.txt" ]; then touch secrets/discord_client_id.txt; fi; \
    if [ ! -f "secrets/discord_client_secret.txt" ]; then touch secrets/discord_client_secret.txt; fi; \
    if [ ! -f "secrets/test_discord_channel_id.txt" ]; then touch secrets/test_discord_channel_id.txt; fi; \
    if [ ! -f "secrets/test_discord_guild_id.txt" ]; then touch secrets/test_discord_guild_id.txt; fi; \
    if [ ! -f "secrets/mongodb_username.txt" ]; then echo "pekora" > secrets/mongodb_username.txt; fi; \
    if [ ! -f "secrets/mongodb_password.txt" ]; then date | md5sum | head -c 25 > secrets/mongodb_password.txt; fi; \
    if [ ! -f "secrets/mongodb_connection_url.txt" ]; then echo "mongodb://pekora:$(cat secrets/mongodb_password.txt)@mongodb:27017" > secrets/mongodb_connection_url.txt; fi

env-files:
	@if [ ! -f "apps/bot/backend/.env" ]; then cp apps/bot/backend/.env.example apps/bot/backend/.env; fi
	@if [ ! -f "apps/bot/discord-bot/.env" ]; then cp apps/bot/discord-bot/.env.example apps/bot/discord-bot/.env; fi
	@if [ ! -f "apps/bot/frontend/.env" ]; then cp apps/bot/frontend/.env.example apps/bot/frontend/.env; fi

docker-backend-test:
	@if [ ! -d "reports/backend" ]; then mkdir -p "reports/backend"; fi; \
	export COMPOSE_PROJECT_NAME="$$(basename $$(pwd))-test"; \
	docker compose -f docker-compose.yml -f docker-compose.test.yml run --build backend gradle :contexts:bot:test :apps:bot:backend:test && \
	docker compose -f docker-compose.yml -f docker-compose.test.yml down -v

docker-discord-bot-test:
	@if [ ! -d "reports/discord-bot" ]; then mkdir -p "reports/discord-bot"; fi; \
	export COMPOSE_PROJECT_NAME="$$(basename $$(pwd))-test"; \
	docker compose -f docker-compose.yml -f docker-compose.test.yml run --build backend gradle :contexts:bot:test :apps:bot:discord-bot:test && \
	docker compose -f docker-compose.yml -f docker-compose.test.yml down -v

docker-up-infrastructure:
	docker compose up -d mongodb redis

create-keys:
	@if [ ! -d "ssl" ]; then mkdir ssl; fi; \
	openssl genrsa -out ssl/private.pem 2048; \
	openssl rsa -in ssl/private.pem -outform PEM -pubout -out ssl/public.pem
