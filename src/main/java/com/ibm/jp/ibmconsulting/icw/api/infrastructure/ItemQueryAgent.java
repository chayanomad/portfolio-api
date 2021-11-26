package com.ibm.jp.ibmconsulting.icw.api.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemSummary;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemSummary_;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;

public class ItemQueryAgent {
  
  private final EntityManager manager;
  private final ItemQueryCondition condition;
  private final PaginationCondition pCondition;

  public ItemQueryAgent(
      EntityManager manager,
      ItemQueryCondition condition,
      PaginationCondition pCondition) {
    this.manager = manager;
    this.condition = condition;
    this.pCondition = pCondition;
  }

  protected List<ItemSummary> getItems() {
    final CriteriaBuilder cb = manager.getCriteriaBuilder();
    final CriteriaQuery<ItemSummary> cq = cb.createQuery(ItemSummary.class);
    final Root<ItemSummary> items = cq.from(ItemSummary.class);

    cq.where(where(cb, items));
    // ソートキーの設定
    final SingularAttribute<ItemSummary, ?> attribute =
        ItemSummary_.getSingularAttribute(pCondition.getSort());
    if (Objects.nonNull(attribute)) {
      Order order;
      if (pCondition.isAscending()) {
        order = cb.asc(items.get(attribute));
      } else {
        order = cb.desc(items.get(attribute));
      }
      cq.orderBy(order);
    }

    return pCondition.getLimit() == 0
        ? manager
            .createQuery(cq)
            .setFirstResult(pCondition.getOffset())
            .getResultList()
        : manager
            .createQuery(cq)
            .setFirstResult(pCondition.getOffset())
            .setMaxResults(pCondition.getLimit())
            .getResultList();
  }

  protected Long getCount() {
    final CriteriaBuilder cb = manager.getCriteriaBuilder();
    final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    final Root<ItemSummary> items = cq.from(ItemSummary.class);
    // 検索条件の指定
    cq.where(where(cb, items));
    cq.select(cb.count(items));

    return manager.createQuery(cq).getSingleResult();
  }

  /** 
   * クエリ条件を基にしたPredicateを作成する
   * 
   * @param cb
   * @param items
   * @return
   */
  private Predicate where(CriteriaBuilder cb, Root<ItemSummary> items) {
    final List<Predicate> whereConditions = new ArrayList<>();
    // 商品名
    condition
        .getName()
        .ifPresent(
            name -> {
              whereConditions.add(
                  cb.or(
                      cb.like(
                          items.get(ItemSummary_.name), "%" + name + "%"),
                      cb.like(
                          items.get(ItemSummary_.kana), "%" + name + "%")));
            });
    
    // カテゴリー
    condition
        .getCategories()
        .ifPresent(
            categories -> {
              final List<Predicate> categoryConditions =
                  categories
                      .stream()
                      .map(
                          c -> 
                              cb.equal(items.get(ItemSummary_.category), c))
                      .collect(Collectors.toList());
              if (!categoryConditions.isEmpty()) {
                whereConditions.add(cb.or(categoryConditions.stream().toArray(Predicate[]::new)));
              }
            });
        
    // 下限金額
    condition
        .getMinPrice()
        .ifPresent(
            minPrice -> {
              whereConditions.add(
                  cb.greaterThanOrEqualTo(items.get(ItemSummary_.price), minPrice));
            });
      
    // 上限金額
    condition
        .getMaxPrice()
        .ifPresent(
            maxPrice -> {
              whereConditions.add(
                  cb.lessThanOrEqualTo(items.get(ItemSummary_.price), maxPrice));
            });
    
    return cb.and(whereConditions.stream().toArray(Predicate[]::new));
  }
}
