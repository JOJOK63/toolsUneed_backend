package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.SubCategoryEntity;

import java.util.List;

public interface SubCategoryService {
    void newSubCategory(SubCategoryEntity subCategory);
    List<SubCategoryEntity> findAllSubCategories(Long categoryId);
    SubCategoryEntity findByid(Long id);
    void deleteSubCategory(Long id);
    void editSubCategory(Long id, SubCategoryEntity subCategory);

}
