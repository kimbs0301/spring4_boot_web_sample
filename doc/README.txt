이클립스 VM arguments
-server -Xms256m -Xmx256m -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:InitiatingHeapOccupancyPercent=35 -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=local

java -server -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:+G1SummarizeConcMark -XX:InitiatingHeapOccupancyPercent=35 -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=local -jar target/spring-0.0.1-SNAPSHOT.jar

java -server -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:InitiatingHeapOccupancyPercent=35 -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=local -jar spring-0.0.1-SNAPSHOT.jar

java -server -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:InitiatingHeapOccupancyPercent=35 -Dlogging.config=logback.xml -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=svc -jar spring-0.0.1-SNAPSHOT.jar

mvn clean install -DskipTests

mvn install -DskipTests

mvn dependency:tree


배포시 디렉터리 구조
./file/ssl
./logback.xml
./spring-0.0.1-SNAPSHOT.jar
./src/main/webapp/WEB-INF
./src/main/webapp/static


cd /workspace/luna/spring4_boot_web_sample
git add -A
git commit -a -m "ok"
git push


for ((i=1;i<=100000;i++)); do curl -H "Content-Type:application/json; charset=utf-8" -H "Accept: application/json; charset=utf-8" -d '{"id":1,"username":"KKK"}' "http://localhost:8080/mvc/json/data.json"; echo "
"; done

for ((i=1;i<=100000;i++)); do curl -X POST -H "Content-Type:application/xml; charset=utf-8" -H "Accept: application/xml" -d '<?xml version="1.0" encoding="UTF-8"?><user><id>1</id></user>' "http://localhost:8080/mvc/xml/data.xml"; echo "
"; done



#! /bin/bash
./redis-server redis.6379.conf > /dev/null 2>&1&
#! /bin/bash
./redis-server redis.6380.conf > /dev/null 2>&1&
#! /bin/bash
./redis-server redis.6381.conf > /dev/null 2>&1&

참고 URL :
http://peyton.tk/index.php/post/20
https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples
https://blog.outsider.ne.kr/882
http://egloos.zum.com/kwon37xi/v/4886947
https://github.com/kwon37xi/ChainedTransactionManagerTest
