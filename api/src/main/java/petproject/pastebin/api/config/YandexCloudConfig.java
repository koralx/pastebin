package petproject.pastebin.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class YandexCloudConfig {

    @Bean
    public S3Client yandexS3Client() {
        String accessKey = System.getenv("s3.access-key");
        String secretKey = System.getenv("s3.secret-key");
        String region = System.getenv("s3.region");
        String url = System.getenv("s3.url");

        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(AwsSessionCredentials.create(accessKey, secretKey, ""));
        return S3Client.builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.of(region))
                .endpointOverride(URI.create(url))
                .forcePathStyle(true)
                .build();
    }
}
