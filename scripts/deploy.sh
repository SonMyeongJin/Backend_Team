#!/bin/bash

echo "> Checking pid of the running application..."

APP_BASE_PATH=/home/ubuntu/app/

CURRENT_PID=$(pgrep -f i-like-this-page)

echo "PID = $CURRENT_PID"

if [ -z $CURRENT_PID ]; then
    echo "> There is no running application."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> Deploying a new application..."

echo "> Copy jar files"

cp $APP_BASE_PATH/deploy/build/libs/*.jar $APP_BASE_PATH/jar/

JAR_NAME=$(ls -tr $APP_BASE_PATH/jar/ | grep 'i-like-this-page' | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar $APP_BASE_PATH/jar/$JAR_NAME &