
# For faster rebuilds:
# 1. Comment this
#FROM openjdk:latest
#RUN mkdir /build
# 2. Uncomment this
FROM workspace:latest
# 3. Run `docker build ./ -f ./Dockerfile-cache -t bigsister-cache`

# Build phase
COPY . .
RUN ./gradlew installDist
RUN mv build/install/workspace /workspace

# You also required to attach secrets.json and config.json somehow to root folder

FROM openjdk:latest
COPY --from=0 /workspace /workspace


STOPSIGNAL 9

# HEALTHCHECK CMD "sh" "-c" "curl http://localhost:$PORT/health || exit 1"

EXPOSE 8080

#AND
SHELL [ "/workspace/bin/workspace" ]
#WATCH
#YOU
#WORK

ENTRYPOINT ""