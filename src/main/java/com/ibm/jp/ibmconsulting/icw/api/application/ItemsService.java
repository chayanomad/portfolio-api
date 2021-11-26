package com.ibm.jp.ibmconsulting.icw.api.application;

import javax.inject.Inject;

import com.ibm.jp.ibmconsulting.icw.api.domain.Item;
import com.ibm.jp.ibmconsulting.icw.api.domain.ItemNotFoundException;
import com.ibm.jp.ibmconsulting.icw.api.domain.ItemRepository;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemQueryResult;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@ApplicationScope
@RequiredArgsConstructor
public class ItemsService {
  @Inject private final ItemRepository repository;

  public ItemQueryResult query(ItemQueryCondition condition, PaginationCondition pCondition) {
    final ItemQueryResult result = repository.query(condition, pCondition);
    return result;
  }

  public Item get(final String id) {
    final Item item = repository.find(id).orElseThrow(() -> new ItemNotFoundException(id));
    return item;
  }
}
