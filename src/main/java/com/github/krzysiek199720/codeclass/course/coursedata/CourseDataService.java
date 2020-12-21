package com.github.krzysiek199720.codeclass.course.coursedata;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.InvalidInputException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseDAO;
import com.github.krzysiek199720.codeclass.course.coursedata.parser.CourseDataParser;
import com.github.krzysiek199720.codeclass.course.coursedata.parser.ParserResultState;
import com.github.krzysiek199720.codeclass.course.coursedata.parser.ParserState;
import com.github.krzysiek199720.codeclass.course.coursedata.parser.exception.exception.CourseDataParserParseErrorException;
import com.github.krzysiek199720.codeclass.course.coursedata.parser.exception.exception.CourseDataParserTokenizerErrorException;
import com.github.krzysiek199720.codeclass.course.coursedata.response.CourseDataResponse;
import com.github.krzysiek199720.codeclass.course.coursedata.response.DataImageResponse;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupDAO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CourseDataService {

    private final ObjectFactory<CourseDataParser> factory;

    private final CourseDataDAO courseDataDAO;
    private final CourseDAO courseDAO;
    private final CourseGroupDAO courseGroupDAO;
    private final DataImageDAO dataImageDAO;

    @Value("${codeclass.course.sourcedir:./course/sources}")
    private String sourceDirectory;
    @Value("${codeclass.course.imagedir:./course/images}")
    private String imageDirectory;

    @Autowired
    public CourseDataService(ObjectFactory<CourseDataParser> factory, CourseDataDAO courseDataDAO, CourseDAO courseDAO, CourseGroupDAO courseGroupDAO, DataImageDAO dataImageDAO){
        this.factory = factory;
        this.courseDataDAO = courseDataDAO;
        this.courseDAO = courseDAO;
        this.courseGroupDAO = courseGroupDAO;
        this.dataImageDAO = dataImageDAO;
    }

    public List<CourseData> parseCourseData(String input){
        CourseDataParser parser = factory.getObject();
        if(input == null)
            input = "";

        parser.tokenize(input);

        if(parser.getState() == ParserState.ERROR){
            throw new CourseDataParserTokenizerErrorException(parser.getState(), parser.getDataPosition());
        }

        List<CourseData> result = parser.parse();

        if(parser.getResultState() != ParserResultState.SUCCESS || result == null){
            throw new CourseDataParserParseErrorException(
                    parser.getResultState(),
                    parser.getResultIndexPosition());
        }

        if(result.size() < 1){
            throw new CourseDataParserParseErrorException(
                    ParserResultState.ERROR_NO_DATA,
                    0);
        }

        return result;
    }

    @Transactional
    public List<CourseData> saveCourseData(Long courseId, String input){
        List<CourseData> result = parseCourseData(input);

        Course course = courseDAO.getById(courseId);

        if(course == null)
            throw new NotFoundException("course.notfound");

        result.forEach(e -> e.setCourse(course));

        String fileName = String.format("%019d", courseId) + ".course";

        if(course.getSourcePath() == null ||
                course.getSourcePath().isBlank()){
            course.setSourcePath(fileName);
        }

        File dir = new File(sourceDirectory);
        File sourceFile = new File(sourceDirectory, course.getSourcePath());
        FileWriter fw;
        try{
            dir.mkdirs();
            sourceFile.createNewFile(); // does not create if file exists
            fw = new FileWriter(sourceFile, false);
            fw.write(input);
            fw.close();
        }catch (IOException e){
            throw new RuntimeException("Could not save course source file");
        }
        courseDAO.save(course);

        courseDataDAO.deleteOld(course);

        result = courseDataDAO.saveAll(result);

        return result;
    }

    @Transactional
    public List<CourseDataResponse> getCourseData(Long courseId){
        List<CourseData> courseDataList = courseDataDAO.getByCourseId(courseId);

        List<CourseDataResponse> response = new ArrayList<>(courseDataList.size());
        for(CourseData cd : courseDataList){
            CourseDataResponse res = new CourseDataResponse();
            res.setId(cd.getId());
            res.setType(cd.getType());
            res.setOrder(cd.getOrder());
            res.setCourseDataLineList(cd.getCourseDataLineList());

            if(res.getType().equals(CourseDataType.CODE))
                res.setLinesPlain(courseDataDAO.getLines(res.getId()));
            else
                res.setLinesPlain(new ArrayList<>());
            response.add(res);
        }
        return response;
    }

    @Transactional
    public String getCourseDataRaw(Long courseId, User user){
        Course course = courseDAO.getById(courseId);

        if(course == null)
            throw new NotFoundException("course.notfound");

        User author = courseGroupDAO.getUserByCourseId(course.getId());
        if(!author.equals(user))
            throw new UnauthorizedException("course.unauthorized");

        File sourceFile = new File(sourceDirectory, course.getSourcePath());

        try{
            return new String( Files.readAllBytes(sourceFile.toPath()) );
        } catch(IOException ignored){
            throw new NotFoundException("course.data.raw.notfound");
        }
    }

    @Transactional
    public List<String> getCourseDataPlain(Long courseDataId, User user){
        Course course = courseDAO.getByCourseDataId(courseDataId);

        if(course == null)
            throw new NotFoundException("course.notfound");

        User author = courseGroupDAO.getUserByCourseId(course.getId());
        if(!author.equals(user))
            if(course.getIsPublished() == null)
                throw new UnauthorizedException("course.data.unauthorized");

        return courseDataDAO.getLines(courseDataId);
    }

    @Transactional
    public Image getImage(Long courseId, String localId) throws IOException{
        DataImage di = dataImageDAO.get(courseId, localId);
        if(di == null)
            throw new NotFoundException("course.data.image.notfound");

        File file = new File(getImageFolder(courseId), di.getPath());
        Image result = new Image();
        result.setData(IOUtils.toByteArray(file.toURI()));

        switch (di.getType()){
            case "jpg": result.setType(MediaType.IMAGE_JPEG); break;
            case "png": result.setType(MediaType.IMAGE_PNG); break;
            case "gif": result.setType(MediaType.IMAGE_GIF); break;
            default: result.setType(MediaType.APPLICATION_OCTET_STREAM);
        }

        return result;
    }

    @Transactional
    public String saveImage(Long courseId, String localId, MultipartFile multipartFile) throws IOException {
        DataImage di = dataImageDAO.get(courseId, localId);
        if(di != null)
            throw new InvalidInputException("course.data.image");
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        di = new DataImage();

        String type = multipartFile.getOriginalFilename() != null ? multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.')+1) : "";
        switch(type){
            case "jpg": di.setType("jpg"); break;
            case "png": di.setType("png"); break;
            case "gif": di.setType("gif"); break;
            default: di.setType("");
        }
        String fileName = multipartFile.getOriginalFilename() == null ? String.valueOf(multipartFile.hashCode()) : multipartFile.getOriginalFilename();
        String folder = getImageFolder(courseId);
        byte[] bytes = multipartFile.getBytes();

        File folderFile = new File(folder);
        folderFile.mkdirs();
        File imageFile = new File(folder, fileName);
        boolean isFileNew = imageFile.createNewFile(); // does not create if file exists

        if(!isFileNew){
            String[] fileNameParts = fileName.split("\\.");

            StringBuilder newFileName = new StringBuilder();
            if(fileNameParts.length > 1) {
                for (int i = 0; i < fileNameParts.length - 1; ++i) {
                    newFileName.append(fileNameParts[i]);
                }
                newFileName.append(new Random().nextLong());
                newFileName.append(".");
                newFileName.append(fileNameParts[fileNameParts.length - 1]);
            }
            else{
                newFileName.append(fileName);
                newFileName.append(new Random().nextLong());
            }

            fileName = newFileName.toString();
            imageFile = new File(folder, fileName);
            imageFile.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(imageFile);
        fos.write(bytes);
        fos.close();

        di.setCourse(course);
        di.setLocalId(localId);
        di.setPath(fileName);

        dataImageDAO.save(di);

        return di.getLocalId();
    }

    @Transactional
    public List<DataImageResponse> getImageList(Long courseId) {
        List<DataImage> resList = dataImageDAO.getAll(courseId);

        return resList.stream().map(item -> new DataImageResponse(item.getLocalId())).collect(Collectors.toList());
    }

    private String getImageFolder(Long courseId){
        return imageDirectory + "/" + String.format("%019d", courseId);
    }

    @Transactional
    public void deleteImage(Long courseId, String localId) {
        DataImage di = dataImageDAO.get(courseId, localId);
        if(di == null)
            throw new NotFoundException("course.data.image.notfound");
        dataImageDAO.delete(di);
    }
}
