FROM java:8
COPY build/libs/heroku-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Durl.webhook=https://discordapp.com/api/webhooks/","-jar","/app.jar"]

