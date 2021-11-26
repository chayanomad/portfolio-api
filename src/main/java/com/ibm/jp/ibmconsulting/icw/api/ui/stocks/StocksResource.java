package com.ibm.jp.ibmconsulting.icw.api.ui.stocks;

import java.util.List;

import javax.inject.Inject;
import com.ibm.jp.ibmconsulting.icw.api.application.StocksService;
import com.ibm.jp.ibmconsulting.icw.api.common.log.Logging;
import com.ibm.jp.ibmconsulting.icw.api.domain.Stock;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.StockQueryResult;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.BeanValidateHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("stocks")
@RequiredArgsConstructor
public class StocksResource {
  @Inject private final StocksService service;

  /** 在庫検索API */
  @GetMapping(
      produces = { "application/json" })
  @Logging
  public ResponseEntity<QueryResponse> query(
      @RequestParam(name = "id", required = false) List<String> params1,
      @RequestParam(name = "offset", required = false) Integer params2,
      @RequestParam(name = "limit", required = false) Integer params3) {
    final QueryRequestParams query = new QueryRequestParams(params1, params2, params3);
    
    BeanValidateHelper.validate(query);
    
    final StockQueryResult result =
        service.query(
            query.toStockQueryCondition(),
            query.toPaginationCondition());

    return new ResponseEntity<QueryResponse>(new QueryResponse(result), HttpStatus.OK);
  }

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
      produces = { MediaType.APPLICATION_JSON_VALUE })
  @Logging
  public ResponseEntity<StockResponse> putStock(
      @PathVariable("id") String params1,
      @RequestBody PutRequestBody params2) {
    
    BeanValidateHelper.validate(params2);

    final Stock response = service.putStock(params1, params2.getAmount());

    return new ResponseEntity<StockResponse>(new StockResponse(response), HttpStatus.OK);
  }

  /** 在庫管理API */
  @PutMapping(
      value = "manage",
      produces = { MediaType.APPLICATION_JSON_VALUE })
  @Logging
  public ResponseEntity<PutManageResponse> manage(
      @RequestBody PutManageRequestBody params1) {
    
    BeanValidateHelper.validate(params1);
    params1.getStocks().forEach(BeanValidateHelper::validate);

    final List<Stock> response = service.putStocks(params1.convert());

    return new ResponseEntity<PutManageResponse>(new PutManageResponse(response), HttpStatus.OK);
  }
}
