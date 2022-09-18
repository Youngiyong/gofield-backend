package com.gofield.infrastructure.s3.infra;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.infrastructure.s3.model.enums.FileContentType;
import com.gofield.infrastructure.s3.model.enums.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class S3FileStorageClient {

    @Value("${bucket.name}")
    private String BUCKET;
    private final AmazonS3Client amazonS3Client;

    public String uploadFile(MultipartFile file, FileType type) {
        FileContentType.IMAGE.validateAvailableContentType(file.getContentType());
        String bucketKey = type.createUniqueFileNameWithExtension(file.getOriginalFilename());
        ObjectMetadata metaData = createObjectMetadata(file);
        try {
            amazonS3Client.putObject(BUCKET, bucketKey, file.getInputStream(), metaData);
            amazonS3Client.setObjectAcl(BUCKET, bucketKey, CannedAccessControlList.PublicRead);
            return bucketKey;
        } catch (IOException e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.TOAST, "파일 첨부 에러");
        }
    }

    private ObjectMetadata createObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

}
