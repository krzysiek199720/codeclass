package com.github.krzysiek199720.codeclass.course.link;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseDAO;
import com.github.krzysiek199720.codeclass.course.link.api.LinkSaveApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LinkService {

    private final LinkDAO linkDAO;
    private final CourseDAO courseDAO;

    @Autowired
    public LinkService(LinkDAO linkDAO, CourseDAO courseDAO) {
        this.linkDAO = linkDAO;
        this.courseDAO = courseDAO;
    }

    @Transactional
    public List<Link> getAllByCourse(Long courseId) {
        return linkDAO.getAllByCourse(courseId);
    }

    @Transactional
    public Link createLink(Long courseId, LinkSaveApi api){
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        Link link = new Link();
        link.setId(null);
        link.setDisplay(api.getDisplay());
        link.setLink(api.getLink());
        link.setCourse(course);

        linkDAO.save(link);
        return link;
    }

    @Transactional
    public Link updateLink(Long id, LinkSaveApi api){
        Link link = linkDAO.getById(id);
        if(link==null)
            throw new NotFoundException("course.link.notfound");

        link.setDisplay(api.getDisplay());
        link.setLink(api.getLink());

        linkDAO.save(link);
        return link;
    }

    @Transactional
    public void deleteLink(Long id){
        Link link = linkDAO.getById(id);
        if(link==null)
            throw new NotFoundException("course.link.notfound");

        linkDAO.delete(link);
    }

    public User getUserByLink(Long id) {
        return linkDAO.getUserByLinkId(id);
    }
}
