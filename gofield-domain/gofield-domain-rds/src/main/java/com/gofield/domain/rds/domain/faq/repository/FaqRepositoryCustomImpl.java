package com.gofield.domain.rds.domain.faq.repository;

import com.gofield.domain.rds.domain.faq.Faq;
import com.gofield.domain.rds.domain.item.ItemBundleReview;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.domain.faq.QFaq.faq;


@RequiredArgsConstructor
public class FaqRepositoryCustomImpl implements FaqRepositoryCustom {

    private BooleanExpression eqUse(Boolean isUse){
        if (isUse == null || !isUse) {
            return faq.isUse.isFalse();
        }
        return null;
    }

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Faq> findAllOrderBySort() {
        return null;
    }

    @Override
    public Page<Faq> findAllPaging(Pageable pageable, Boolean isUse) {
        List<Faq> result = jpaQueryFactory
                .selectFrom(faq)
                .from(faq)
                .where(eqUse(isUse))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(faq.id.desc())
                .fetch();

        List<Long> totalCount = jpaQueryFactory
                .select(faq.id)
                .from(faq)
                .fetch();

        return new PageImpl<>(result, pageable, totalCount.size());
    }

    @Override
    public Faq findByNoticeId(Long noticeId) {
        return null;
    }
}
