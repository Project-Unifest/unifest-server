package UniFest.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Configuration
public class FcmConfig {

    private final String fcmKey;

    public FcmConfig(@Value("${spring.firebase.key:#{null}}") String fcmKey) {
        this.fcmKey = fcmKey;
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        if (fcmKey == null || fcmKey.isEmpty()) {
            throw new IllegalArgumentException("Firebase key is not set");
        }

        try (InputStream refreshToken = new ByteArrayInputStream(fcmKey.getBytes())) {
            FirebaseApp firebaseApp = null;
            List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();
            if (firebaseAppList != null && !firebaseAppList.isEmpty()) {
                for (FirebaseApp app : firebaseAppList) {
                    if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                        firebaseApp = app;
                    }
                }
            }

            if (firebaseApp == null) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(refreshToken))
                        .build();
                firebaseApp = FirebaseApp.initializeApp(options);
            }

            return FirebaseMessaging.getInstance(firebaseApp);
        }
    }
}
