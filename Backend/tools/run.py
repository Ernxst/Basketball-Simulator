with open("src/main/resources/application.properties", "r") as file:
    lines = [line for line in file if line.startswith("com.example.app.port=")]
    DEFAULT_PORT = lines[0].split("=")[1]
    lines = [line for line in file if line.startswith("com.example.app.title=")]
    APP_TITLE = lines[0].split("=")[1]

if __name__ == "__main__":
    import argparse
    from os import system

    parser = argparse.ArgumentParser(prog=APP_TITLE + " Backend", description='Run the {TITLE} backend as an API on localhost.'.format(TITLE=APP_TITLE)
    parser.add_argument("-port", "--port", "-p", "--p", type=str, default=DEFAULT_PORT, nargs="?", 
                    help='(optional) choose to run on a desired port - default is ' + DEFAULT_PORT)
    parser.add_argument('-l', '--l', action="store_true", help='include logging information during execution')
    parser.add_argument('-b', '-b', action="store_true", help='broadcast server IP address over network')
    parser.add_argument('-jar', '--jar', action="store_true", help="build application into a single JAR file.")
    args = parser.parse_args()

    log = " l" if args.l else ""
    broadcast = " b" if args.b else ""
    if (args.jar):
        cmd = "set -e; mvn clean package; java -jar target/Backend-1.0-SNAPSHOT.jar port={PORT}{LOG}{BROADCAST}"
    else:
        cmd = "set -e; mvn clean; mvn org.springframework.boot:spring-boot-maven-plugin:run -Dspring-boot.run.arguments=\"--port={PORT}{LOG}{BROADCAST}\""
    system(cmd.format(LOG=log, PORT=args.port, BROADCAST=broadcast))