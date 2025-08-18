package com.app.toolsUneedBack.controller;

import com.app.toolsUneedBack.entity.SubCategoryEntity;
import com.app.toolsUneedBack.service.SubCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping (path = "subcategory")
public class SubCategoryController {

    private SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void newSubCategory(@RequestBody SubCategoryEntity subCategory){
        this.subCategoryService.newSubCategory(subCategory);
    }

    @GetMapping
    public List<SubCategoryEntity> findAllSubCategory(@RequestParam(required = false) Long categoryId){
        return this.subCategoryService.findAllSubCategories(categoryId);
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public SubCategoryEntity getSubCategoryById(@PathVariable Long id){
        return this.subCategoryService.findByid(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "{id}")
    public void deleteSubCategory(@PathVariable Long id){
        this.subCategoryService.deleteSubCategory(id);
    }

    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE)
    public void editSubCategory(@PathVariable Long id, @RequestBody SubCategoryEntity subCategory){
        this.subCategoryService.editSubCategory(id,subCategory);
    }
}
