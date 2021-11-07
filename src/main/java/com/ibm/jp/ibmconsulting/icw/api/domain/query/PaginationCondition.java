package com.ibm.jp.ibmconsulting.icw.api.domain.query;

import lombok.Getter;

@Getter
public class PaginationCondition {
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
    this.offset = offset;
    this.limit = limit;
  }
}
