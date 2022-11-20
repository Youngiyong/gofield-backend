pkill -9 -f /opt/gofield/gofield-backend-deploy/gofield-sns/build/libs/gofield-sns.jar

nohup java -jar -Dspring.profiles.active=dev /opt/gofield/gofield-backend-deploy/gofield-sns/build/libs/gofield-sns.jar
