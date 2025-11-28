FROM docker:24-dind

# Install required tools
RUN apk add --no-cache openjdk17 maven nodejs npm bash
