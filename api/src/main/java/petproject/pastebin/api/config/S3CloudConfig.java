package petproject.pastebin.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class S3CloudConfig {

    @Value("${s3.access-key}")
    private String ACCESS_KEY;
    @Value("${s3.secret-key}")
    private String SECRET_KEY;
    @Value("${s3.region}")
    private String REGION;
    @Value("${s3.url}")
    private String URL;

    @Bean
    public S3Client s3Client() {
        String accessKey = ACCESS_KEY;
        String secretKey = SECRET_KEY;
        String region = REGION;
        String url = URL;

        if (accessKey == null || secretKey == null || region == null || url == null) {
            throw new Error("S3 authentication data not set");
        }

        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(AwsSessionCredentials.create(accessKey, secretKey, ""));
        return S3Client.builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.of(region))
                .endpointOverride(URI.create(url))
                .forcePathStyle(true)
                .build();
    }

    @Bean
    public String s3bucket() {
        String bucket = System.getenv("s3.bucket");

        if (bucket == null) {
            throw new Error("S3 bucket not set");
        }

        return bucket;
    }
}
