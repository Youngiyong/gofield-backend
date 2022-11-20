sudo su -

pkill -9 -f /opt/gofield/gofield-backend-deploy/gofield-sns/build/libs/gofield-sns.jar
pkill -9 -f /opt/gofield/gofield-backend-deploy/gofield-admin/build/libs/gofield-admin.jar
pkill -9 -f /opt/gofield/gofield-backend-deploy/gofield-api-user-service/build/libs/gofield-api-user-service.jar

java -jar -Dspring.profiles.active=dev /opt/gofield/gofield-backend-deploy/gofield-admin/build/libs/gofield-admin.jar
java -jar -Dspring.profiles.active=dev /opt/gofield/gofield-backend-deploy/gofield-sns/build/libs/gofield-sns.jar
java -jar -Dspring.profiles.active=dev /opt/gofield/gofield-backend-deploy/gofield-api-user-service/build/libs/gofield-api-user-service.jar

echo 'success!'