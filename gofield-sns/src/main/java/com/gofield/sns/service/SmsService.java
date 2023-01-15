package com.gofield.sns.service;

import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.infrastructure.external.api.naver.NaverSnsApiClient;
import com.gofield.infrastructure.external.api.naver.dto.req.NaverSmsRequest;
import com.gofield.sns.dto.req.SmsRequest;
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
    @Value("${secret.naver.cloud.client_id}")
    private String NAVER_CLIENT_ID;
    @Value("${secret.naver.cloud.secret}")
    private String NAVER_CLIENT_SECRET;
    @Value("${secret.naver.sns.from}")
    private String NAVER_SNS_FROM;
    @Value("${secret.naver.sns.url}")
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

        System.out.println("timestamp: " + timeStamp + "\n" +
                            "clientId: " +NAVER_CLIENT_ID + "\n" +
                            "signature: " + signature + "\n"+
                            "body: " + body.getFrom() + "\n"+
                            "content: " + body.getContent() + "\n"+
                            "massage: " + body.getMessages().get(0).toString());

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

        log.info("message:"+message+"\n");
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
