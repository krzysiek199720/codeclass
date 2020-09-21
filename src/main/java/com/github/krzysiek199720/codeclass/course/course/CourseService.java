package com.github.krzysiek199720.codeclass.course.course;

import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.course.course.api.CourseCreateApi;
import com.github.krzysiek199720.codeclass.course.course.api.CourseUpdateApi;
import com.github.krzysiek199720.codeclass.course.course.coursegroup.CourseGroup;
import com.github.krzysiek199720.codeclass.course.course.coursegroup.CourseGroupDAO;
import com.github.krzysiek199720.codeclass.course.course.response.CourseResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CourseService {

    private final CourseDAO courseDAO;
    private final CourseGroupDAO courseGroupDAO;
//    private final LanguageDAO languageDAO;
//    private final CategoryDAO categoryDAO;

    public CourseService(CourseDAO courseDAO, CourseGroupDAO courseGroupDAO) {
        this.courseDAO = courseDAO;
        this.courseGroupDAO = courseGroupDAO;
    }

    @Transactional
    public CourseResponse getById(Long id, boolean isAuthor){
        Course course = courseDAO.getById(id);
        if(course == null)
            throw new NotFoundException("course.notfound");

        if(course.getIsPublished() == null)
            if(!isAuthor)
                throw new UnauthorizedException("course.unauthorized");

        return new CourseResponse(course, isAuthor);
    }

    @Transactional
    public CourseResponse createCourse(CourseCreateApi api) {
        Course course = new Course();

        CourseGroup courseGroup = courseGroupDAO.getById(api.getCourseGroupId());
        if(courseGroup == null)
            throw new NotFoundException("course.coursegroup.notfound");

//TODO uncomment

//        Language language = languageDAO.getById(api.getLanguageId());
//        if(language == null)
//            throw new NotFoundException("course.language.notfound");
//
//        Category category = categoryDAO.getById(api.getCategoryId());
//        if(category == null)
//            throw new NotFoundException("course.category.notfound");

        course.setCourseGroup(courseGroup);
//        course.setLanguage(language);
//        course.setCategory(category);


        course.setId(null);
        course.setTitle(api.getTitle());
        course.setComplexity(api.getComplexity());
        course.setGroupOrder(api.getGroupOrder());
        course.setIsPublished(null);

        courseDAO.save(course);

        return new CourseResponse(course, true);
    }

    @Transactional
    public CourseResponse updateCourse(Long id, CourseUpdateApi api) {
        Course course = courseDAO.fetchById(id);

        if(course == null)
            throw new NotFoundException("course.notfound");

//TODO uncomment

        if(!course.getLanguage().getId().equals(api.getLanguageId())){
//          Language language = languageDAO.getById(api.getLanguageId());
//          if(language == null)
//              throw new NotFoundException("course.language.notfound");
//            course.setLanguage(language);
        }

        if(!course.getCategory().getId().equals(api.getCategoryId())) {
//        Category category = categoryDAO.getById(api.getCategoryId());
//        if(category == null)
//            throw new NotFoundException("course.category.notfound");
//            course.setCategory(category);
        }

        course.setTitle(api.getTitle());
        course.setComplexity(api.getComplexity());
        course.setGroupOrder(api.getGroupOrder());

        courseDAO.save(course);

        return new CourseResponse(course, true);
    }

    @Transactional
    public void delete(Long id){
        Course course = courseDAO.getById(id);
        if(course == null)
            throw new NotFoundException("user.notfound");
        courseDAO.delete(course);
    }

    @Transactional
    public void publish(Long courseId, Boolean isPublished){
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        if(isPublished){
            if(course.getIsPublished().isBefore(LocalDateTime.now()))
                return;
            course.setIsPublished(LocalDateTime.now());
        }else{
            course.setIsPublished(null);
        }

        courseDAO.save(course);
    }
}
