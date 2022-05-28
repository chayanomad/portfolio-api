package com.ibm.jp.ibmconsulting.icw.api.infrastructure;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import com.ibm.jp.ibmconsulting.icw.api.domain.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

@Repository
@ApplicationScope
public class SampleRepositoryImpl implements SampleRepository {
  @Autowired private EntityManagerFactory factory;
  private final String sql = "SELECT COUNT(u.id) FROM USERS u limit 1";

  @Override
  public void touchToRepository() {
    final EntityManager manager = getEntityManager();
    final Query query = manager.createNativeQuery(sql);
    query.getSingleResult();
  }

  public EntityManager getEntityManager() {
    return factory.createEntityManager();
  }
}
