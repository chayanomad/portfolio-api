package com.ibm.jp.ibmconsulting.icw.api.ui.stocks;

import com.ibm.jp.ibmconsulting.icw.api.application.stocksService;
import com.ibm.jp.ibmconsulting.icw.api.common.log.Logging;
import com.ibm.jp.ibmconsulting.icw.api.domain.Stock;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.BeanValidateHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("stocks")
@RequiredArgsConstructor
public class StocksResource {
  final private stocksService service;

  /** 在庫取得API */
  @GetMapping(
      value = "{id}",
      produces = { "application/json" })
  @Logging
  public ResponseEntity<StockResponse> getStock(
      @PathVariable("id") String params1) {

    final Stock response = service.get(params1);

    return new ResponseEntity<StockResponse>(new StockResponse(response), HttpStatus.OK);
  }

  /** 購入API */
  @PutMapping(
      value = "{id}/buy",
      produces = { MediaType.APPLICATION_JSON_VALUE },
      consumes = { MediaType.APPLICATION_JSON_VALUE })
  @Logging
  public ResponseEntity<StockResponse> putStock(
      @PathVariable("id") String params1,
      @RequestBody PutRequestBody params2) {
    
    BeanValidateHelper.validate(params2);

    final Stock response = service.putStock(params1, params2.getAmount());

    return new ResponseEntity<StockResponse>(new StockResponse(response), HttpStatus.OK);
  }
}
