package UniFest.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FcmConfig {

    private final String fcmKeyPath;

    public FcmConfig(@Value("${spring.firebase.key-path}") String fcmKeyPath) {
        this.fcmKeyPath = fcmKeyPath;
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        ClassPathResource resource = new ClassPathResource(fcmKeyPath);
        InputStream refreshToken = resource.getInputStream();
        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();
        if (firebaseAppList != null && !firebaseAppList.isEmpty()) {
            for (FirebaseApp app : firebaseAppList) {
                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                    firebaseApp = app;
                }
            }
        } else {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
