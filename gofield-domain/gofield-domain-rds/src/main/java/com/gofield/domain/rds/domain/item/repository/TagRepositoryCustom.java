package com.gofield.domain.rds.domain.item.repository;


import java.util.List;

public interface TagRepositoryCustom {

    List<String> findAllByItemId(Long itemId);

}
