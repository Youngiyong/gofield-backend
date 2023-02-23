package com.gofield.api.controller.thirdparty;

import com.gofield.api.service.thirdparty.ThirdPartyService;
import com.gofield.common.model.dto.res.ApiResponse;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/third")
public class ThirdPartyController {

    private final ThirdPartyService thirdPartyService;

//    @ApiOperation(value = "소셜 로그인 페이지 이동")
//    @GetMapping("/v1/ready")
//    public void thirdPartyReady(@RequestParam ESocialFlag social,
//                                @RequestParam EEnvironmentFlag environment,
//                                HttpServletResponse response) throws IOException {
//        response.sendRedirect(thirdPartyService.redirect(social, environment));
//    }
//
//    @ApiOperation(value = "테스트 - 카카오 인증 콜백")
//    @GetMapping("/v1/callback")
//    public void callbackLoginAuto(@RequestParam String code,
//                                  @RequestParam String state,
//                                  HttpServletResponse response) throws IOException {
//         response.sendRedirect(thirdPartyService.callbackAuth(code, state));
//    }

    @ApiOperation(value = "결제 - 성공 콜백")
    @GetMapping("/v1/payment/callback/success")
    public void callbackSuccessPayment(@RequestParam String orderId,
                                       @RequestParam String paymentKey,
                                       @RequestParam int amount,
                                       HttpServletResponse response) throws IOException {
        response.sendRedirect(thirdPartyService.callbackSuccessPayment(orderId, paymentKey, amount));
    }
    @ApiOperation(value = "결제 - 실패 콜백")
    @GetMapping("/v1/payment/callback/fail")
    public void callbackFailPayment(@RequestParam(required = false) String orderId,
                                    @RequestParam(required = false) String code,
                                    @RequestParam(required = false) String message,
                                    HttpServletResponse response) throws IOException {
        response.sendRedirect(thirdPartyService.callbackFailPayment(orderId, code, message));
    }

    @ApiOperation(value = "결제 - 가상계좌 성공 콜백")
    @PostMapping("/v1/payment/virtual/callback/success")
    public ApiResponse callbackVirtualAccountPaymentSuccess(@RequestBody TossPaymentRequest.PaymentVirtualCallback request){
        return ApiResponse.success(thirdPartyService.callbackVirtualAccount(request));
    }

//    @ApiOperation(value = "결제 - 가상계좌 성공 콜백")
//    @GetMapping("/v1/payment/virtual/callback/success")
//    public ApiResponse callbackVirtualAccountPaymentSuccess(@RequestParam String orderId,
//                                                            @RequestParam String transactionKey,
//                                                            @RequestParam String status,
//                                                            @RequestParam String secret,
//                                                            @RequestParam String createdAt){
//        return ApiResponse.success(thirdPartyService.callbackVirtualAccount(orderId, transactionKey, status, secret, createdAt));
//    }

}
