package com.ibm.jp.ibmconsulting.icw.api.domain.query;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/** 
 * JPAのCriteria Queryを利用する為のmetamodel
 */
@SuppressWarnings({
  "PMD.StdCyclomaticComplexity",
  "PMD.ClassNamingConventions",
  "PMD.AvoidUsingVolatile",
  "PMD.LinguisticNaming",
  "PMD.TooManyFields",
  "PMD.UseUtilityClass"
})
@StaticMetamodel(ItemSummary.class)
public class ItemSummary_ {
  public static volatile SingularAttribute<ItemSummary, String> id;
  public static volatile SingularAttribute<ItemSummary, String> category;
  public static volatile SingularAttribute<ItemSummary, String> name;
  public static volatile SingularAttribute<ItemSummary, String> kana;
  public static volatile SingularAttribute<ItemSummary, Integer> price;
  public static volatile SingularAttribute<ItemSummary, Integer> amount;

  /**
   * 指定したalias keyに紐づくSingularAttributeを返す。紐づくものがない場合はnullを返す。
   * 
   * @param key alias key
   * @return
   */
  public static SingularAttribute<ItemSummary, ?> getSingularAttribute(String key) {
    switch (key) {
      case "id":
        return id;
      case "name":
        return name;
      case "price":
        return price;
      case "stock":
        return amount;
      default:
        return null;
    }
  }
}