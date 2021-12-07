package com.ibm.jp.ibmconsulting.icw.api.infrastructure;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.StockQueryCondition;

public class StockQueryAgent {
  
  private final EntityManager manager;
  private final StockQueryCondition condition;
  private final PaginationCondition pCondition;

  public StockQueryAgent(
      EntityManager manager,
      StockQueryCondition condition,
      PaginationCondition pCondition) {
    this.manager = manager;
    this.condition = condition;
    this.pCondition = pCondition;
  }

  protected List<Stocks> getStocks() {
    final CriteriaBuilder cb = manager.getCriteriaBuilder();
    final CriteriaQuery<Stocks> cq = cb.createQuery(Stocks.class);
    final Root<Stocks> stocks = cq.from(Stocks.class);
    
    cq.where(where(cb, stocks));

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
    final Root<Stocks> stocks = cq.from(Stocks.class);

    cq.where(where(cb, stocks));
    cq.select(cb.count(stocks));

    return manager.createQuery(cq).getSingleResult();
  }

  private Predicate where(CriteriaBuilder cb, Root<Stocks> stocks) {
    final List<Predicate> whereConditions = new ArrayList<>();
    condition
        .getIds()
        .ifPresent(
            ids -> whereConditions.add(stocks.get(Stocks_.productId).in(ids)));
    return cb.and(whereConditions.stream().toArray(Predicate[]::new));
  }
}
