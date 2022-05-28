package com.ibm.jp.ibmconsulting.icw.api.infrastructure;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ibm.jp.ibmconsulting.icw.api.domain.User;
import com.ibm.jp.ibmconsulting.icw.api.domain.UserRepository;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.RequiredArgsConstructor;

@Repository
@ApplicationScope
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  @PersistenceContext private EntityManager manager;

  @Override
  public Optional<User> find(String id) {
    final UserEntity user = manager.find(UserEntity.class, id);
    return Objects.nonNull(user) ? Optional.of(user.convert()) : Optional.empty();
  }
}
