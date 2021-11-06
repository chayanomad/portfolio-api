package com.ibm.jp.ibmconsulting.icw.api.application;

import com.ibm.jp.ibmconsulting.icw.api.domain.Stock;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockAttributes;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockNotFoundException;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockNotInStockException;
import com.ibm.jp.ibmconsulting.icw.api.domain.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@ApplicationScope
@RequiredArgsConstructor
public class stocksService {
  @Autowired private StockRepository repository;

  public Stock get(String id) {
    final Stock stock = repository.find(id).orElseThrow(() -> new StockNotFoundException(id));
    return stock;
  }

  public Stock putStock(String id, int amount) {
    final Stock stock = repository.find(id).orElseThrow(() -> new StockNotFoundException(id));
    if (stock.getAttributes().notInStock(amount)) {
      throw new StockNotInStockException(id, stock.getAttributes().getAmount());
    }
    final StockAttributes attributes = stock.getAttributes().buyStockUpdated(amount);
    final Stock updatedStock = repository.update(stock.getId(), attributes);
    
    return updatedStock;
  }
}
