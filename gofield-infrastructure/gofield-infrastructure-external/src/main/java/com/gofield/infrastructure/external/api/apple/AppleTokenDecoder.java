package com.gofield.infrastructure.external.api.apple;

import com.gofield.infrastructure.external.api.apple.dto.response.IdTokenPayload;

public interface AppleTokenDecoder {

    IdTokenPayload getUserInfoFromToken(String idToken);

}
