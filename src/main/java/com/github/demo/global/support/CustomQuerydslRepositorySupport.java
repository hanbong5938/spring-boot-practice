package com.github.demo.global.support;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("unchecked")
@Repository
public abstract class CustomQuerydslRepositorySupport {

    private final Class domainClass;
    private EntityManager entityManager;
    private JpaEntityInformation entityInformation;
    private Querydsl querydsl;
    private JPAQueryFactory queryFactory;

    public CustomQuerydslRepositorySupport(Class<?> domainClass) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        this.domainClass = domainClass;
    }

    @PostConstruct
    public void validate() {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        Assert.notNull(querydsl, "Querydsl must not be null!");
        Assert.notNull(queryFactory, "QueryFactory must not be null!");
    }

    protected JPAQueryFactory getQueryFactory() {
        return queryFactory;
    }

    protected Querydsl getQuerydsl() {
        return querydsl;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        this.entityInformation =
                JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
        SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
        EntityPath path = resolver.createPath(entityInformation.getJavaType());
        this.entityManager = entityManager;
        this.querydsl = new Querydsl(entityManager, new
                PathBuilder<>(path.getType(), path.getMetadata()));
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    protected JpaEntityInformation getEntityInformation() {
        return entityInformation;
    }

    protected <T> JPAQuery<T> select(Expression<T> expr) {
        return this.getQueryFactory().select(expr);
    }

    protected JPAQuery<Integer> selectOne() {
        return select(Expressions.ONE);
    }

    protected <T> JPAQuery<T> selectFrom(final EntityPath<T> from) {
        return this.getQueryFactory().selectFrom(from);
    }

    protected <T> Page<T> applyPagination(final Pageable pageable,
                                          final Function<JPAQueryFactory, JPAQuery> contentQuery) {
        final JPAQuery jpaQuery = contentQuery.apply(getQueryFactory());
        List<T> content = getQuerydsl().applyPagination(pageable,
                jpaQuery).fetch();
        return PageableExecutionUtils.getPage(content, pageable,
                () -> contentQuery.apply(getQueryFactory()).fetch().size());
    }

    protected <T> Page<T> applyPagination(final Pageable pageable,
                                          final Function<JPAQueryFactory, JPAQuery> contentQuery, final Function<JPAQueryFactory, JPAQuery> countQuery) {
        JPAQuery jpaContentQuery = contentQuery.apply(getQueryFactory());
        List<T> content = getQuerydsl().applyPagination(pageable,
                jpaContentQuery).fetch();
        JPAQuery countResult = countQuery.apply(getQueryFactory());
        return PageableExecutionUtils.getPage(content, pageable,
                () -> countResult.fetch().size());
    }

    protected Query createNativeQuery(final String query) {
        return this.getEntityManager().createQuery(query);
    }

    protected List applyNativeQuery(final Query query) {
        return query.getResultList();

    }

    @Transactional
    public void flush() {
        this.getEntityManager().flush();
    }

    protected void isNull(final Object object) {
        Objects.requireNonNull(object);
    }

    protected <T> Page<T> getPage(final List<T> content, final Integer page, final Integer size,
                                  final long totalElements) {
        return new PageImpl<>(content, Pageable.ofSize(size).withPage(page), totalElements);
    }
}
