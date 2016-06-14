-Djava.security.egd=file:/dev/./urandom -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:+G1SummarizeConcMark -XX:InitiatingHeapOccupancyPercent=35

export CLASSPATH=.:/Library/Java/JavaVirtualMachines/jdk1.8.0_91.jdk/Contents/Home/lib/tools.jar
/Library/Java/JavaVirtualMachines/jdk1.8.0_91.jdk/Contents/Home/bin/java -server -jar target/spring-0.0.1-SNAPSHOT.jar

mvn install -DskipTests

cd /workspace/luna/spring4_boot_web_sample
git add -A
git commit -a -m "ok"
git push

curl -v "http://localhost:8080/mvc/"

curl -v -H "Accept: application/json" "http://localhost:8080/mvc/account/member/11.json"

http://localhost:8080/mvc/ajax/searchCriteria

http://localhost:8080/mvc/file/uploadForm
