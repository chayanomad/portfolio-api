package com.ibm.jp.ibmconsulting.icw.api.infrastructure;

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
@StaticMetamodel(Stocks.class)
public class Stocks_ {
  public static volatile SingularAttribute<Stocks, String> productId;
}
