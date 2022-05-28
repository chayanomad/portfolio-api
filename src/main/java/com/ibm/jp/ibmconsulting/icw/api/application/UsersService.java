package com.ibm.jp.ibmconsulting.icw.api.application;

import javax.inject.Inject;

import com.ibm.jp.ibmconsulting.icw.api.domain.User;
import com.ibm.jp.ibmconsulting.icw.api.domain.UserNotFoundException;
import com.ibm.jp.ibmconsulting.icw.api.domain.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@ApplicationScope
@RequiredArgsConstructor
public class UsersService {
  @Inject private final UserRepository repository;

  public User get(final String id) {
    final User user = repository.find(id).orElseThrow(() -> new UserNotFoundException(id));
    return user;
  }
}
