package com.ibm.jp.ibmconsulting.icw.api.ui.products;

import java.util.List;

import javax.inject.Inject;

import com.ibm.jp.ibmconsulting.icw.api.application.ProductsService;
import com.ibm.jp.ibmconsulting.icw.api.common.log.Logging;
import com.ibm.jp.ibmconsulting.icw.api.domain.Product;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ProductQueryResult;
import com.ibm.jp.ibmconsulting.icw.api.ui.validation.BeanValidateHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductsResource {
  @Inject private final ProductsService service;

  /** 商品検索API */
  @GetMapping(
      produces = { "application/json" })
  @Logging
  public ResponseEntity<QueryResponse> query(
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "category", required = false) List<String> category,
      @RequestParam(name = "minprice", required = false) Integer minPrice,
      @RequestParam(name = "maxprice", required = false) Integer maxPrice,
      @RequestParam(name = "sort", required = false) String sort,
      @RequestParam(name = "order", required = false) String order,
      @RequestParam(name = "offset", required = false) Integer offset,
      @RequestParam(name = "limit", required = false) Integer limit) {
    final QueryRequestParams query = new QueryRequestParams(name, category, minPrice, maxPrice, sort, order, offset, limit);

    BeanValidateHelper.validate(query);

    final ProductQueryResult result =
        service.query(
            query.toItemQueryCondition(),
            query.toPaginationCondition());
    
    return new ResponseEntity<QueryResponse>(new QueryResponse(result), HttpStatus.OK);
  }

  /** 商品取得API */
  @GetMapping(
      value = "{id}",
      produces = { "application/json" })
  @Logging
  public ResponseEntity<ProductResponse> getItem(
      @PathVariable("id") String params1) {
    
    final Product response = service.get(params1);
    
    return new ResponseEntity<ProductResponse>(new ProductResponse(response), HttpStatus.OK);
  }
}
