package com.ibm.jp.ibmconsulting.icw.api.ui.users;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;

import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;
import com.ibm.jp.ibmconsulting.icw.api.domain.User;
import com.ibm.jp.ibmconsulting.icw.api.domain.UserAttributes;

import lombok.Getter;

@Getter
public class UserResponse {
  @NotNull private final String id;
  
  @NotNull
  @JsonbProperty("first_name")
  private final String firstName;
  
  @NotNull
  @JsonbProperty("last_name")
  private final String lastName;
  
  @NotNull
  @JsonbProperty("first_name_kana")
  private final String firstNameKana;
  
  @NotNull
  @JsonbProperty("last_name_kana")
  private final String lastNameKana;
  
  @JsonbProperty("profile_pic_url")
  private final String profilePicUrl;
  
  @NotNull private final String introduction;
  
  private final String email;
  
  private final String linkedIn;
  
  private final String twitter;
  
  private final String facebook;

  public UserResponse(User user) {
    final UserAttributes attributes = user.getAttributes();
    this.id = user.getId();
    this.firstName = attributes.getFirstName();
    this.lastName = attributes.getLastName();
    this.firstNameKana = attributes.getFirstNameKana();
    this.lastNameKana = attributes.getLastNameKana();
    this.profilePicUrl = attributes.getProfilePicUrl().orElse(null);
    this.introduction = attributes.getIntroduction();
    this.email = attributes.getEmail().orElse(null);
    this.linkedIn = attributes.getLinkedIn().orElse(null);
    this.twitter = attributes.getTwitter().orElse(null);
    this.facebook = attributes.getFacebook().orElse(null);
    ValidateHelper.validate(this);
  }
}
