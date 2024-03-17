FROM alpine:latest
LABEL authors="sandman"

WORKDIR /app

RUN apk add --no-cache transmission-cli

RUN mkdir -p /app/data
VOLUME /app/data

ENTRYPOINT ["transmission-cli"]