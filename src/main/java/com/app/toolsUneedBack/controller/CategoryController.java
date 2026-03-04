package com.app.toolsUneedBack.controller;

import com.app.toolsUneedBack.entity.CategoryEntity;
import com.app.toolsUneedBack.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> newCategory(@RequestBody CategoryEntity category) {
        try {
            categoryService.newCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> findAllCategory() {
        try {
            List<CategoryEntity> categories = categoryService.findAllCategories();
            return ResponseEntity.ok(categories); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Long id) {
        try {
            CategoryEntity category = categoryService.findById(id);
            if (category != null) {
                return ResponseEntity.ok(category); // 200 OK
            } else {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build(); // 204 No Content (suppression réussie)
        } catch (IllegalStateException e) {
            // Contrainte métier (sous-catégories liées)
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (DataIntegrityViolationException e) {
            // Contrainte base de données
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> editCategory(@PathVariable Long id, @RequestBody CategoryEntity category) {
        try {
            categoryService.editCategory(id, category);
            return ResponseEntity.noContent().build(); // 204 No Content (modification réussie)
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }


}
