package com.github.krzysiek199720.codeclass.course.language;

import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.course.language.api.LanguageSaveApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageDAO languageDAO;

    @Autowired
    public LanguageService(LanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
    }

    @Transactional
    public Language getById(Long id) {
        Language language = languageDAO.getById(id);
        if(language == null)
            throw new NotFoundException("course.language.notfound");

        return language;
    }

    @Transactional
    public List<Language> getAll() {
        return languageDAO.getAll();
    }

    @Transactional
    public Language createLanguage(LanguageSaveApi api){
        Language language = new Language();
        language.setId(null);
        language.setName(api.getName());

        languageDAO.save(language);
        return language;
    }
    @Transactional
    public Language updateLanguage(Long id, LanguageSaveApi api){
        Language language = languageDAO.getById(id);
        if(language==null)
            throw new NotFoundException("course.language.notfound");

        language.setName(api.getName());

        languageDAO.save(language);
        return language;
    }

    @Transactional
    public void deleteLanguage(Long id){
        Language language = languageDAO.getById(id);
        if(language==null)
            throw new NotFoundException("course.language.notfound");

        languageDAO.delete(language);
    }
}
