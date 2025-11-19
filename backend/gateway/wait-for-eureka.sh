#!/bin/sh

EUREKA_URL="http://discovery:8761/eureka/apps"

echo "Waiting for Eureka at $EUREKA_URL..."

# wait until Eureka responds
until curl -s $EUREKA_URL > /dev/null; do
  echo "Eureka not ready yet... retrying in 5 seconds"
  sleep 5
done

echo "Eureka is up! Starting Gateway..."
exec java -jar app.jar
