package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.common.EStatusFlag;
import com.gofield.domain.rds.domain.item.Brand;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.domain.item.QBrand.brand;


@RequiredArgsConstructor
public class BrandRepositoryCustomImpl implements BrandRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression containName(String name) {
        if (name == null) {
            return null;
        }
        return brand.name.contains(name);
    }

    @Override
    public Page<Brand> findAllByKeyword(String keyword, Pageable pageable) {
        List<Brand> content = jpaQueryFactory
                .selectFrom(brand)
                .where(brand.status.ne(EStatusFlag.DELETE), containName(keyword))
                .orderBy(brand.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        List<Long> totalCount = jpaQueryFactory
                .select(brand.id)
                .from(brand)
                .where(brand.status.ne(EStatusFlag.DELETE), containName(keyword))
                .fetch();

        return new PageImpl<>(content, pageable, totalCount.size());
    }

    @Override
    public Brand findByBrandId(Long id) {
        return jpaQueryFactory
                .selectFrom(brand)
                .where(brand.id.eq(id))
                .fetchFirst();
    }

    @Override
    public Brand findByName(String name) {
        return jpaQueryFactory
                .selectFrom(brand)
                .where(brand.name.eq(name))
                .fetchOne();
    }
}
