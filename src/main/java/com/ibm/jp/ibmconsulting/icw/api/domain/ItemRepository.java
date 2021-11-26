package com.ibm.jp.ibmconsulting.icw.api.domain;

import java.util.Optional;

import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemQueryResult;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;

public interface ItemRepository {

  ItemQueryResult query(ItemQueryCondition conditionl, PaginationCondition pCondition);

  Optional<Item> find(String id);
}
