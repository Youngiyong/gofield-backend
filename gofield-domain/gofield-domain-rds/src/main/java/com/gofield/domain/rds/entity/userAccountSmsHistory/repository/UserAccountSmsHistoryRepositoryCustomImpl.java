package com.gofield.domain.rds.entity.userAccountSmsHistory.repository;

import com.gofield.common.utils.LocalDateTimeUtils;
import com.gofield.domain.rds.entity.userAccountSmsHistory.UserAccountSmsHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.gofield.domain.rds.entity.userAccountSmsHistory.QUserAccountSmsHistory.userAccountSmsHistory;

@RequiredArgsConstructor
public class UserAccountSmsHistoryRepositoryCustomImpl implements UserAccountSmsHistoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public UserAccountSmsHistory findByUserIdAndCode(Long userId, String code) {
        return jpaQueryFactory
                .select(userAccountSmsHistory)
                .from(userAccountSmsHistory)
                .where(userAccountSmsHistory.userId.eq(userId),
                        userAccountSmsHistory.code.eq(code))
                .fetchOne();
    }

    @Override
    public List<Long> todaySmsAccountCount(Long userId) {
        return jpaQueryFactory
                .select(userAccountSmsHistory.id)
                .from(userAccountSmsHistory)
                .where(userAccountSmsHistory.userId.eq(userId)
                        ,userAccountSmsHistory.createDate.before(LocalDateTimeUtils.tomorrowMinTime()))
                .fetch();
    }
}
