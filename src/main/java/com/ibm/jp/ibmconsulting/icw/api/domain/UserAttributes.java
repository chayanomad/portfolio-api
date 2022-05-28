package com.ibm.jp.ibmconsulting.icw.api.domain;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import com.ibm.jp.ibmconsulting.icw.api.common.validation.ValidateHelper;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderClassName = "Builder", buildMethodName = "build")
public class UserAttributes {
  @NotNull private final String firstName;
  
  @NotNull private final String lastName;
  
  @NotNull private final String firstNameKana;
  
  @NotNull private final String lastNameKana;

  private final String profilePicUrl;
  
  @NotNull private final String introduction;
  
  private final String email;
  
  private final String linkedIn;
  
  private final String twitter;
  
  private final String facebook;

  public static class Builder {
    public UserAttributes build() {
      final UserAttributes attributes =
          new UserAttributes(firstName, lastName, firstNameKana, lastNameKana, profilePicUrl, introduction, email, linkedIn, twitter, facebook);
      ValidateHelper.validate(attributes);
      return attributes;
    }
  }

  public Optional<String> getProfilePicUrl() {
    return Optional.ofNullable(profilePicUrl);
  }

  public Optional<String> getEmail() {
    return Optional.ofNullable(email);
  }

  public Optional<String> getLinkedIn() {
    return Optional.ofNullable(linkedIn);
  }

  public Optional<String> getTwitter() {
    return Optional.ofNullable(twitter);
  }

  public Optional<String> getFacebook() {
    return Optional.ofNullable(facebook);
  }
}
