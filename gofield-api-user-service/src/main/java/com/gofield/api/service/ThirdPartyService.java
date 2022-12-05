package com.gofield.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.api.dto.SocialAuthentication;
import com.gofield.api.dto.req.OrderRequest;
import com.gofield.api.dto.req.UserRequest;
import com.gofield.api.dto.res.ItemOrderSheetListResponse;
import com.gofield.api.dto.res.ItemOrderSheetResponse;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.HttpUtils;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.CodeRepository;
import com.gofield.domain.rds.domain.common.EEnvironmentFlag;
import com.gofield.domain.rds.domain.common.EGofieldService;
import com.gofield.domain.rds.domain.item.Item;
import com.gofield.domain.rds.domain.item.ItemOption;
import com.gofield.domain.rds.domain.item.ItemOptionRepository;
import com.gofield.domain.rds.domain.item.ItemRepository;
import com.gofield.domain.rds.domain.order.*;
import com.gofield.domain.rds.domain.user.ESocialFlag;
import com.gofield.domain.rds.domain.user.StateLog;
import com.gofield.domain.rds.domain.user.StateLogRepository;
import com.gofield.infrastructure.external.api.kakao.KaKaoAuthApiClient;
import com.gofield.infrastructure.external.api.kakao.KaKaoProfileApiClient;
import com.gofield.infrastructure.external.api.kakao.dto.req.KaKaoTokenRequest;
import com.gofield.infrastructure.external.api.kakao.dto.res.KaKaoProfileResponse;
import com.gofield.infrastructure.external.api.kakao.dto.res.KaKaoTokenResponse;
import com.gofield.infrastructure.external.api.naver.NaverAuthApiClient;
import com.gofield.infrastructure.external.api.naver.NaverProfileApiClient;
import com.gofield.infrastructure.external.api.naver.dto.req.NaverTokenRequest;
import com.gofield.infrastructure.external.api.naver.dto.res.NaverProfileResponse;
import com.gofield.infrastructure.external.api.naver.dto.res.NaverTokenResponse;
import com.gofield.infrastructure.external.api.toss.TossPaymentApiClient;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentApproveResponse;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThirdPartyService {

    @Value("${secret.kakao.client_id}")
    private String KAKAO_CLIENT_ID;

    @Value("${secret.kakao.client_secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${secret.kakao.auth_url}")
    private String KAKAO_AUTH_URL;

    @Value("${secret.naver.client_id}")
    private String NAVER_CLIENT_ID;

    @Value("${secret.naver.client_secret}")
    private String NAVER_CLIENT_SECRET;

    @Value("${secret.naver.auth_url}")
    private String NAVER_AUTH_URL;

    @Value("${secret.auth.callback_url}")
    private String AUTH_CALLBACK_URL;

    @Value("${secret.auth.front_local_redirect_url}")
    private String AUTH_FRONT_LOCAL_REDIRECT_URL;

    @Value("${secret.auth.front_service_redirect_url}")
    private String AUTH_FRONT_SERVICE_REDIRECT_URL;

    @Value("${secret.payment.success.front_service_redirect_url}")
    private String AUTH_FRONT_PAYMENT_SERVICE_SUCCESS_REDIRECT_URL;

    @Value("${secret.payment.success.front_service_redirect_url}")
    private String AUTH_FRONT_PAYMENT_LOCAL_SUCCESS_REDIRECT_URL;

    @Value("${secret.payment.fail.front_service_redirect_url}")
    private String AUTH_FRONT_PAYMENT_SERVICE_FAIL_REDIRECT_URL;

    @Value("${secret.payment.fail.front_local_redirect_url}")
    private String AUTH_FRONT_PAYMENT_LOCAL_FAIL_REDIRECT_URL;

    @Value("${secret.toss.success-url}")
    private String PAYMENT_CALLBACK_SUCCESS_URL;

    @Value("${secret.toss.fail-url}")
    private String PAYMENT_CALLBACK_FAIL_URL;

    @Value("${secret.toss.secret-key}")
    private String TOSS_PAYMENT_CLIENT_SECRET;

    private final OrderWaitRepository orderWaitRepository;
    private final TossPaymentApiClient tossPaymentApiClient;
    private final KaKaoAuthApiClient kaKaoAuthApiClient;
    private final KaKaoProfileApiClient kaKaoProfileApiClient;
    private final NaverAuthApiClient naverAuthApiClient;
    private final NaverProfileApiClient naverProfileApiClient;
    private final StateLogRepository stateLogRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseFailRepository purchaseFailRepository;

    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final CodeRepository codeRepository;
    private final OrderRepository orderRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemOptionRepository orderItemOptionRepository;
    private final OrderShippingRepository orderShippingRepository;
    private final OrderShippingLogRepository orderShippingLogRepository;
    private final OrderShippingAddressRepository orderShippingAddressRepository;

    @Transactional(readOnly = true)
    public String callbackAuth(String code, String state){
        String redirectUrl = null;
        StateLog stateLog = stateLogRepository.findByState(state);
        if(stateLog==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, String.format("<%s> 존재하지 않는 state입니다.", state));
        }
        if(stateLog.getEnvironment().equals(EEnvironmentFlag.LOCAL)){
            redirectUrl = AUTH_FRONT_LOCAL_REDIRECT_URL + "?state=" + state + "&code=" + code + "&social=" + stateLog.getSocial().getKey();
        } else {
            redirectUrl = AUTH_FRONT_SERVICE_REDIRECT_URL + "?state=" + state + "&code=" + code + "&social=" + stateLog.getSocial().getKey();
        }
        return redirectUrl;
    }

    public TossPaymentResponse getPaymentReadyInfo(TossPaymentRequest.Payment request){
        return tossPaymentApiClient.createPayment(TOSS_PAYMENT_CLIENT_SECRET, request);
    }

    public String getTossPaymentSuccessUrl(){
        return PAYMENT_CALLBACK_SUCCESS_URL;
    }

    public String getTossPaymentFailUrl(){
        return PAYMENT_CALLBACK_FAIL_URL;
    }

    @Transactional
    public String redirect(ESocialFlag social, EEnvironmentFlag environment){
        String state = RandomUtils.makeRandomCode(32);
        StateLog stateLog = StateLog.newInstance(state, social, environment);
        stateLogRepository.save(stateLog);
        return makeUrl(state, social);
    }

    @Transactional
    public String callbackSuccessPayment(String orderId, String paymentKey, int amount){
        OrderWait orderWait = orderWaitRepository.findByOid(orderId);
        OrderSheet orderSheet = orderSheetRepository.findByUserIdAndUuid(orderWait.getUserId(), orderWait.getUuid());
        TossPaymentApproveResponse response = tossPaymentApiClient.approvePayment(TOSS_PAYMENT_CLIENT_SECRET, TossPaymentRequest.PaymentApprove.of(amount, orderId, paymentKey));
        String paymentCompany = null;
        if (orderWait.getPaymentType().equals(EPaymentType.EASYPAY)) {
            paymentCompany = response.getEasyPay().getProvider();
        } else if (orderWait.getPaymentType().equals(EPaymentType.CARD)){
            Code code = codeRepository.findByCode(response.getCard().getIssuerCode());
            paymentCompany = code!=null ? code.getName() : "..";
        }

        /*
        ToDo: 할인금액 totalDiscount 처리
         */
        try {
            Purchase purchase = Purchase.newInstance(orderId, paymentKey, amount, new ObjectMapper().writeValueAsString(response));
            purchaseRepository.save(purchase);
            ItemOrderSheetListResponse orderSheetList =  new ObjectMapper().readValue(orderSheet.getSheet(), new TypeReference<ItemOrderSheetListResponse>(){});
            UserRequest.ShippingAddress shippingAddress = new ObjectMapper().readValue(orderWait.getShippingAddress(), new TypeReference<UserRequest.ShippingAddress>(){});
            OrderShippingAddress orderShippingAddress = OrderShippingAddress.newInstance(orderId, shippingAddress.getName(), shippingAddress.getTel(), shippingAddress.getZipCode(), shippingAddress.getAddress(), shippingAddress.getAddressExtra(), shippingAddress.getShippingComment());
            orderShippingAddressRepository.save(orderShippingAddress);
            Order order = Order.newInstance(orderShippingAddress, orderWait.getUserId(), orderId, paymentKey, orderSheetList.getTotalDelivery(), amount, 0,  paymentCompany);
            orderRepository.save(order);
            for(ItemOrderSheetResponse result: orderSheetList.getOrderSheetList()){
                OrderShipping orderShipping = OrderShipping.newInstance(result.getSellerId(), order, orderId, RandomUtils.makeRandomCode(32), shippingAddress.getShippingComment(), result.getChargeType(),  result.getCharge(), result.getDeliveryPrice(), result.getCondition(), result.getFeeJeju(), result.getFeeJejuBesides());
                orderShippingRepository.save(orderShipping);
                OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), orderWait.getUserId(), EGofieldService.GOFIELD_API,  EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK);
                orderShippingLogRepository.save(orderShippingLog);
                Item item = itemRepository.findByItemId(result.getId());
                OrderItem orderItem = OrderItem.newInstance(order.getId(), result.getSellerId(), item, orderShipping, orderId, result.getItemNumber(), result.getName(),  result.getQty(), result.getPrice());
                orderItemRepository.save(orderItem);
                if(result.getIsOption()){
                    ItemOption itemOption = itemOptionRepository.findByOptionId(result.getOptionId());
                    OrderItemOption orderItemOption = OrderItemOption.newInstance(orderItem.getId(), itemOption, result.getOptionType(), new ObjectMapper().writeValueAsString(result.getOptionName()), result.getQty(), result.getPrice());
                    orderItemOptionRepository.save(orderItemOption);
                }
            }
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }





        if(orderWait.getEnvironment().equals(EEnvironmentFlag.LOCAL)){
            return AUTH_FRONT_PAYMENT_LOCAL_SUCCESS_REDIRECT_URL + orderId;
        } else {
            return AUTH_FRONT_PAYMENT_SERVICE_SUCCESS_REDIRECT_URL + orderId;
        }
    }

    @Transactional
    public String callbackFailPayment(String orderId, String code, String message){
        OrderWait orderWait = orderWaitRepository.findByOid(orderId);
        PurchaseFail purchase = PurchaseFail.newInstance(orderId, code, message);
        purchaseFailRepository.save(purchase);
        if(orderWait.getEnvironment().equals(EEnvironmentFlag.LOCAL)){
            return AUTH_FRONT_PAYMENT_LOCAL_FAIL_REDIRECT_URL + orderId + "?" + message;
        } else {
            return AUTH_FRONT_PAYMENT_SERVICE_FAIL_REDIRECT_URL + orderId + "?" + message;
        }
    }

    public SocialAuthentication getSocialAuthentication(String state, String code, ESocialFlag social){
        if(social.equals(ESocialFlag.KAKAO)){
            KaKaoTokenRequest request = KaKaoTokenRequest.of(KAKAO_CLIENT_ID, AUTH_CALLBACK_URL, code, KAKAO_CLIENT_SECRET);
            KaKaoTokenResponse tokenResponse = kaKaoAuthApiClient.getToken(request);
            KaKaoProfileResponse profileResponse = kaKaoProfileApiClient.getProfileInfo(HttpUtils.withBearerToken(tokenResponse.getAccess_token()));
            return SocialAuthentication.of(profileResponse.getId(), profileResponse.getNickName());
        } else if(social.equals(ESocialFlag.NAVER)) {
            NaverTokenRequest request = NaverTokenRequest.of(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, code, state);
            NaverTokenResponse tokenResponse = naverAuthApiClient.getToken(request);
            NaverProfileResponse profileResponse = naverProfileApiClient.getProfileInfo(HttpUtils.withBearerToken(tokenResponse.getAccess_token()));
            return SocialAuthentication.of(profileResponse.getResponse().getId(), null);
        } else {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "지원하지 않는 소셜 로그인입니다.");
        }
    }

    private String makeUrl(String state, ESocialFlag social){
        switch (social){
            case KAKAO:
                return KAKAO_AUTH_URL + "?response_type=code&client_id=" +
                        KAKAO_CLIENT_ID + "&redirect_uri=" +
                        AUTH_CALLBACK_URL + "&state=" + state;
            case NAVER:
                return NAVER_AUTH_URL + "?response_type=code&client_id=" +
                        NAVER_CLIENT_ID + "&redirect_uri=" +
                        AUTH_CALLBACK_URL + "&state=" + state;
            default:
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "지원하지 않는 소셜 로그인입니다.");
        }
    }


}
