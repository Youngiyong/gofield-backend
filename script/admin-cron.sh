#!/bin/bash

echo "Start Spring Boot Application!"
CURRENT_PID=$(ps -ef | grep java | grep gofield-admin.jar | awk '{print $2}')
echo "$CURRENT_PID"

if [ -z $CURRENT_PID ];
then
       java -jar -Dspring.profiles.active=dev /opt/gofield/gofield-backend-deploy/gofield-admin/build/libs/gofield-admin.jar
else
        echo "구동중"
fi