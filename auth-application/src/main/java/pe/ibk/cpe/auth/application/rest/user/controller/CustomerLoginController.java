package pe.ibk.cpe.auth.application.rest.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerResponse;
import pe.ibk.cpe.auth.domain.service.user.port.inbound.LoginCustomerUseCase;

@Slf4j
@RestController
@RequestMapping("/api/auth/customer")
@AllArgsConstructor
public class CustomerLoginController {
    private final LoginCustomerUseCase loginCustomerUseCase;

    @PostMapping("/v1.0/login")
    public ResponseEntity<LoginCustomerResponse> login(@RequestBody LoginCustomerRequest loginCustomerRequest) {
        log.info("Login request : {}",loginCustomerRequest);
        LoginCustomerResponse loginCustomerResponse = loginCustomerUseCase.login(loginCustomerRequest);

        return ResponseEntity.ok(loginCustomerResponse);
    }
}
