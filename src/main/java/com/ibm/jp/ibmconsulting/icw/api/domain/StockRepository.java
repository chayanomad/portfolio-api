package com.ibm.jp.ibmconsulting.icw.api.domain;

import java.util.Optional;

import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.StockQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.StockQueryResult;

public interface StockRepository {

  StockQueryResult query(StockQueryCondition condition, PaginationCondition pCondition);
  
  Optional<Stock> find(String id);

  Stock update(String id, StockAttributes attributes);
}
