all:
	set -e
	make build
	make run
build:
	docker build -t app .
run:
	docker run --rm -it -p 8100:8100 app
stop:
	docker stop app
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
	make stop
	docker container prune -f
	docker volume prune -f