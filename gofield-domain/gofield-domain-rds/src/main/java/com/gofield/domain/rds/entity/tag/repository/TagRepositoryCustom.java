package com.gofield.domain.rds.entity.tag.repository;


import java.util.List;

public interface TagRepositoryCustom {

    List<String> findAllByItemId(Long itemId);

}
