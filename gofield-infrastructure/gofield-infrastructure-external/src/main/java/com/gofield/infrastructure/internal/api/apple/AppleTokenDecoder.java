package com.gofield.infrastructure.internal.api.apple;

public interface AppleTokenDecoder {

    String getSocialIdFromAppleIdToken(String idToken);

}
