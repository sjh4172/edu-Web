package com.abroad.baekjunghyunDev.S3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abroad.baekjunghyunDev.model.video.Video;
import com.abroad.baekjunghyunDev.repository.video.VideoRepository;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import java.io.InputStream;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;
    
    @Autowired
    private VideoRepository videoRepository;
    
    // 생성자에서 AWS 자격 증명과 S3 클라이언트를 설정
    public S3Service(@Value("${spring.cloud.aws.credentials.access-key}") String accessKey, 
                     @Value("${spring.cloud.aws.credentials.secret-key}") String secretKey) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

        // AmazonS3 클라이언트를 생성
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.AP_NORTHEAST_2)  // 서울 리전 설정
                .build();
    }

    @Transactional
    // 파일 업로드 메서드
    public String uploadFile(String bucketName, String key, InputStream inputStream) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(inputStream.available());  // 파일 크기 설정
            amazonS3.putObject(bucketName, key, inputStream, metadata);
              
            return amazonS3.getUrl(bucketName, key).toString();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error uploading file to S3: " + e.getMessage());
        }
    }
    
    @Transactional
    public InputStream downloadFile(String bucketName, String key) {
        S3Object s3Object = amazonS3.getObject(bucketName, key);
        return s3Object.getObjectContent();
    }
    
}
