FROM java:8
COPY build/libs/heroku-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Durl.webhook=https://discordapp.com/api/webhooks/","-jar","/app.jar"]

