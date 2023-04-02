.PHONY: secrets env-files

secrets:
	@if [ ! -d "secrets" ]; then mkdir secrets; fi
	@if [ ! -f "secrets/discord_bot_token.txt" ]; then touch secrets/discord_bot_token.txt; fi
	@if [ ! -f "secrets/discord_client_id.txt" ]; then touch secrets/discord_client_id.txt; fi
	@if [ ! -f "secrets/discord_client_secret.txt" ]; then touch secrets/discord_client_secret.txt; fi
	@if [ ! -f "secrets/mongodb_username.txt" ]; then touch secrets/mongodb_username.txt; fi
	@if [ ! -f "secrets/mongodb_password.txt" ]; then touch secrets/mongodb_password.txt; fi
	@if [ ! -f "secrets/mongodb_connection_url.txt" ]; then touch secrets/mongodb_connection_url.txt; fi

env-files:
	@if [ ! -f "app/backend/.env" ]; then cp app/backend/.env.example app/backend/.env; fi
	@if [ ! -f "app/discord-bot/.env" ]; then cp app/discord-bot/.env.example app/discord-bot/.env; fi
	@if [ ! -f "app/frontend/.env" ]; then cp app/frontend/.env.example app/frontend/.env; fi
