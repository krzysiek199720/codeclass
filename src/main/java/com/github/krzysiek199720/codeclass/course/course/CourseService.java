package com.github.krzysiek199720.codeclass.course.course;

import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
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
    public Course publish(Long courseId, Boolean isPublished){
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        if(isPublished){
            if(course.getIsPublished().isBefore(LocalDateTime.now()))
                return course;
            course.setIsPublished(LocalDateTime.now());
        }else{
            course.setIsPublished(null);
        }

        courseDAO.save(course);

        return course;
    }
}
