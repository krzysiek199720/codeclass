package com.github.krzysiek199720.codeclass.course.category;

import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.course.category.api.CategorySaveApi;
import com.github.krzysiek199720.codeclass.course.language.api.LanguageSaveApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryDAO categoryDAO;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Transactional
    public Category getById(Long id) {
        Category language = categoryDAO.getById(id);
        if(language == null)
            throw new NotFoundException("course.category.notfound");

        return language;
    }

    @Transactional
    public List<Category> getAll() {
        return categoryDAO.getAll();
    }

    @Transactional
    public Category createCategory(CategorySaveApi api){
        Category category = new Category();
        category.setId(null);
        category.setName(api.getName());

        categoryDAO.save(category);
        return category;
    }
    @Transactional
    public Category updateCategory(Long id, CategorySaveApi api){
        Category category = categoryDAO.getById(id);
        if(category==null)
            throw new NotFoundException("course.category.notfound");

        category.setName(api.getName());

        categoryDAO.save(category);
        return category;
    }

    @Transactional
    public void deleteCategory(Long id){
        Category category = categoryDAO.getById(id);
        if(category==null)
            throw new NotFoundException("course.category.notfound");

        categoryDAO.delete(category);
    }
}
