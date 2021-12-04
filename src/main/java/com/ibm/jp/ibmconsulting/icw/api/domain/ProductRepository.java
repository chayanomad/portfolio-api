package com.ibm.jp.ibmconsulting.icw.api.domain;

import java.util.Optional;

import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductQueryResult;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;

public interface ProductRepository {

  ProductQueryResult query(ProductQueryCondition conditionl, PaginationCondition pCondition);

  Optional<Product> find(String id);
}
