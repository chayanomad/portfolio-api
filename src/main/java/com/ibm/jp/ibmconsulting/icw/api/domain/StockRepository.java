package com.ibm.jp.ibmconsulting.icw.api.domain;

import java.util.Optional;

public interface StockRepository {
  
  Optional<Stock> find(String id);

  Stock update(String id, StockAttributes attributes);
}
