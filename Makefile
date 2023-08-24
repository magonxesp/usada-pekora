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
	docker compose up -d mongodb redis rabbitmq

create-keys:
	@if [ ! -d "ssl" ]; then mkdir ssl; fi; \
	openssl genrsa -out ssl/private_rsa.pem 2048; \
	openssl pkcs8 -topk8 -inform PEM -outform PEM -in ssl/private_rsa.pem -out ssl/private.pem -nocrypt; \
	openssl rsa -in ssl/private.pem -outform PEM -pubout -out ssl/public.pem
