package com.gofield.domain.rds.entity.stateLog.repository;


import com.gofield.domain.rds.entity.stateLog.StateLog;

public interface StateLogRepositoryCustom {
    StateLog findByState(String state);
}
