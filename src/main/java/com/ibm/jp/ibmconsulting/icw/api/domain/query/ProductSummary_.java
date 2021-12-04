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
@StaticMetamodel(ProductSummary.class)
public class ProductSummary_ {
  public static volatile SingularAttribute<ProductSummary, String> id;
  public static volatile SingularAttribute<ProductSummary, String> category;
  public static volatile SingularAttribute<ProductSummary, String> name;
  public static volatile SingularAttribute<ProductSummary, String> kana;
  public static volatile SingularAttribute<ProductSummary, Integer> price;
  public static volatile SingularAttribute<ProductSummary, Integer> amount;

  /**
   * 指定したalias keyに紐づくSingularAttributeを返す。紐づくものがない場合はnullを返す。
   * 
   * @param key alias key
   * @return
   */
  public static SingularAttribute<ProductSummary, ?> getSingularAttribute(String key) {
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