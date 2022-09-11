package com.gofield.external.api.kakao;

import com.gofield.common.exception.model.BadGatewayException;
import com.gofield.common.exception.model.InvalidException;
import com.gofield.external.api.kakao.dto.response.KaKaoProfileResponse;
import com.gofield.external.api.kakao.dto.response.KaKaoTokenResponse;
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
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new InvalidException(String.format("잘못된 액세스 토큰 (%s) 입니다", accessToken))))
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new BadGatewayException("카카오 로그인 연동 중 에러가 발생하였습니다")))
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
