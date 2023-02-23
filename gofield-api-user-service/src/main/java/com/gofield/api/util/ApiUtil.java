package com.gofield.api.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.service.auth.dto.response.ClientResponse;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.common.utils.ObjectMapperUtils;

import java.util.Base64;

public class ApiUtil {

    public static ClientResponse base64EncodingStrToDecodeClientDetail(String encodingStr){

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodingStr);
            String decodedString = new String(decodedBytes);

            return ObjectMapperUtils.strToObject(decodedString, new TypeReference<ClientResponse>(){});
        } catch (Exception e){
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, "base 64 decoding error");
        }


    }
}
