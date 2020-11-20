package com.github.krzysiek199720.codeclass.course.quiz;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseDAO;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupDAO;
import com.github.krzysiek199720.codeclass.course.quiz.api.QuizQuestionApi;
import com.github.krzysiek199720.codeclass.course.quiz.api.QuizSaveApi;
import com.github.krzysiek199720.codeclass.course.quiz.dao.QuizDAO;
import com.github.krzysiek199720.codeclass.course.quiz.dao.QuizQuestionDAO;
import com.github.krzysiek199720.codeclass.course.quiz.dao.QuizScoreDAO;
import com.github.krzysiek199720.codeclass.course.quiz.response.QuizResponse;
import com.github.krzysiek199720.codeclass.course.quiz.response.QuizScoreResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final QuizDAO quizDAO;
    private final QuizQuestionDAO quizQuestionDAO;
    private final QuizScoreDAO quizScoreDAO;
    private final CourseDAO courseDAO;
    private final CourseGroupDAO courseGroupDAO;

    public QuizService(QuizDAO quizDAO, QuizQuestionDAO quizQuestionDAO, QuizScoreDAO quizScoreDAO, CourseDAO courseDAO, CourseGroupDAO courseGroupDAO) {
        this.quizDAO = quizDAO;
        this.quizQuestionDAO = quizQuestionDAO;
        this.quizScoreDAO = quizScoreDAO;
        this.courseDAO = courseDAO;
        this.courseGroupDAO = courseGroupDAO;
    }

    @Transactional
    public QuizResponse getQuiz(Long courseId, User user) {
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        User author = courseGroupDAO.getUserByCourseId(course.getId());

        if(!author.equals(user))
            if(course.getIsPublished() == null)
                throw new UnauthorizedException("course.quiz.unauthorized");

        Quiz quiz = quizDAO.getByCourseId(course.getId());

        if(quiz == null)
            throw new NotFoundException("course.quiz.notfound");

        List<QuizQuestion> questions = quizQuestionDAO.getAllByQuizId(quiz.getId());

        return new QuizResponse(quiz.getMaxScore(), questions);
    }

    @Transactional
    public QuizResponse saveQuiz(Long courseId, QuizSaveApi api){
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        Quiz old_quiz = quizDAO.getByCourseId(course.getId());
        if(old_quiz != null){
            quizDAO.delete(old_quiz);
        }

        Quiz quiz = new Quiz();
        quiz.setId(null);
        quiz.setCourse(course);
        quiz.setMaxScore(api.getQuestionApiList().size());

        quizDAO.save(quiz);

        List<QuizQuestion> questions = new ArrayList<>(api.getQuestionApiList().size());
        for(QuizQuestionApi questionApi : api.getQuestionApiList()){
            QuizQuestion qq = new QuizQuestion();
            qq.setQuestion(questionApi.getQuestion());
            qq.setAnswer0(questionApi.getAnswer0());
            qq.setAnswer1(questionApi.getAnswer1());
            qq.setAnswer2(questionApi.getAnswer2());
            qq.setAnswer3(questionApi.getAnswer3());
            qq.setAnswer(questionApi.getAnswer());
            qq.setId(null);
            qq.setQuiz(quiz);

//            FIXME change to some sort of batch save
            quizQuestionDAO.save(qq);
            questions.add(qq);
        }

        return new QuizResponse(quiz.getMaxScore(), questions);
    }

    @Transactional
    public void deleteQuiz(Long courseId){
        Quiz quiz = quizDAO.getByCourseId(courseId);
        if(quiz==null)
            throw new NotFoundException("course.quiz.notfound");
        quizDAO.delete(quiz);
    }

    @Transactional
    public void setScore(Long courseId, Integer score, User user) {
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        User author = courseGroupDAO.getUserByCourseId(course.getId());

        if(!author.equals(user))
            if(course.getIsPublished() == null)
                throw new UnauthorizedException("course.quiz.unauthorized");

        Quiz quiz = quizDAO.getByCourseId(course.getId());
        if(quiz == null)
            throw new NotFoundException("course.quiz.notfound");

        QuizScore quizScore = quizScoreDAO.getByQuizAndUserId(quiz.getId(), user.getId());
        if(quizScore == null){
            quizScore = new QuizScore();
            quizScore.setId(null);
            quizScore.setQuiz(quiz);
            quizScore.setUser(user);
        }

        quizScore.setScore(score);

        quizScoreDAO.save(quizScore);
    }

    public QuizScoreResponse getScore(Long courseId, User user) {
        return quizScoreDAO.getByCourseAndUserId(courseId, user.getId());
    }
}
