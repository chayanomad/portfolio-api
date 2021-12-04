package com.ibm.jp.ibmconsulting.icw.api.infrastructure;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ibm.jp.ibmconsulting.icw.api.domain.Product;
import com.ibm.jp.ibmconsulting.icw.api.domain.ProductRepository;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductQueryResult;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductSummary;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.RequiredArgsConstructor;

@Repository
@ApplicationScope
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
  @PersistenceContext private EntityManager manager;

  @Override
  public ProductQueryResult query(
      ProductQueryCondition condition, PaginationCondition pCondition) {
    final ProductQueryAgent agent =
        new ProductQueryAgent(manager, condition, pCondition);
    return new ProductQueryResult(agent.getCount(), agent.getItems());
  }

  @Override
  public Optional<Product> find(String id) {
    final ProductSummary products =
        manager.find(ProductSummary.class, id);
    return Objects.nonNull(products) ? Optional.of(products.convert()) : Optional.empty();
  } 
}
