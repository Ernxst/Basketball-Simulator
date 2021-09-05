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

all:
	make up build=1 silent=$(silent)
build:
	docker-compose build $(silent_flag)
up:
	docker-compose up --abort-on-container-exit --renew-anon-volumes $(build_flag) $(silent_flag)
down:
	docker-compose down
junit:
	mvn surefire-report:report && make server
unit-test:
	mvn test
coverage:
	mvn jacoco:prepare-agent test jacoco:report && make server
pmd:
	mvn pmd:pmd &&  make server
server:
	cd target/site/ && python3 -m http.server
bash:
	docker -ti web /bin/bash
wait:
	until nc -w 10 127.0.0.1 $(port); do sleep 1; done
ci: # TODO - Fix, run mvn test in container
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