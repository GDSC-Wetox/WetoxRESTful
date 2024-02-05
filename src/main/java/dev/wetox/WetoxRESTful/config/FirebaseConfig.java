package dev.wetox.WetoxRESTful.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    private final FileInputStream firebaseResources;

    public FirebaseConfig(@Value("${firebase.credential}") String firebaseCredentialPath) throws IOException {
         this.firebaseResources = new FileInputStream(firebaseCredentialPath);
    }

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(
                        GoogleCredentials.fromStream(firebaseResources)
                )
                .build();
        return FirebaseApp.initializeApp(firebaseOptions);
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        return FirebaseMessaging.getInstance(firebaseApp());
    }
}
