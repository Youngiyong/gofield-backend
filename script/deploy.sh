kill `ps -ef | grep "gofield-api-user-service.jar" | grep -v grep | awk '{print $2}'`
kill `ps -ef | grep "gofield-admin.jar" | grep -v grep | awk '{print $2}'`
kill `ps -ef | grep "gofield-sns.jar" | grep -v grep | awk '{print $2}'`
nohup java -jar -Dspring.profiles.active=dev /opt/gofield/gofield-backend-deploy/gofield-api-user-service/build/libs/gofield-api-user-service.jar &
nohup java -jar -Dspring.profiles.active=dev /opt/gofield/gofield-backend-deploy/gofield-admin/build/libs/gofield-admin.jar &
nohup java -jar -Dspring.profiles.active=dev /opt/gofield/gofield-backend-deploy/gofield-sns/build/libs/gofield-sns.jar &