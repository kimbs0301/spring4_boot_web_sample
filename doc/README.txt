java -server -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:+G1SummarizeConcMark -XX:InitiatingHeapOccupancyPercent=35 -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=local -jar target/spring-0.0.1-SNAPSHOT.jar

java -server -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:InitiatingHeapOccupancyPercent=35 -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=local -jar spring-0.0.1-SNAPSHOT.jar

java -server -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:InitiatingHeapOccupancyPercent=35 -Dlogging.config=logback.xml -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=svc -jar spring-0.0.1-SNAPSHOT.jar

mvn clean install -DskipTests

mvn install -DskipTests


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

curl -v "http://localhost:8080/mvc/"

curl -v -H "Accept: application/json" "http://localhost:8080/mvc/account/member/11.json"

http://localhost:8080/mvc/ajax/searchCriteria

http://localhost:8080/mvc/file/uploadForm

http://peyton.tk/index.php/post/20
