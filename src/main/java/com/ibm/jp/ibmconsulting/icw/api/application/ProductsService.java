package com.ibm.jp.ibmconsulting.icw.api.application;

import javax.inject.Inject;

import com.ibm.jp.ibmconsulting.icw.api.domain.Product;
import com.ibm.jp.ibmconsulting.icw.api.domain.ProductNotFoundException;
import com.ibm.jp.ibmconsulting.icw.api.domain.ProductRepository;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductQueryResult;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@ApplicationScope
@RequiredArgsConstructor
public class ProductsService {
  @Inject private final ProductRepository repository;

  public ProductQueryResult query(ProductQueryCondition condition, PaginationCondition pCondition) {
    final ProductQueryResult result = repository.query(condition, pCondition);
    return result;
  }

  public Product get(final String id) {
    final Product product = repository.find(id).orElseThrow(() -> new ProductNotFoundException(id));
    return product;
  }
}
