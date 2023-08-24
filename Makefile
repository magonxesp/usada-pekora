.PHONY: secrets env-files create-keys

env-files:
	@if [ ! -f ".env" ]; then cp .env.example .env; fi
	@if [ ! -f ".env.docker" ]; then cp .env.example .env.docker; fi
	@if [ ! -f ".env.infrastructure" ]; then cp .env.infrastructure.example .env.infrastructure; fi
	@if [ ! -f "apps/bot/backend/.env" ]; then ln -s ../../../.env apps/bot/backend/.env; fi
	@if [ ! -f "apps/bot/discord-bot/.env" ]; then ln -s ../../../.env apps/bot/discord-bot/.env; fi
	@if [ ! -f "apps/bot/frontend/.env" ]; then ln -s ../../../.env apps/bot/frontend/.env; fi
	@if [ ! -f "apps/auth/backend/.env" ]; then ln -s ../../../.env apps/auth/backend/.env; fi
	@if [ ! -f "apps/auth/frontend/.env" ]; then ln -s ../../../.env apps/auth/frontend/.env; fi

docker-bot-backend-test:
	@if [ ! -d "reports/bot/backend" ]; then mkdir -p "reports/bot/backend"; fi; \
	if [ ! -d "reports/bot/context" ]; then mkdir -p "reports/bot/context"; fi; \
	if [ ! -d "reports/shared" ]; then mkdir -p "reports/shared"; fi; \
	export COMPOSE_PROJECT_NAME="$$(basename $$(pwd))-test"; \
	docker compose -f docker-compose.yml -f docker-compose.test.yml run --build bot_backend gradle :contexts:shared:test :contexts:bot:test :apps:bot:backend:test && \
	docker compose -f docker-compose.yml -f docker-compose.test.yml down -v

docker-bot-discord-bot-test:
	@if [ ! -d "reports/bot/discord-bot" ]; then mkdir -p "reports/bot/discord-bot"; fi; \
    if [ ! -d "reports/bot/context" ]; then mkdir -p "reports/bot/context"; fi; \
    if [ ! -d "reports/shared" ]; then mkdir -p "reports/shared"; fi; \
	export COMPOSE_PROJECT_NAME="$$(basename $$(pwd))-test"; \
	docker compose -f docker-compose.yml -f docker-compose.test.yml run --build bot_discord_bot gradle :contexts:shared:test :contexts:bot:test :apps:bot:discord-bot:test && \
	docker compose -f docker-compose.yml -f docker-compose.test.yml down -v

docker-auth-backend-test:
	@if [ ! -d "reports/auth/backend" ]; then mkdir -p "reports/auth/backend"; fi; \
    if [ ! -d "reports/auth/context" ]; then mkdir -p "reports/auth/context"; fi; \
    if [ ! -d "reports/shared" ]; then mkdir -p "reports/shared"; fi; \
	export COMPOSE_PROJECT_NAME="$$(basename $$(pwd))-test"; \
	docker compose -f docker-compose.yml -f docker-compose.test.yml run --build auth_backend gradle :contexts:shared:test :contexts:auth:test :apps:auth:backend:test && \
	docker compose -f docker-compose.yml -f docker-compose.test.yml down -v

docker-up-infrastructure:
	docker compose up -d shared_mongodb shared_redis shared_rabbitmq

create-keys:
	@if [ ! -d "ssl" ]; then mkdir ssl; fi; \
	openssl genrsa -out ssl/private_rsa.pem 2048; \
	openssl pkcs8 -topk8 -inform PEM -outform PEM -in ssl/private_rsa.pem -out ssl/private.pem -nocrypt; \
	openssl rsa -in ssl/private.pem -outform PEM -pubout -out ssl/public.pem
