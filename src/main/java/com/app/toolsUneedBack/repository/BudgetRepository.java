package com.app.toolsUneedBack.repository;

import com.app.toolsUneedBack.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity,Long> {

    List<BudgetEntity> findByCustomer_Id( Long customerId);
}
