package com.gofield.external.api.apple;

import com.gofield.external.api.apple.dto.response.IdTokenPayload;

public interface AppleTokenDecoder {

    IdTokenPayload getUserInfoFromToken(String idToken);

}
