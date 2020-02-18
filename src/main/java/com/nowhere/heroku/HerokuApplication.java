package com.nowhere.heroku;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.net.InetAddress;

@SpringBootApplication
//@EnableScheduling
@RestController
public class HerokuApplication {

    public static void main(String[] args) {
        SpringApplication.run(HerokuApplication.class, args);
    }


    @Value("${url.webhook}")
    private String url;


    @GetMapping("health")
    public String health() {
        return "ok";
    }

    @GetMapping("info")
    public String info() throws Exception {
        return InetAddress.getLocalHost().getHostName() + "@" + InetAddress.getLocalHost().getHostAddress();
    }

    //    @Scheduled(fixedDelay = 3000000L)
    public void webhook() throws Exception {
        DiscordWebhook webhook = new DiscordWebhook(url);
        webhook.setContent("Any message!");
//        webhook.setAvatarUrl("https://your.awesome/image.png");
        webhook.setUsername("Custom Usernames!");
        webhook.setTts(true);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("Title")
                .setDescription("This is a description")
                .setColor(Color.RED)
                .addField("1st Field", "Inline", true)
                .addField("2nd Field", "Inline", true)
                .addField("3rd Field", "No-Inline", false)
//                .setThumbnail("data:image/png;base64,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")
                .setFooter("Footer text", "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/240/microsoft/209/reversed-hand-with-middle-finger-extended_1f595.png")
                .setImage("https://pds.joins.com/news/component/htmlphoto_mmdata/202001/01/be765ede-3343-4b54-be52-a2e152554fcd.jpg")
                .setAuthor("Author Name", "https://locallhost.com/", "https://lh3.googleusercontent.com/mcOMRbQqdYCb87d8eHrTJVrTnalulDbxOx4y9tZHXKcGE6ruIJ_siWQZnT14esotFQ=s180")
                .setUrl("https://locallhost.com/"));
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription("Just another added embed object!"));
        webhook.execute(); //Handle exception
    }
}
