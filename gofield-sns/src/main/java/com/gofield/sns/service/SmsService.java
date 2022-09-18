package com.gofield.sns.service;

import com.gofield.common.exception.InternalServerException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.infrastructure.external.api.naver.NaverSnsApiClient;
import com.gofield.infrastructure.external.api.naver.dto.request.NaverSmsRequest;
import com.gofield.infrastructure.external.api.naver.dto.response.NaverSmsResponse;
import com.gofield.sns.model.request.SmsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {
    @Value("${naver.cloud.client_id}")
    private String NAVER_CLIENT_ID;
    @Value("${naver.cloud.secret}")
    private String NAVER_CLIENT_SECRET;
    @Value("${naver.cloud.sns.from}")
    private String NAVER_SNS_FROM;
    @Value("${naver.cloud.sns.url}")
    private String NAVER_SNS_URL;
    private final NaverSnsApiClient naverSnsApiClient;

    public void sendSms(SmsRequest.SmsCustom request) {
        List<NaverSmsRequest.SmsMessage> list = new ArrayList<>();
        list.add(new NaverSmsRequest.SmsMessage(request.getTel()));
        NaverSmsRequest.SmsBody body = NaverSmsRequest.SmsBody.builder()
                .type("LMS")
                .from(NAVER_SNS_FROM)
                .subject(request.getSubject())
                .content(request.getContent())
                .messages(list)
                .build();

        String timeStamp = String.valueOf(System.currentTimeMillis());
        String signature = makeSignature(timeStamp);
        naverSnsApiClient.sendSms(timeStamp, NAVER_CLIENT_ID, signature, body);
    }

    private String makeSignature(String timeStamp) {

        String space = " ";
        String newLine = "\n";
        String method = "POST";

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(NAVER_SNS_URL)
                .append(newLine)
                .append(timeStamp)
                .append(newLine)
                .append(NAVER_CLIENT_ID)
                .toString();

        String encode = "";
        try {
            SecretKeySpec signingKey = new SecretKeySpec(NAVER_CLIENT_SECRET.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            encode = Base64.encodeBase64String(rawHmac);
        } catch (Exception e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.RETRY, "try again");
        }

        return encode;
    }

}
