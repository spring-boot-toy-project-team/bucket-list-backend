# bucket-list-backend
<hr>

## 사용 기술 & 사용 스택

+ Spring boot
+ DB : PostgreSQL, Redis
+ OAuth2.0
+ JPA

<hr>

<div>DB : https://www.erdcloud.com/d/bWGmmorjCfKTMcTWy</div>
<div>UI/UX : https://www.figma.com/file/8usXJvCka7qSB3lWNmMEhR/Bucket-list?node-id=0%3A1<div>
<div>기능 설계 : https://docs.google.com/spreadsheets/d/1aSE4A_02oGwybr7PBaCleb6wUatf5GIrMhnAU98h3qs/edit#gid=0</div>
  
<hr>
  
## BackEnd 테스트 환경 실행 방법
  
+ **Redis 실행**
  + 해당 경로로 이동 : `cd DB/Redis`
  + docker-compose 실행 : `docker-compose up -d`
+ **application 실행**  
  + 해당 경로로 이동 : `cd list`
  + docker 이미지 빌드 & docker-compose 실행 : `docker-compose up --build -d`
  

