package com.github.demo.domain.user.common.api;

import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.factory.SignUpFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SignUpApi {

    private final SignUpFactory signUpFactory;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody AccountDto.SignUpRequest request) {
        this.signUpFactory.getService(request.accountType()).signUp(request);
    }
}
