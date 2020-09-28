package com.github.krzysiek199720.codeclass.course.file;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseDAO;
import com.github.krzysiek199720.codeclass.course.file.exception.exception.CourseFileEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final CourseDAO courseDAO;
    private final FileDAO fileDAO;

    @Value("${codeclass.course.filedir:./course/files}")
    private String filePath;

    @Autowired
    public FileService(CourseDAO courseDAO, FileDAO fileDAO) {
        this.courseDAO = courseDAO;
        this.fileDAO = fileDAO;
    }

    @Transactional
    public File saveFile(Long courseId, String displayName, MultipartFile multipartFile) throws IOException {
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        if(multipartFile.isEmpty())
            throw new CourseFileEmptyException();

        String fileName = multipartFile.getOriginalFilename() == null ? String.valueOf(multipartFile.hashCode()) : multipartFile.getOriginalFilename();
        String folder = filePath + "/" + String.format("%019d", courseId);

        byte[] bytes = multipartFile.getBytes();

        java.io.File folderFile = new java.io.File(folder);
        folderFile.mkdirs();
        java.io.File sourceFile = new java.io.File(folder, fileName);
        sourceFile.createNewFile(); // does not create if file exists
        FileOutputStream fos = new FileOutputStream(sourceFile);
        fos.write(bytes);
        fos.close();

        File file = new File();
        file.setId(null);
        file.setDisplay(displayName);
        file.setPath(fileName);
        file.setCourse(course);
        fileDAO.save(file);
        return file;
    }

    @Transactional
    public List<File> getAllByCourse(Long courseId) {
        return fileDAO.getAddByCourse(courseId);
    }

    public User getUserByFile(Long id) {
        return fileDAO.getUserByFile(id);
    }

    @Transactional
    public void deleteFile(Long id) {
        File file = fileDAO.getById(id);
        if(file == null)
            throw new NotFoundException("course.file.notfound");

        fileDAO.delete(file);
    }
}
