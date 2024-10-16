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
public class S3CloudService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private String s3Bucket;

    public void upload(String key, String content) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(s3Bucket).key(key).build();
        s3Client.putObject(putObjectRequest, RequestBody.fromString(content));
    }

    public String download(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(s3Bucket).key(key).build();

        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
        byte[] data = objectBytes.asByteArray();

        return new String(data);
    }
}
