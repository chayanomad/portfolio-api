package com.ibm.jp.ibmconsulting.icw.api.mapper;

import com.ibm.jp.ibmconsulting.icw.api.infrastructure.Stocks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksJpaRepository extends JpaRepository<Stocks, String> {}
