package com.gofield.domain.rds.domain.cart.repository;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.cart.Cart;
import com.gofield.domain.rds.domain.cart.projection.CartProjection;
import com.gofield.domain.rds.domain.cart.projection.QCartProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.cart.QCart.cart;
import static com.gofield.domain.rds.domain.item.QItem.item;
import static com.gofield.domain.rds.domain.item.QItemDetail.itemDetail;
import static com.gofield.domain.rds.domain.item.QItemOption.itemOption;
import static com.gofield.domain.rds.domain.item.QItemStock.itemStock;
import static com.gofield.domain.rds.domain.seller.QSeller.seller;
import static com.gofield.domain.rds.domain.seller.QShippingTemplate.shippingTemplate;
import static com.gofield.domain.rds.domain.user.QUser.user;
@RequiredArgsConstructor
public class CartRepositoryCustomImpl implements CartRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public int getCartCount(Long userId) {
        List<Long> cartList = jpaQueryFactory
                .select(cart.id)
                .from(cart)
                .innerJoin(user)
                .on(cart.userId.eq(user.id))
                .where(user.id.eq(userId))
                .fetch();

        return cartList.size();
    }

    @Override
    public Cart findByUserIdAndItemNumber(Long userId, String itemNumber) {
        return jpaQueryFactory
                .selectFrom(cart)
                .where(cart.userId.eq(userId),
                        cart.itemNumber.eq(itemNumber))
                .fetchOne();
    }

    @Override
    public Cart findByCartIdAndUserId(Long cartId, Long userId) {
        return jpaQueryFactory
                .selectFrom(cart)
                .where(cart.id.eq(cartId),
                        cart.userId.eq(userId))
                .fetchFirst();
    }

    @Override
    public List<CartProjection> findAllByUserId(Long userId) {
        return jpaQueryFactory
                .select(new QCartProjection(
                        cart.id,
                        item.name,
                        cart.itemNumber,
                        itemStock.sellerId,
                        seller.name,
                        itemOption.name,
                        item.thumbnail.prepend(Constants.CDN_URL),
                        cart.qty,
                        cart.isOrder,
                        item.classification,
                        itemDetail.spec,
                        itemDetail.gender,
                        shippingTemplate.isCondition,
                        shippingTemplate.condition,
                        shippingTemplate.chargeType,
                        shippingTemplate.charge,
                        shippingTemplate.feeJeju,
                        shippingTemplate.feeJejuBesides))
                .from(cart)
                .innerJoin(itemStock)
                .on(cart.itemNumber.eq(itemStock.itemNumber))
                .innerJoin(seller)
                .on(itemStock.sellerId.eq(seller.id))
                .innerJoin(shippingTemplate)
                .on(seller.id.eq(shippingTemplate.seller.id))
                .innerJoin(item)
                .on(itemStock.item.id.eq(item.id))
                .leftJoin(itemOption)
                .on(item.itemNumber.eq(itemOption.itemNumber))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .where(cart.userId.eq(userId))
                .fetch();
    }
}
