package com.github.krzysiek199720.codeclass.course.search;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.course.course.CourseComplexity;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupDAO;
import com.github.krzysiek199720.codeclass.course.search.dto.SearchDTO;
import com.github.krzysiek199720.codeclass.course.search.response.SearchResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final CourseGroupDAO courseGroupDAO;

    public SearchService(CourseGroupDAO courseGroupDAO){
        this.courseGroupDAO = courseGroupDAO;
    }

    @Transactional
    public List<SearchResponse> search(String searchQuery, List<CourseComplexity> complexities, User user) {
        List<SearchDTO> searchResponseList = courseGroupDAO.search(searchQuery, complexities, user == null ? null : user.getId());

        return searchResponseList.stream().map(SearchDTO::toResponse).collect(Collectors.toList());
    }
}
