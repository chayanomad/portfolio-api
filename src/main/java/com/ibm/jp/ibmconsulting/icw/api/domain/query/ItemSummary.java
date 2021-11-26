package com.ibm.jp.ibmconsulting.icw.api.domain.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ibm.jp.ibmconsulting.icw.api.domain.Category;
import com.ibm.jp.ibmconsulting.icw.api.domain.Item;
import com.ibm.jp.ibmconsulting.icw.api.domain.ItemAttributes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ITEM_SUMMARIES")
public class ItemSummary {
  @Id
  private String id;

  @Column(name = "CATEGORY")
  private String category;

  @Column(name = "NAME")
  private String name;

  @Column(name = "KANA")
  private String kana;

  @Column(name = "PRICE")
  private int price;

  @Column(name = "COMMENT")
  private String comment;

  @Column(name = "IMAGE_URL")
  private String imageURL;

  @Column(name = "AMOUNT")
  private int amount;

  public ItemSummary(
      String id,
      String category,
      String name,
      String kana,
      int price,
      String comment,
      String imageURL,
      int amount) {
    this.id = id;
    this.category = category;
    this.name = name;
    this.kana = kana;
    this.price = price;
    this.comment = comment;
    this.imageURL = imageURL;
    this.amount = amount;
  }

  public Item convert() {
    final ItemAttributes attributes =
        ItemAttributes.builder()
            .category(Category.valueOf(category))
            .name(name)
            .kana(kana)
            .price(price)
            .comment(comment)
            .imageURL(imageURL)
            .amount(amount)
            .build();
    return new Item(id, attributes);
  }
}
