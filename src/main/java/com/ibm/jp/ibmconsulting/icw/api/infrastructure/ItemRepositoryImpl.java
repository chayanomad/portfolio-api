package com.ibm.jp.ibmconsulting.icw.api.infrastructure;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ibm.jp.ibmconsulting.icw.api.domain.Item;
import com.ibm.jp.ibmconsulting.icw.api.domain.ItemRepository;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemQueryCondition;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemQueryResult;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.ItemSummary;
import com.ibm.jp.ibmconsulting.icw.api.domain.query.PaginationCondition;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.RequiredArgsConstructor;

@Repository
@ApplicationScope
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
  @PersistenceContext private EntityManager manager;

  @Override
  public ItemQueryResult query(
      ItemQueryCondition condition, PaginationCondition pCondition) {
    final ItemQueryAgent agent =
        new ItemQueryAgent(manager, condition, pCondition);
    return new ItemQueryResult(agent.getCount(), agent.getItems());
  }

  @Override
  public Optional<Item> find(String id) {
    final ItemSummary items =
        manager.find(ItemSummary.class, id);
    return Objects.nonNull(items) ? Optional.of(items.convert()) : Optional.empty();
  } 
}
