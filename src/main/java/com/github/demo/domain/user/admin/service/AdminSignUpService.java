package com.github.demo.domain.user.admin.service;

import com.github.demo.domain.user.admin.domain.Admin;
import com.github.demo.domain.user.admin.repository.AdminRepository;
import com.github.demo.domain.user.common.dto.AccountDto;
import com.github.demo.domain.user.common.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSignUpService implements SignUpService {

    private final AdminRepository adminRepository;


    @Override
    public void signUp(AccountDto.SignUpRequest request) {
        this.adminRepository.save((Admin) request.toAccount());
    }
}
