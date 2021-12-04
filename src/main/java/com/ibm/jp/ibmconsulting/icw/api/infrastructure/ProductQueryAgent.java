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

import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductSummary;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductSummary_;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;

public class ProductQueryAgent {
  
  private final EntityManager manager;
  private final ProductQueryCondition condition;
  private final PaginationCondition pCondition;

  public ProductQueryAgent(
      EntityManager manager,
      ProductQueryCondition condition,
      PaginationCondition pCondition) {
    this.manager = manager;
    this.condition = condition;
    this.pCondition = pCondition;
  }

  protected List<ProductSummary> getItems() {
    final CriteriaBuilder cb = manager.getCriteriaBuilder();
    final CriteriaQuery<ProductSummary> cq = cb.createQuery(ProductSummary.class);
    final Root<ProductSummary> products = cq.from(ProductSummary.class);

    cq.where(where(cb, products));
    // ソートキーの設定
    final SingularAttribute<ProductSummary, ?> attribute =
        ProductSummary_.getSingularAttribute(pCondition.getSort());
    if (Objects.nonNull(attribute)) {
      Order order;
      if (pCondition.isAscending()) {
        order = cb.asc(products.get(attribute));
      } else {
        order = cb.desc(products.get(attribute));
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
    final Root<ProductSummary> products = cq.from(ProductSummary.class);
    // 検索条件の指定
    cq.where(where(cb, products));
    cq.select(cb.count(products));

    return manager.createQuery(cq).getSingleResult();
  }

  /** 
   * クエリ条件を基にしたPredicateを作成する
   * 
   * @param cb
   * @param products
   * @return
   */
  private Predicate where(CriteriaBuilder cb, Root<ProductSummary> products) {
    final List<Predicate> whereConditions = new ArrayList<>();
    // 商品名
    condition
        .getName()
        .ifPresent(
            name -> {
              whereConditions.add(
                  cb.or(
                      cb.like(
                          products.get(ProductSummary_.name), "%" + name + "%"),
                      cb.like(
                          products.get(ProductSummary_.kana), "%" + name + "%")));
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
                              cb.equal(products.get(ProductSummary_.category), c))
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
                  cb.greaterThanOrEqualTo(products.get(ProductSummary_.price), minPrice));
            });
      
    // 上限金額
    condition
        .getMaxPrice()
        .ifPresent(
            maxPrice -> {
              whereConditions.add(
                  cb.lessThanOrEqualTo(products.get(ProductSummary_.price), maxPrice));
            });
    
    return cb.and(whereConditions.stream().toArray(Predicate[]::new));
  }
}
