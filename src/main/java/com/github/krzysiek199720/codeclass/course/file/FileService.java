package com.github.krzysiek199720.codeclass.course.file;

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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

//        Path path = Paths.get(filePath + fileName);
//        Files.write(path, bytes);

        java.io.File folerFile = new java.io.File(folder);
        folerFile.mkdirs();
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

}
