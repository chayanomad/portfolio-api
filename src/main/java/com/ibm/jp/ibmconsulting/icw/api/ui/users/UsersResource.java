package com.ibm.jp.ibmconsulting.icw.api.ui.users;

import javax.inject.Inject;

import com.ibm.jp.ibmconsulting.icw.api.application.UsersService;
import com.ibm.jp.ibmconsulting.icw.api.common.log.Logging;
import com.ibm.jp.ibmconsulting.icw.api.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersResource {
  @Inject private final UsersService service;

  /** ユーザー取得API */
  @GetMapping(
    value = "{id}",
    produces = { "application/json" })
  @Logging
  public ResponseEntity<UserResponse> getUser(
      @PathVariable("id") String params) {
    
    final User user = service.get(params);

    return new ResponseEntity<UserResponse>(new UserResponse(user), HttpStatus.OK);
  }
}
