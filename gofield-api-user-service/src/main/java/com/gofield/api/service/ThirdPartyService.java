package com.gofield.api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.dto.SocialAuthentication;
import com.gofield.api.dto.enums.SlackChannelType;
import com.gofield.api.dto.req.UserRequest;
import com.gofield.api.dto.res.ItemOrderSheetListResponse;
import com.gofield.api.dto.res.ItemOrderSheetResponse;
import com.gofield.api.dto.res.OrderSheetContentResponse;
import com.gofield.api.util.ApiUtil;
import com.gofield.api.util.SlackUtil;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.common.utils.HttpUtils;
import com.gofield.common.utils.LocalDateTimeUtils;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.domain.cart.CartRepository;
import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.CodeRepository;
import com.gofield.domain.rds.domain.common.EEnvironmentFlag;
import com.gofield.domain.rds.domain.common.EGofieldService;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.order.*;
import com.gofield.domain.rds.domain.user.*;
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
import com.gofield.infrastructure.external.api.slack.SlackWebhookApiClient;
import com.gofield.infrastructure.external.api.toss.TossPaymentApiClient;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentApproveResponse;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentCancelResponse;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentResponse;
import com.gofield.infrastructure.external.api.tracker.TrackerApiClient;
import com.gofield.infrastructure.external.api.tracker.res.CarrierTrackResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    @Value("${secret.kakao.front_local_redirect_url}")
    private String AUTH_FRONT_KAKAO_LOCAL_REDIRECT_URL;

    @Value("${secret.kakao.front_service_redirect_url}")
    private String AUTH_FRONT_KAKAO_SERVICE_REDIRECT_URL;

    @Value("${secret.naver.front_local_redirect_url}")
    private String AUTH_FRONT_LOCAL_REDIRECT_URL;

    @Value("${secret.naver.front_service_redirect_url}")
    private String AUTH_FRONT_SERVICE_REDIRECT_URL;

    @Value("${secret.payment.success.front_service_redirect_url}")
    private String AUTH_FRONT_PAYMENT_SERVICE_SUCCESS_REDIRECT_URL;

    @Value("${secret.payment.success.front_local_redirect_url}")
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

    private final TrackerApiClient trackerApiClient;
    private final TossPaymentApiClient tossPaymentApiClient;
    private final KaKaoAuthApiClient kaKaoAuthApiClient;
    private final KaKaoProfileApiClient kaKaoProfileApiClient;
    private final NaverProfileApiClient naverProfileApiClient;
    private final SlackWebhookApiClient slackWebhookApiClient;
    private final StateLogRepository stateLogRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseFailRepository purchaseFailRepository;
    private final ItemStockRepository itemStockRepository;
    private final ItemRepository itemRepository;
    private final ItemBundleAggregationRepository itemBundleAggregationRepository;
    private final CartRepository cartRepository;
    private final OrderWaitRepository orderWaitRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final CodeRepository codeRepository;
    private final OrderRepository orderRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemOptionRepository orderItemOptionRepository;
    private final OrderShippingRepository orderShippingRepository;
    private final OrderShippingLogRepository orderShippingLogRepository;
    private final OrderShippingAddressRepository orderShippingAddressRepository;

    public CarrierTrackResponse getCarrierTrackInfo(String carrierId, String trackId){
        return trackerApiClient.getCarrierTrackInfo(carrierId, trackId);
    }

    public TossPaymentCancelResponse cancelPayment(String paymentKey, TossPaymentRequest.PaymentCancel request){
        return tossPaymentApiClient.cancelPayment(TOSS_PAYMENT_CLIENT_SECRET, paymentKey, request);
    }

    public String makeOrderItemNumber() {
        StringBuilder cancelNumber =  new StringBuilder(String.valueOf(Calendar.getInstance(Locale.KOREA).getTimeInMillis()));
        cancelNumber.setCharAt(0, '9');
        return cancelNumber.toString();
    }

    public void sendOrderSlackNotification(SlackChannelType channel, String username, String userTel, String orderNumber, String orderDate, String orderName, String comment, int totalAmount) {
        JSONObject request = SlackUtil.makeOrder(username, userTel, orderNumber, orderDate, orderName, totalAmount);
        slackWebhookApiClient.sendOrderNotification(request);
    }

    public void sendItemQnaNotification(String username, String itemNumber, String itemName, String thumbnail, String question, String createDate) {
        JSONObject request = SlackUtil.makeQna(username, itemNumber, itemName, thumbnail, question, createDate);
        slackWebhookApiClient.sendQnaNotification(request);
    }

    public void sendCancelSlackNotification(SlackChannelType channel, String username, String userTel, String orderNumber, String orderDate, String comment, String itemName, String optionName, String thumbnail, int totalAmount) {
        JSONObject request = SlackUtil.makeCancel(username, userTel, orderNumber, orderDate, comment, itemName, optionName, thumbnail, totalAmount);
        if(channel.equals(SlackChannelType.CANCEL)){
            slackWebhookApiClient.sendCancelNotification(request);
        }else if(channel.equals(SlackChannelType.RETURN)){
            slackWebhookApiClient.sendReturnNotification(request);
        } else if(channel.equals(SlackChannelType.CHANGE)){
            slackWebhookApiClient.sendChangeNotification(request);
        }
    }

    @Transactional(readOnly = true)
    public String callbackAuth(String code, String state){
        String redirectUrl = null;
        StateLog stateLog = stateLogRepository.findByState(state);
        if(stateLog==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, String.format("<%s> ???????????? ?????? state?????????.", state));
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
        String cardNumber = null;
        String cardType = null;
        EPaymentType paymentType = null;
        int installmentPlanMonth = 0;

        if(response.getEasyPay()!=null){
            paymentCompany = response.getEasyPay().getProvider();
            paymentType = EPaymentType.EASYPAY;
        } else {
            Code code = codeRepository.findByCode(response.getCard().getIssuerCode());
            paymentCompany = code!=null ? code.getName() : "??????";
            paymentType = EPaymentType.CARD;
            cardNumber = response.getCard().getNumber();
            cardType = response.getCard().getCardType();
            installmentPlanMonth = response.getCard().getInstallmentPlanMonths();
        }

        /*
        ToDo: ???????????? totalDiscount ??????
         */
        Purchase purchase = Purchase.newInstance(orderId, paymentKey, amount, ApiUtil.toJsonStr(response));
        purchaseRepository.save(purchase);
        OrderSheetContentResponse orderSheetContent =  ApiUtil.strToObject(orderSheet.getSheet(), new TypeReference<OrderSheetContentResponse>(){});
        ItemOrderSheetListResponse orderSheetList = orderSheetContent.getOrderSheetList();
        UserRequest.ShippingAddress shippingAddress = ApiUtil.strToObject(orderWait.getShippingAddress(), new TypeReference<UserRequest.ShippingAddress>() {
        });
        OrderShippingAddress orderShippingAddress = OrderShippingAddress.newInstance(orderId, shippingAddress.getName(), shippingAddress.getTel(), shippingAddress.getZipCode(), shippingAddress.getAddress(), shippingAddress.getAddressExtra(), shippingAddress.getShippingComment());
        orderShippingAddressRepository.save(orderShippingAddress);

        Order order = Order.newInstance(orderShippingAddress, orderWait.getUserId(), orderId, paymentKey,  orderSheetList.getTotalPrice(), orderSheetList.getTotalPrice()+orderSheetList.getTotalDelivery(), orderSheetList.getTotalDelivery(),0,  paymentCompany, paymentType.name(), cardNumber, cardType, installmentPlanMonth);
        orderRepository.save(order);

        for(ItemOrderSheetResponse result: orderSheetList.getOrderSheetList()){
            OrderShipping orderShipping = OrderShipping.newInstance(result.getSellerId(), order, orderId, RandomUtils.makeRandomCode(32), shippingAddress.getShippingComment(), result.getChargeType(),  result.getCharge(), result.getDeliveryPrice(), result.getCondition(), result.getFeeJeju(), result.getFeeJejuBesides());
            orderShippingRepository.save(orderShipping);
            OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), orderWait.getUserId(), EGofieldService.GOFIELD_API,  EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK);
            orderShippingLogRepository.save(orderShippingLog);
            ItemStock itemStock = itemStockRepository.findByItemNumber(result.getItemNumber());
            itemStock.updateOrderApprove(result.getQty());
            if(itemStock.getStatus().equals(EItemStatusFlag.SOLD_OUT)){
                ItemBundleAggregation itemBundleAggregation = itemBundleAggregationRepository.findByBundleId(itemStock.getItem().getBundle().getId());
                itemBundleAggregation.updateItemMinusOne();
            }
            OrderItemOption orderItemOption = null;
            if(result.getIsOption()){
                ItemOption itemOption = itemOptionRepository.findByOptionId(result.getOptionId());
                orderItemOption = OrderItemOption.newInstance(itemOption.getId(), result.getOptionType(), ApiUtil.toJsonStr(result.getOptionName()), result.getQty(), result.getPrice());
                orderItemOptionRepository.save(orderItemOption);
            }
            OrderItem orderItem = OrderItem.newInstance(order, result.getSellerId(), itemStock.getItem(), orderItemOption, orderShipping, orderId, makeOrderItemNumber(), result.getItemNumber(), result.getName(),  result.getQty(), result.getPrice());
            orderItemRepository.save(orderItem);
            if(result.getBundleId()!=null){
                //?????? ?????? ????????????
                ItemBundleAggregation itemBundleAggregation = itemBundleAggregationRepository.findByBundleId(result.getBundleId());

                List<Item> itemList = itemRepository.findAllItemByBundleIdAndStatusSalesOrderByPrice(result.getBundleId());
                List<Item> usedItemList = itemList.stream().filter(k -> k.getClassification().equals(EItemClassificationFlag.USED)).collect(Collectors.toList());
                List<Item> newItemList = itemList.stream().filter(k -> k.getClassification().equals(EItemClassificationFlag.NEW)).collect(Collectors.toList());

                int itemCount = 0;
                int newLowestPrice = 0;
                int usedLowestPrice = 0;
                int lowestPrice = 0;
                int highestPrice = 0;

                if(itemList.isEmpty()){
                    itemBundleAggregation.update(0, 0, 0, 0, 0);
                } else {
                    itemCount = itemList.size();
                    lowestPrice = itemList.get(0).getPrice();
                    highestPrice = itemList.get(itemList.size()-1).getPrice();

                    if(usedItemList.isEmpty()){
                        usedLowestPrice = 0;
                    } else {
                        usedLowestPrice = usedItemList.get(0).getPrice();
                    }
                    if(newItemList.isEmpty()){
                        newLowestPrice = 0;
                    } else {
                        newLowestPrice = newItemList.get(0).getPrice();
                    }
                    itemBundleAggregation.update(itemCount, newLowestPrice, usedLowestPrice, lowestPrice, highestPrice);
                }
            }
        }
        List<Long> cartIdList = orderSheetContent.getCartIdList();
        if(cartIdList!=null && !cartIdList.isEmpty()){
            cartRepository.deleteByCartIdList(cartIdList);
        }
        orderWaitRepository.delete(orderWait);
        sendOrderSlackNotification(SlackChannelType.ORDER, shippingAddress.getName(), shippingAddress.getTel(), order.getOrderNumber(), LocalDateTimeUtils.LocalDateTimeToString(order.getCreateDate()), response.getOrderName(), null, order.getTotalAmount());

        if(orderWait.getEnvironment().equals(EEnvironmentFlag.LOCAL)){
            return AUTH_FRONT_PAYMENT_LOCAL_SUCCESS_REDIRECT_URL + orderId;
        } else {
            return AUTH_FRONT_PAYMENT_SERVICE_SUCCESS_REDIRECT_URL + orderId;
        }
    }

    @Transactional
    public String callbackFailPayment(String orderId, String code, String message){
        OrderWait orderWait = orderWaitRepository.findByOid(orderId);
        PurchaseFail purchaseFail = PurchaseFail.newInstance(orderId, code, message);
        purchaseFailRepository.save(purchaseFail);
        if(orderWait.getEnvironment().equals(EEnvironmentFlag.LOCAL)){
            return AUTH_FRONT_PAYMENT_LOCAL_FAIL_REDIRECT_URL + orderId + "?" + message;
        } else {
            return AUTH_FRONT_PAYMENT_SERVICE_FAIL_REDIRECT_URL + orderId + "?" + message;
        }
    }


    public SocialAuthentication getSocialAuthentication(String code, ESocialFlag social, EEnvironmentFlag environment){
        if(social.equals(ESocialFlag.KAKAO)){
            String redirectUrl = AUTH_FRONT_KAKAO_SERVICE_REDIRECT_URL;
            if(environment.equals(EEnvironmentFlag.LOCAL)){
                redirectUrl = AUTH_FRONT_KAKAO_LOCAL_REDIRECT_URL;
            }
            KaKaoTokenRequest request = KaKaoTokenRequest.of(KAKAO_CLIENT_ID, redirectUrl, code, KAKAO_CLIENT_SECRET);
            KaKaoTokenResponse tokenResponse = kaKaoAuthApiClient.getToken(request);
            KaKaoProfileResponse profileResponse = kaKaoProfileApiClient.getProfileInfo(HttpUtils.withBearerToken(tokenResponse.getAccess_token()));
            return SocialAuthentication.of(profileResponse.getId(), profileResponse.getNickName(), profileResponse.getEmail());
        } else if(social.equals(ESocialFlag.NAVER)) {
            NaverProfileResponse profileResponse = naverProfileApiClient.getProfileInfo(HttpUtils.withBearerToken(code));
            return SocialAuthentication.of(profileResponse.getResponse().getId(), profileResponse.getResponse().getName()==null ? profileResponse.getResponse().getNickname() : profileResponse.getResponse().getName(), profileResponse.getResponse().getEmail());
        } else {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "???????????? ?????? ?????? ??????????????????.");
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
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "???????????? ?????? ?????? ??????????????????.");
        }
    }


}
