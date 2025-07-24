package com.app.toolsUneedBack.controller;

import com.app.toolsUneedBack.entity.CategoryEntity;
import com.app.toolsUneedBack.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void newCategory(@RequestBody CategoryEntity category){
        categoryService.newCategory(category);
    }

    @GetMapping
    public List<CategoryEntity> findAllCategory(){
        return this.categoryService.findAllCategories();
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public CategoryEntity getCategoryById(@PathVariable Long id){
        return this.categoryService.findById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "{id}")
    public void deleteCategory(@PathVariable Long id){
        this.categoryService.deleteCategory(id);
    }

    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE)
    public void editCategory(@PathVariable Long id, @RequestBody CategoryEntity category){
        this.categoryService.editCategory(id,category);
    }


}
