package com.ibm.jp.ibmconsulting.icw.api.domain;

import java.util.Optional;

public interface UserRepository {
  Optional<User> find(String id);
}
