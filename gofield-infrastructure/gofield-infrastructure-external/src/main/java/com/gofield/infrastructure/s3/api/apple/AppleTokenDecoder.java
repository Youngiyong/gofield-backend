package com.gofield.infrastructure.s3.api.apple;

public interface AppleTokenDecoder {

    String getSocialIdFromAppleIdToken(String idToken);

}
