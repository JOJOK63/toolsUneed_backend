package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.CategoryEntity;
import com.app.toolsUneedBack.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements  CategoryService{

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void newCategory(CategoryEntity category) {
        this.categoryRepository.save(category);
    }

    @Override
    public List<CategoryEntity> findAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public CategoryEntity findById(Long id) {
        Optional<CategoryEntity> optionalCategory = this.categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }

    @Override
    public void deleteCategory(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public void editCategory(Long id, CategoryEntity category) {
        CategoryEntity categoryFromBDD = this.findById(id);

        if(category.getId() == categoryFromBDD.getId()){
            categoryFromBDD.setName(category.getName());
            categoryFromBDD.setColor(category.getColor());
            categoryFromBDD.setIcon(category.getIcon());
            categoryFromBDD.setIsActive(category.getIsActive());
            this.categoryRepository.save(categoryFromBDD);
        }
    }

}
