package com.abroad.baekjunghyunDev.S3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abroad.baekjunghyunDev.config.auth.PrincipalDetail;
import com.abroad.baekjunghyunDev.dto.ResponseDto;
import com.abroad.baekjunghyunDev.model.video.Video;
import com.abroad.baekjunghyunDev.service.SchemaService;
import com.abroad.baekjunghyunDev.service.video.VideoService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class FileUploadController {

    @Autowired
    private S3Service s3Service;
    @Autowired
    private SchemaService schemaService;
 
    @PostMapping("/v1/{site}/upload/file")
    public ResponseDto<String> uploadFile(@PathVariable String site, @RequestParam("file") MultipartFile file) throws IOException {
    	schemaService.changeSchema(site);
        String bucketName = "abroad-b";  // 업로드할 S3 버킷 이름
        String key = file.getOriginalFilename();  // 파일 이름
        
        // S3에 파일 업로드
        String videoUrl = s3Service.uploadFile(bucketName, key, file.getInputStream());
        
        return new ResponseDto<String>(HttpStatus.OK.value(), videoUrl);
    }
//    
//    @PostMapping("/v1/{site}/upload/content")
//    public ResponseDto<Video> uploadContent(@PathVariable String site, @RequestBody Video video, @AuthenticationPrincipal PrincipalDetail principal){
//		if(schemaService.changeSchemaPrincipal(site, principal.getUser()) != false) {
//			Video newVideo = videoService.S3비디오쓰기(video, principal.getUser());
//			return new ResponseDto<Video>(HttpStatus.OK.value(), newVideo);
//		}
//		else {
//			return new ResponseDto<Video>(HttpStatus.UNAUTHORIZED.value(), null);
//		}
//    }
//    
    
    @GetMapping("/v1/{site}/file/download/{fileName}")
    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) {
        try {
        	String bucketName = "abroad-b";  // 업로드할 S3 버킷 이름
            InputStream inputStream = s3Service.downloadFile(bucketName, fileName);

            // HTTP 응답 헤더 설정
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            // 파일을 응답 스트림으로 복사
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            } 

            outputStream.flush();
            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
}
