package com.ibm.jp.ibmconsulting.icw.api.infrastructure;

import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.ibm.jp.ibmconsulting.icw.api.domain.Stock;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockAttributes;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockNotFoundException;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;
import lombok.RequiredArgsConstructor;

@Repository
@ApplicationScope
@RequiredArgsConstructor
public class StocksRepositoryImpl implements StockRepository {
  @PersistenceContext private EntityManager manager;
  
  public Optional<Stock> find(String id) {
    final Stocks stockEntity =
        manager.find(Stocks.class, id);
    return Objects.nonNull(stockEntity) ? Optional.of(stockEntity.convert()) : Optional.empty();
  }

  public Stock update(String id, StockAttributes attributes) {
    final Stocks stockEntity =
        manager.find(Stocks.class, id);
    if (Objects.isNull(stockEntity)) {
      throw new StockNotFoundException(id);
    }

    stockEntity.update(attributes);

    return stockEntity.convert();
  }
}
