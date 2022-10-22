sudo pkill -9 -f /opt/gofield/gofield-backend-deploy/gofield-api-user-service/build/libs/gofield-api-user-service.jar
sudo pkill -9 -f /opt/gofield/gofield-backend-deploy/gofield-admin/build/libs/gofield-admin.jar
sudo pkill -9 -f /opt/gofield/gofield-backend-deploy/gofield-sns/build/libs/gofield-sns.jar
nohup java -jar -Dspring.profiles.active=dev /opt/gofield/gofield-backend-deploy/gofield-api-user-service/build/libs/gofield-api-user-service.jar &
nohup java -jar -Dspring.profiles.active=dev /opt/gofield/gofield-backend-deploy/gofield-admin/build/libs/gofield-admin.jar &
nohup java -jar -Dspring.profiles.active=dev /opt/gofield/gofield-backend-deploy/gofield-sns/build/libs/gofield-sns.jar &