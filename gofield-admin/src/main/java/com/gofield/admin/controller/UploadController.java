package com.gofield.admin.controller;


import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.common.utils.CommonUtils;
import com.gofield.infrastructure.s3.infra.S3FileStorageClient;
import com.gofield.infrastructure.s3.model.enums.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@RestController
@RequiredArgsConstructor
public class UploadController {

    private final S3FileStorageClient s3FileStorageClient;

    @PostMapping("/upload/image")
    public String uploadImage(@RequestPart MultipartFile upload, HttpServletResponse response){

        PrintWriter printWriter = null;

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try {
            printWriter = response.getWriter();
            // 서버로 파일 전송 후 이미지 정보 확인을 위해 filename, uploaded, fileUrl 정보를 response 해주어야 함
            printWriter.println("{\"filename\" : \"" + upload.getName() + "\", \"uploaded\" : 1, \"url\":\"" + CommonUtils.makeCloudFrontUrl(s3FileStorageClient.uploadFile(upload, FileType.ITEM_CONTENT_IMAGE)) + "\"}");
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.TOAST, "파일 업로드 오류");
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
        return null;
    }

}
