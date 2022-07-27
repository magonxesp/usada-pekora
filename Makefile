.PHONY: dc-dev \
	dc-dev-build

dc-dev:
	docker-compose -f docker-compose.override.yml -f docker-compose.yml up -d

dc-dev-build:
	docker-compose -f docker-compose.override.yml -f docker-compose.yml up -d --build
