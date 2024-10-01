package petproject.pastebin.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class YandexCloudService {

    @Autowired
    private S3Client yandexS3Client;

    public void upload(String key, String content) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket("spring-test").key(key).build();
        yandexS3Client.putObject(putObjectRequest, RequestBody.fromString(content));
    }

    public String download(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket("spring-test").key(key).build();

        //client.getObjectAsBytes(getObjectRequest);
        ResponseBytes<GetObjectResponse> objectBytes = yandexS3Client.getObjectAsBytes(getObjectRequest);
        byte[] data = objectBytes.asByteArray();

        return new String(data);
    }
}
