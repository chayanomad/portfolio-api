package com.ibm.jp.ibmconsulting.icw.api.domain.query;

import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class PaginationCondition {
  private final boolean isAscending;
  private final String sort;
  private final int offset;
  private final int limit;

  /**
   * ページネーション条件を初期化する
   * 
   * @param offset 開始番号
   * @param limit 返却数
   */
  public PaginationCondition(
      int offset,
      int limit) {
    this.isAscending = true;
    this.sort = "";
    this.offset = offset;
    this.limit = limit;
  }

  public PaginationCondition(
      String sort,
      @NonNull String defaultSort,
      String order,
      @NonNull String defaultOrder,
      int offset,
      int limit) {
    final String checkedOrder = Objects.isNull(order) ? defaultOrder : order;
    this.isAscending = "asc".equals(checkedOrder);
    this.sort = Objects.isNull(sort) ? defaultSort : sort;
    this.offset = offset;
    this.limit = limit;
  }
}
