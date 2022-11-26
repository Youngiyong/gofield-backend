package com.gofield.domain.rds.domain.user.repository;


import com.gofield.domain.rds.domain.user.StateLog;

public interface StateLogRepositoryCustom {
    StateLog findByState(String state);
}
