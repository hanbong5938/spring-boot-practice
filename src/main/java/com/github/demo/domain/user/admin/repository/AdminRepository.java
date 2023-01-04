package com.github.demo.domain.user.admin.repository;

import com.github.demo.domain.user.admin.domain.Admin;
import com.github.demo.global.support.CustomQuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository extends CustomQuerydslRepositorySupport {
    public AdminRepository() {
        super(Admin.class);
    }
}
