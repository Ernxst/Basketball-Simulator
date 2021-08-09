# Backend API

Source code for the backend of the **Basketball Simulator** web app accessed via a restful API running on `localhost`.

## Table of Contents

* [**Dependencies**](#dependencies)
* [**Development**](#development)
* [**Testing**](#testing)
* [**Deployment**](#deployment)
* [**Makefile**](#makefile)
* [**Production Deployment**](#production_deployment)
* [**Documentation**](#documentation)
* [**License**](#license)

## Dependencies

The following dependencies are required for development:

* [**Docker**](https://docs.docker.com/get-docker/)
* [TODO] ADD DEPENDENCIES

## Development

For development (and testing), the container must be built and run using the commands in the ["Deployment"](#deployment)
section - the app does not work outside the container unless ... [TBC]

Any external migrations made to the database require both the Docker containers and volumes to be purged.

First, stop the application:

```bash
docker stop backend
```

Then, list all running containers:

```bash
docker container ls -a
```

This should produce something like:

```bash
[TODO]
```

Plus any other containers you may have running.

Now, one by one:

```bash
docker container rm [ID]
```

And replace `[ID]` with the values shown in the `CONTAINER ID` column.

Now, the same must be done for the docker volumes:

List all persisted volumes:

```bash
docker volume ls
```

This should produce something like:

```bash
[TODO]
```

Plus any other volumes you may have on your system.

Now, one by one:

```bash
docker volume rm [VOLUME]
```

And replace `[VOLUME]` with the values shown in the `VOLUME NAME` column.

Alternatively, if you only have these containers on your system, you can use:

```bash
docker container prune
```

And enter `y` when prompted to delete the containers at once.

And for volumes (if you only have volumes for this application):

```bash
docker volume prune
```

Entering `y` when prompted.

Again, this is recommended only if you have docker containers and volumes related to this application stored on your
system, and nothing else.

### Running a Shell

To run a shell in the container, assuming it is already running:

```bash
docker -ti backend /bin/bash
```

And quit it using:

```bash
exit
```

When in a shell in the container, the prefix `docker exec backend` is not needed.

## Testing

To run the test suites ([assuming the container is running](#deployment)):

```bash
docker exec backend mvn test
```

This will generate a coverage report with a summary printed in the terminal.

To see the full coverage report in a browser window:

```bash
open coverage/index.html
```

Note that this command is not available inside the container.

## Deployment

The application is containerised to make for simple deployment.

To start the application in a Docker container, you must first build the image with:

```bash
docker build --tag backend .
```

And then to start the application on port `8100`:

```bash
docker run --rm -it -p 8100:8100 backend backend
```

## Makefile

A `Makefile` is included to shorten common `Docker` commands to the following:

* Note that if a `make` action has arguments (all optional), a value must be given to it.
* The `env` argument can either be `dev` or `prod`. If no `env` is provided, it defaults to the development (`dev`)
  environment.

## Production Deployment

TBC

## Documentation

TBC

## License

TBC