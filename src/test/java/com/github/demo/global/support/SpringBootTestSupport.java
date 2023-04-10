package com.github.demo.global.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("local") // 프로파일
//@Transactional // 롤백 시키고 싶으면 활성화
public abstract class SpringBootTestSupport {

    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    @Autowired
    protected JPAQueryFactory jpaQueryFactory;
    protected EntityManager entityManager;

    protected EntityTransaction transaction;

    @PostConstruct
    public void init() {
        this.entityManager = entityManagerFactory.createEntityManager();
        this.transaction = entityManager.getTransaction();
    }

    protected <T> T save(T entity) {
        transaction.begin();
        try {
            entityManager.persist(entity);
            entityManager.flush();// transaction commit시 자동으로 flush 발생시키나 명시적으로 선언
            transaction.commit();
            entityManager.clear();

        } catch (Exception e) {
            transaction.rollback();
        }

        return entity;
    }
}
