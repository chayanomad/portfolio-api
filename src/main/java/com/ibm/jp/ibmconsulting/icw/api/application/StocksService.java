package com.ibm.jp.ibmconsulting.icw.api.application;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import com.ibm.jp.ibmconsulting.icw.api.domain.Stock;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockAttributes;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockNotFoundException;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockNotInStockException;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockRepository;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.StockQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.StockQueryResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@ApplicationScope
@RequiredArgsConstructor
public class StocksService {
  @Inject private final StockRepository repository;

  public StockQueryResult query(StockQueryCondition condition, PaginationCondition pCondition) {
    final StockQueryResult result = repository.query(condition, pCondition);
    return result;
  }

  public Stock get(final String id) {
    final Stock stock = repository.find(id).orElseThrow(() -> new StockNotFoundException(id));
    return stock;
  }

  public Stock putStock(String id, int amount) {
    final Stock stock = repository.find(id).orElseThrow(() -> new StockNotFoundException(id));
    if (stock.getAttributes().notInStock(amount)) {
      throw new StockNotInStockException(id, stock.getAttributes().getAmount());
    }
    final StockAttributes attributes = stock.getAttributes().buyStockUpdated(amount);
    final Stock updatedStock = repository.update(stock.getItemId(), attributes);
    
    return updatedStock;
  }

  public List<Stock> putStocks(List<Stock> stocks) {
    return stocks
        .stream()
        .peek(s -> {
            final String id = s.getItemId();
            final Stock stock = repository.find(id).orElseThrow(() -> new StockNotFoundException(id));
            final StockAttributes attributes = stock.getAttributes().getStockUpdated(s.getAttributes().getAmount());
            s = repository.update(stock.getItemId(), attributes);})
        .collect(Collectors.toList());
  }
}
