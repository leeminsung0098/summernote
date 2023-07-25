package com.testapi.leem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DbRepository extends CrudRepository<Db, Integer> {
}
