package com.app.toolsUneedBack.repository;

import com.app.toolsUneedBack.entity.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity,Long> {

    List<SubCategoryEntity> findByCategory_Id(Long categoryId);
}
