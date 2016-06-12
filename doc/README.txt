cd /workspace/luna/spring4_boot_web_sample
git add -A
git commit -a -m "ok"
git push

curl -v "http://localhost:8080/mvc/"

curl -v -H "Accept: application/json" "http://localhost:8080/mvc/account/member/11.json"

http://localhost:8080/mvc/ajax/searchCriteria

http://localhost:8080/mvc/file/uploadForm
