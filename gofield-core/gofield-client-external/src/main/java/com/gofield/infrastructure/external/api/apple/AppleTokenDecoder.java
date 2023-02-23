package com.gofield.infrastructure.external.api.apple;

public interface AppleTokenDecoder {

    String getSocialIdFromAppleIdToken(String idToken);

}
