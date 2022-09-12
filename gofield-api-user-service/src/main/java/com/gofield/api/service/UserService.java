package com.gofield.api.service;

import com.gofield.api.config.resolver.UserUuidResolver;
import com.gofield.common.utils.EncryptUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${gofield.token_key}")
    private String tokenDecryptKey;

    public String getUserDecryptUuid(){
        return EncryptUtils.aes256Decrypt(tokenDecryptKey, UserUuidResolver.getCurrentUserUuid());
    }

}
