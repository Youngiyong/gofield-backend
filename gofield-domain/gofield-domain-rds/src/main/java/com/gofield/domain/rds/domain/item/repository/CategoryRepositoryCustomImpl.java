package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.Category;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.gofield.domain.rds.domain.item.QCategory.category;
import static com.gofield.domain.rds.domain.order.QOrder.order;
import static com.gofield.domain.rds.domain.order.QOrderShipping.orderShipping;

@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression containKeyword(String keyword){
        if (keyword == null) {
            return null;
        }
        return category.name.contains(keyword);
    }

    private BooleanExpression parentIsNull(String keyword){
        if(keyword==null || keyword.equals("")){
          return category.parent.isNull();
        }
        return null;
    }

    private BooleanExpression eqParentId(Long parentId){
        if(parentId==null){
            return null;
        }
        return category.parent.id.eq(parentId);
    }

    @Override
    public List<Category> findAllIsActiveAndIsAttentionOrderBySort() {
        return jpaQueryFactory
                .selectFrom(category)
                .where(category.isActive.isTrue(), category.isAttention.isTrue())
                .orderBy(category.sort.asc())
                .fetch();
    }

    @Override
    public List<Category> findAllSubCategoryByCategoryId(Long categoryId) {
        return jpaQueryFactory
                .selectFrom(category)
                .where(category.parent.id.eq(categoryId),
                        category.isActive.isTrue())
                .orderBy(category.sort.asc())
                .fetch();
    }

    @Override
    public List<Category> findAllByInId(List<Long> idList) {
        return jpaQueryFactory
                .selectFrom(category)
                .where(category.id.in(idList))
                .fetch();
    }

    @Override
    public Category findByName(String name) {
        return jpaQueryFactory
                .selectFrom(category)
                .where(category.name.eq(name))
                .fetchOne();
    }

    @Override
    public Category findByCategoryId(Long id) {
        return jpaQueryFactory
                .selectFrom(category)
                .where(category.id.eq(id))
                .fetchFirst();
    }

    @Override
    public Category findByParentIdAndName(Long parentId, String name) {
        return jpaQueryFactory
                .selectFrom(category)
                .where(eqParentId(parentId),
                        category.name.eq(name))
                .fetchOne();
    }

    @Override
    public List<Category> findAllIsActiveOrderBySort() {
        return jpaQueryFactory
                .selectFrom(category)
                .where(category.isActive.isTrue())
                .orderBy(category.name.asc(), category.sort.desc())
                .fetch();
    }

    @Override
    public List<Category> findAllNotParentIdIsActiveOrderBySort() {
        return jpaQueryFactory
                .selectFrom(category)
                .where(category.isActive.isTrue(), category.parent.isNull())
                .orderBy(category.name.asc(), category.sort.desc())
                .fetch();
    }

    @Override
    public Page<Category> findAllByKeyword(String keyword, Pageable pageable) {
        List<Category> content = jpaQueryFactory
                .selectFrom(category)
                .where(parentIsNull(keyword), containKeyword(keyword))
                .orderBy(category.name.asc())
                .fetch();

        for(Category category: content){
            List<Category> children = category.getChildren();
            children.stream().forEach(o -> o.getChildren());
            for(Category sub: children){
                List<Category> subChildren = sub.getChildren();
                subChildren.stream().forEach(o -> o.getChildren());
            }
        }

        if(content.isEmpty()){
            return new PageImpl<>(content, pageable, 0);
        }

        List<Long> totalCount = jpaQueryFactory
                .select(category.id)
                .from(category)
                .where(containKeyword(keyword))
                .fetch();

        return new PageImpl<>(content, pageable, totalCount.size());
    }
}
