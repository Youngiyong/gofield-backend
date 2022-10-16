package com.gofield.domain.rds.entity.stateLog;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.enums.EEnvironmentFlag;
import com.gofield.domain.rds.enums.ESocialFlag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StateLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String state;

    @Column(name ="env_flag", nullable = false)
    private EEnvironmentFlag environment;

    @Column(name = "social_flag", nullable = false)
    private ESocialFlag social;

    @Column
    private LocalDateTime createDate;

    private StateLog(String state, ESocialFlag social, EEnvironmentFlag environment){
        this.state = state;
        this.social = social;
        this.environment = environment;
    }

    public static StateLog newInstance(String state, ESocialFlag social, EEnvironmentFlag environment){
        return new StateLog(state, social, environment);
    }
}
