package com.ibm.jp.ibmconsulting.icw.api.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ibm.jp.ibmconsulting.icw.api.domain.User;
import com.ibm.jp.ibmconsulting.icw.api.domain.UserAttributes;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name = "USERS")
public class UserEntity {
  @Id
  @Column(name = "ID")
  private String id;

  @Column(name = "first_name")
  private String firstName;
  
  @Column(name = "last_name")
  private String lastName;
  
  @Column(name = "first_name_kana")
  private String firstNameKana;
  
  @Column(name = "last_name_kana")
  private String lastNameKana;
  
  @Column(name = "profile_pic_url")
  private String profilePicUrl;
  
  @Column(name = "introduction")
  private String introduction;
  
  @Column(name = "email")
  private String email;
  
  @Column(name = "linked_in")
  private String linkedIn;
  
  @Column(name = "twitter")
  private String twitter;
  
  @Column(name = "facebook")
  private String facebook;

  public UserEntity(
      String id,
      String firstName, 
      String lastName, 
      String firstNameKana, 
      String lastNameKana,
      String profilePicUrl, 
      String introduction, 
      String email, 
      String linkedIn, 
      String twitter, 
      String facebook) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.firstNameKana = firstNameKana;
    this.lastNameKana = lastNameKana;
    this.profilePicUrl = profilePicUrl;
    this.introduction = introduction;
    this.email = email;
    this.linkedIn = linkedIn;
    this.twitter = twitter;
    this.facebook = facebook;
  }

  public User convert() {
    final UserAttributes attributes =
        UserAttributes.builder()
            .firstName(firstName)
            .lastName(lastName)
            .firstNameKana(firstNameKana)
            .lastNameKana(lastNameKana)
            .profilePicUrl(profilePicUrl)
            .introduction(introduction)
            .email(email)
            .linkedIn(linkedIn)
            .twitter(twitter)
            .facebook(facebook)
            .build();
    return new User(id, attributes);
  }
}
