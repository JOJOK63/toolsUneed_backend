package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    void newCategory(CategoryEntity category);
    List<CategoryEntity> findAllCategories();
    CategoryEntity findById(Long id);
    void deleteCategory(Long id);
    void editCategory(Long id, CategoryEntity category);

    CategoryEntity getReferenceById(Long id);
}
