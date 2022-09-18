package com.gofield.sns.handler;

import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CertTokenHandler {
    private static String GOFIELD_SNS_CERT_TOKEN;

    @Value("${sns.auth}")
    public void setGofieldSnsCertToken(String certToken) {
        this.GOFIELD_SNS_CERT_TOKEN = certToken;
    }

    public static void validGofieldSnsCertToken(String cheeseAuth) {
        if (!cheeseAuth.equals(GOFIELD_SNS_CERT_TOKEN)) {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "invalid sns cert token");
        }
    }
}
