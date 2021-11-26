package com.ibm.jp.ibmconsulting.icw.api.ui.items;

import java.util.List;
import com.ibm.jp.ibmconsulting.icw.api.application.ItemsService;
import com.ibm.jp.ibmconsulting.icw.api.common.log.Logging;
import com.ibm.jp.ibmconsulting.icw.api.domain.Item;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemQueryResult;
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
@RequestMapping("items")
@RequiredArgsConstructor
public class ItemsResource {
  final private ItemsService service;

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

    final ItemQueryResult result =
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
  public ResponseEntity<ItemResponse> getItem(
      @PathVariable("id") String params1) {
    
    final Item response = service.get(params1);
    
    return new ResponseEntity<ItemResponse>(new ItemResponse(response), HttpStatus.OK);
  }
}
