package com.github.krzysiek199720.codeclass.course.course;

import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.course.course.category.Category;
import com.github.krzysiek199720.codeclass.course.course.coursegroup.CourseGroup;
import com.github.krzysiek199720.codeclass.course.course.language.Language;
import com.github.krzysiek199720.codeclass.course.course.response.CourseResponse;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CourseService {

    private final CourseDAO courseDAO;

    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
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
