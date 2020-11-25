package com.github.krzysiek199720.codeclass.course.course;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.course.category.Category;
import com.github.krzysiek199720.codeclass.course.category.CategoryDAO;
import com.github.krzysiek199720.codeclass.course.course.api.CourseCreateApi;
import com.github.krzysiek199720.codeclass.course.course.api.CourseUpdateApi;
import com.github.krzysiek199720.codeclass.course.course.api.PublishApi;
import com.github.krzysiek199720.codeclass.course.course.response.CourseResponse;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroup;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupDAO;
import com.github.krzysiek199720.codeclass.course.follow.Follow;
import com.github.krzysiek199720.codeclass.course.follow.FollowDAO;
import com.github.krzysiek199720.codeclass.course.language.Language;
import com.github.krzysiek199720.codeclass.course.language.LanguageDAO;
import com.github.krzysiek199720.codeclass.notification.NotificationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CourseService {

    private final CourseDAO courseDAO;
    private final CourseGroupDAO courseGroupDAO;
    private final LanguageDAO languageDAO;
    private final CategoryDAO categoryDAO;
    private final FollowDAO followDAO;
    private final NotificationDAO notificationDAO;

    @Autowired
    public CourseService(CourseDAO courseDAO, CourseGroupDAO courseGroupDAO, LanguageDAO languageDAO, CategoryDAO categoryDAO, FollowDAO followDAO, NotificationDAO notificationDAO) {
        this.courseDAO = courseDAO;
        this.courseGroupDAO = courseGroupDAO;
        this.languageDAO = languageDAO;
        this.categoryDAO = categoryDAO;
        this.followDAO = followDAO;
        this.notificationDAO = notificationDAO;
    }

    @Transactional
    public CourseResponse getById(Long id, User user){
        Course course = courseDAO.fetchById(id);
        if(course == null)
            throw new NotFoundException("course.notfound");

        boolean isAuthor = user != null && courseGroupDAO.getUserByCourseId(course.getId()).equals(user);

        if(course.getIsPublished() == null)
            if(!isAuthor)
                throw new UnauthorizedException("course.unauthorized");

        Follow follow = null;
        if(user != null)
            follow = followDAO.getByCourseId(course.getId(), user.getId());

        return new CourseResponse(course, isAuthor, follow != null);
    }

    @Transactional
    public CourseResponse createCourse(CourseCreateApi api) {
        Course course = new Course();

        CourseGroup courseGroup = courseGroupDAO.getById(api.getCourseGroupId());
        if(courseGroup == null)
            throw new NotFoundException("course.coursegroup.notfound");

        Language language = languageDAO.getById(api.getLanguageId());
        if(language == null)
            throw new NotFoundException("course.language.notfound");

        Category category = categoryDAO.getById(api.getCategoryId());
        if(category == null)
            throw new NotFoundException("course.category.notfound");

        course.setCourseGroup(courseGroup);
        course.setLanguage(language);
        course.setCategory(category);


        course.setId(null);
        course.setTitle(api.getTitle());
        course.setComplexity(api.getComplexity());
        course.setGroupOrder(api.getGroupOrder());
        course.setIsPublished(null);

        courseDAO.save(course);

        return new CourseResponse(course, true, null);
    }

    @Transactional
    public CourseResponse updateCourse(Long id, CourseUpdateApi api) {
        Course course = courseDAO.fetchById(id);

        if(course == null)
            throw new NotFoundException("course.notfound");

        if(!course.getLanguage().getId().equals(api.getLanguageId())){
          Language language = languageDAO.getById(api.getLanguageId());
          if(language == null)
              throw new NotFoundException("course.language.notfound");
            course.setLanguage(language);
        }

        if(!course.getCategory().getId().equals(api.getCategoryId())) {
        Category category = categoryDAO.getById(api.getCategoryId());
        if(category == null)
            throw new NotFoundException("course.category.notfound");
            course.setCategory(category);
        }

        course.setTitle(api.getTitle());
        course.setComplexity(api.getComplexity());
        course.setGroupOrder(api.getGroupOrder());

        courseDAO.save(course);

        return new CourseResponse(course, true, null);
    }

    @Transactional
    public void delete(Long id){
        Course course = courseDAO.getById(id);
        if(course == null)
            throw new NotFoundException("user.notfound");
        courseDAO.delete(course);
    }

    @Transactional
    public LocalDateTime publish(Long courseId, User user, PublishApi api){
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        User author = courseGroupDAO.getUserByCourseId(courseId);

        if(!author.equals(user))
            throw new UnauthorizedException("course.unauthorized");

        if(api.getIsPublished()){
            if(course.getIsPublished() != null && course.getIsPublished().isBefore(LocalDateTime.now()))
                return course.getIsPublished();
            course.setIsPublished(LocalDateTime.now());
        }else{
            course.setIsPublished(null);
        }

        courseDAO.save(course);

        if(course.getIsPublished() != null){
            String textSb = "New course: " +
                    course.getTitle() +
                    " by " +
                    user.getFirstname() +
                    " " +
                    user.getLastname();
            notificationDAO.createNotification(
                    course.getId(),
                    textSb,
                    api.getSlug()
                    );
        }
        return course.getIsPublished();
    }

    @Transactional
    public boolean isPublished(Long id){
        Course course = courseDAO.getById(id);
        if(course == null)
            throw new NotFoundException("course.notfound");

        return course.getIsPublished() != null;
    }
}
