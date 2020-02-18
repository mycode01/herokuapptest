# heroku deploy test
heroku 배포 테스트 및 디스코드 웹훅 테스트.

두가지 테스트를 진행하였음.    
첫번째, 헤로쿠는 프리플랜에서는 request가 없을때 30분마다 서버를 멈추고 다음번 request가 들어올때 서버를 올리느라 매우느리게 반응할때가 있음.    
이를 해소하기 위해 https://uptimerobot.com/ 에 가입, 20분마다 uptime 체크를 위해서 /health 에 request를 쏘게 세팅하고, 잘 작동하는지 확인함.

두번째 테스트는 디스코드로 정해진 시간마다 웹훅을 쏘는 테스트였는데, 쓸만한 라이브러리가 없어서 GIthub에 올라와있는 아무 소스 복사해다가 사용했음.    
잘되긴 하는데, 중간에 이스케이핑이 필요한 문자가 들어가면 400에러가 발생함. 아무래도 json serialize시에 문제가 발생하는듯 함.


## Before Test
디스코드 웹훅 + 스케쥴러 테스트를 위해서는 
```java
@EnableScheduling
@Scheduled(fixedDelay = 3000000L)
```
를 주석해제한다.

```java
@Value("${url.webhook}")
private String url;
```
를 주석해제 하든지, webhook에 쓰일 URl을 --url.webhook 실행인수로 준다.
heroku 실행시엔 어떻게 해야하나 싶었는데, 
``` bash
heroku config:set url.webhook=https://discordapp.com/api/webhooks/6752...
```
일케 주면 됨. 


## Run 
heroku cli 설치 후 
```bash
heroku login
heroku git:remote -a HEROKU_APP_NAME
git push heroku master
```
로 배포 끝. 로그 확인은 
```bash
heroku logs --tail 
```
