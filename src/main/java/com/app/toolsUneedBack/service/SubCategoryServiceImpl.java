package com.app.toolsUneedBack.service;

import com.app.toolsUneedBack.entity.CategoryEntity;
import com.app.toolsUneedBack.entity.SubCategoryEntity;
import com.app.toolsUneedBack.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryServiceImpl implements SubCategoryService{

    private SubCategoryRepository subCategoryRepository;
    private CategoryService categoryService;

    public SubCategoryServiceImpl(CategoryService categoryService, SubCategoryRepository subCategoryRepository) {
        this.categoryService = categoryService;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public void newSubCategory(SubCategoryEntity subCategory) {
        Long categoryId = subCategory.getCategory().getId();
        CategoryEntity categoryProxy = this.categoryService.getReferenceById(categoryId);
        subCategory.setCategory(categoryProxy);
        this.subCategoryRepository.save(subCategory);
    }

    @Override
    public List<SubCategoryEntity> findAllSubCategories(Long categoryId) {
        if(categoryId == null){
            return subCategoryRepository.findAll();
        }else{
            return subCategoryRepository.findByCategory_Id(categoryId);
        }
    }

    @Override
    public SubCategoryEntity findByid(Long id) {
        Optional<SubCategoryEntity> optionalSubCategory = this.subCategoryRepository.findById(id);
        return optionalSubCategory.orElse(null);
    }

    @Override
    public void deleteSubCategory(Long id) {
        this.subCategoryRepository.deleteById(id);
    }

    @Override
    public void editSubCategory(Long id, SubCategoryEntity subCategory) {
        SubCategoryEntity subCategoryFromBDD = this.findByid(id);

        if(subCategory.getId() == subCategoryFromBDD.getId()){
            subCategoryFromBDD.setName(subCategory.getName());
            subCategoryFromBDD.setIcon(subCategory.getIcon());
            subCategoryFromBDD.setIsActive(subCategory.getIsActive());
        }

        //management of category change
        if(subCategory.getCategory() !=null && subCategory.getCategory().getId() != null){
            Long categoryId = subCategory.getCategory().getId();
            CategoryEntity categoryProxy = categoryService.getReferenceById(categoryId);
            subCategoryFromBDD.setCategory(categoryProxy);
        }

        this.subCategoryRepository.save(subCategoryFromBDD);
    }

}
