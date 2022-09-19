package com.gofield.domain.rds.entity.userKeyword.repository;

import com.gofield.domain.rds.entity.userKeyword.UserKeyword;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;


import java.util.List;

import static com.gofield.domain.rds.entity.userKeyword.QUserKeyword.userKeyword;
@RequiredArgsConstructor
public class UserKeywordRepositoryCustomImpl implements UserKeywordRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserKeyword findByUserIdAndKeyword(Long userId, String keyword) {
        return jpaQueryFactory
                .selectFrom(userKeyword)
                .where(userKeyword.user.id.eq(userId),
                        userKeyword.keyword.eq(keyword))
                .fetchOne();
    }

    @Override
    public List<UserKeyword> findByUserId(Long userId, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(userKeyword)
                .from(userKeyword)
                .where(userKeyword.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(userKeyword.count.desc())
                .fetch();
    }


}
