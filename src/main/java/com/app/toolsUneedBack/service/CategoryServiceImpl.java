package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.CategoryEntity;
import com.app.toolsUneedBack.entity.SubCategoryEntity;
import com.app.toolsUneedBack.repository.CategoryRepository;
import com.app.toolsUneedBack.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements  CategoryService{

    private CategoryRepository categoryRepository;
    private SubCategoryRepository subCategoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
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
    public void deleteCategory(Long categoryId) {
        // Vérifier s'il y a des sous-catégories liées
        List<SubCategoryEntity> subCategories = subCategoryRepository.findByCategory_Id(categoryId);

        if (!subCategories.isEmpty()) {
            throw new IllegalStateException("Impossible de supprimer une catégorie qui contient des sous-catégories");
        }

        categoryRepository.deleteById(categoryId);
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

    @Override
    public CategoryEntity getReferenceById(Long id){
        return categoryRepository.getReferenceById(id);
    }

}
