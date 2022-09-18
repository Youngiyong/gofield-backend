package com.gofield.infrastructure.external.api.kakao.test;

import com.gofield.infrastructure.external.api.kakao.dto.response.KaKaoProfileResponse;
import com.gofield.infrastructure.external.api.kakao.dto.response.KaKaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Component
public class WebClientKaKaoCaller implements KaKaoApiCaller {

    private final WebClient webClient;

    @Override
    public KaKaoProfileResponse getProfileInfo(String accessToken) {
        return webClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .headers(headers -> headers.setBearerAuth(accessToken))
            .retrieve()
            .bodyToMono(KaKaoProfileResponse.class)
            .block();
    }

    @Override
    public KaKaoTokenResponse getToken(String clientId, String redirectUrl, String code, String clientSecret) {
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

        bodyValues.add("client_id", clientId);
        bodyValues.add("client_secret", clientSecret);
        bodyValues.add("redirect_uri", redirectUrl);
        bodyValues.add("grant_type", "authorization_code");
        bodyValues.add("code", code);

        return webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(bodyValues))
                .retrieve()
                .bodyToMono(KaKaoTokenResponse.class)
                .block();

    }

}
