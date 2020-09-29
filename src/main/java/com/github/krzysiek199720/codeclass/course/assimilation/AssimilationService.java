package com.github.krzysiek199720.codeclass.course.assimilation;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseDAO;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssimilationService {

    private final CourseDAO courseDAO;
    private final CourseGroupDAO courseGroupDAO;
    private final AssimilationDAO assimilationDAO;

    @Autowired
    public AssimilationService(CourseDAO courseDAO, CourseGroupDAO courseGroupDAO, AssimilationDAO assimilationDAO) {
        this.courseDAO = courseDAO;
        this.courseGroupDAO = courseGroupDAO;
        this.assimilationDAO = assimilationDAO;
    }

    @Transactional
    public Assimilation updateAssimilation(Long courseId, AssimilationValue value, User user){
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        User author = courseGroupDAO.getUserByCourseId(course.getId());
        if(!author.equals(user))
            if(course.getIsPublished() == null)
                throw new UnauthorizedException("course.assimilation.unauthorized");

        Assimilation assimilation = assimilationDAO.getByCourseUser(course.getId(), user.getId());
        if(assimilation == null){
            assimilation = new Assimilation();
            assimilation.setId(null);
            assimilation.setCourse(course);
            assimilation.setUser(user);
        }

        assimilation.setValue(value);

        assimilationDAO.save(assimilation);
        return assimilation;
    }

    public Assimilation getAssimilation(Long courseId, User user) {
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        User author = courseGroupDAO.getUserByCourseId(course.getId());
        if(!author.equals(user))
            if(course.getIsPublished() == null)
                throw new UnauthorizedException("course.assimilation.unauthorized");

        return assimilationDAO.getByCourseUser(course.getId(), user.getId());
    }
}
