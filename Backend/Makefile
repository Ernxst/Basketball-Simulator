env = development
env_file = .env.$(env)
build =
silent =
build_flag=
silent_flag=

ifdef build
	build_flag = --build
endif

ifdef silent
	silent_flag = --detach
endif

compose_file = docker-compose.override.yml
ifeq ($(env), production)
	compose_file = docker-compose.production.yml
endif
compose = docker-compose -f docker-compose.yml -f $(compose_file) --env-file $(env_file)
exec = $(compose) exec app

all:
	make up build=1 silent=$(silent)
build:
	${compose} build $(silent_flag) --build-arg ENV=$(env)
up:
	${compose} up --abort-on-container-exit --renew-anon-volumes --remove-orphans $(build_flag) $(silent_flag)
down:
	${compose} down
config:
	${compose} config
db:
	${compose} up --abort-on-container-exit --renew-anon-volumes --remove-orphans $(build_flag) $(silent_flag) db
env:
	export $(cat .env.development | xargs)
junit:
	make env
	mvn surefire-report:report && make server
unit-test:
	make env
	mvn test
coverage:
	make env
	mvn jacoco:prepare-agent test jacoco:report && make server
pmd:
	make env
	mvn pmd:pmd && make server
server:
	cd target/site/ && python3 -m http.server
bash:
	${exec} sh
wait:
	until nc -w 10 127.0.0.1 $(port); do sleep 1; done
ci:
	set -e
	make build
	make unit-test
prune:
	make down
	docker container prune -f
	docker volume prune -f
	docker image prune -f
swagger:
	set -e
	make down
	make build
	make up & open "http://localhost:8100/swagger-ui/index.html"
deploy:
	docker buildx build --platform linux/amd64 --build-arg ENV=production -t app .
	docker tag app registry.heroku.com/basketball-simulator-web/web
	docker push registry.heroku.com/basketball-simulator-web/web
	heroku container:release web -a basketball-simulator-web
push:
	git push production main
merge:
	git pull origin main
	git push
reset:
	heroku pg:reset -a --confirm basketball-simulator-web
.PHONY: list
list:
	@LC_ALL=C $(MAKE) -pRrq -f $(lastword $(MAKEFILE_LIST)) : 2>/dev/null | awk -v RS= -F: '/^# File/,/^# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | egrep -v -e '^[^[:alnum:]]' -e '^$@$$'