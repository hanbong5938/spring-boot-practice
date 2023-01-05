package com.github.demo.domain.user.common.api;

import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.factory.SignInFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class SignInApi {

    private final SignInFactory signInFactory;

    @PostMapping("/sign-in")
    public ResponseEntity<AccountDto.TokenResponse> signIn(@RequestBody AccountDto.SignInRequest request) {
        return ResponseEntity.ok(this.signInFactory.getService(request.accountType()).signIn(request));
    }
}
