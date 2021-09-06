env = development
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

compose = docker-compose
ifeq ($(env), production)
	compose = docker-compose -f docker-compose.yml -f docker-compose.production.yml
endif
exec = $(compose) exec app

all:
	make up build=1 silent=$(silent)
build:
	${compose} build $(silent_flag)
up:
	${compose} up --abort-on-container-exit --renew-anon-volumes $(build_flag) $(silent_flag)
down:
	${compose} down
junit:
	${exec} mvn surefire-report:report && ${exec} make server
unit-test:
	${exec} mvn test
coverage:
	mvn jacoco:prepare-agent test jacoco:report && make server
pmd:
	mvn pmd:pmd &&  make server
server:
	${exec} cd target/site/ && ${exec} python3 -m http.server
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
deploy:
	docker buildx build --platform linux/amd64 -t app .
	docker tag app registry.heroku.com/basketball-simulator-app/web
	docker push registry.heroku.com/basketball-simulator-app/web
	heroku container:release web -a basketball-simulator-app
reset:
	heroku pg:reset -a --confirm basketball-simulator-app
.PHONY: list
list:
	@LC_ALL=C $(MAKE) -pRrq -f $(lastword $(MAKEFILE_LIST)) : 2>/dev/null | awk -v RS= -F: '/^# File/,/^# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | egrep -v -e '^[^[:alnum:]]' -e '^$@$$'