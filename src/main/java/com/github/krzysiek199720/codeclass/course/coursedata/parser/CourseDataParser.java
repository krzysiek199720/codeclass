package com.github.krzysiek199720.codeclass.course.coursedata.parser;

import com.github.krzysiek199720.codeclass.course.coursedata.CourseData;
import com.github.krzysiek199720.codeclass.course.coursedata.CourseDataElement;
import com.github.krzysiek199720.codeclass.course.coursedata.CourseDataLine;
import com.github.krzysiek199720.codeclass.course.coursedata.CourseDataType;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("prototype")
public class CourseDataParser {

//    FIXME doesnt add last character in string section

    private CourseDataCharBuffer data = null;

    public int getDataPosition(){return data.getPosition();}

    private IndexBuffer indexBuffer;

    @Getter
    private int resultIndex = 0;

    public int getResultIndexPosition(){
        if(indexBuffer==null)
            return -1;
        if(indexBuffer.indexes.size() < resultIndex)
            return indexBuffer.indexes.get(resultIndex).position;
        return -1;
    }

    @Getter
    private ParserState state;

    @Getter
    private ParserResultState resultState;

    private List<CourseData> finalResult = null;

    private boolean hasChanged = true;

    public void tokenize(String data){
        this.data = new CourseDataCharBuffer(data);
        hasChanged = true;
        this.tokenize();
    }

    public void tokenize(){
        if(data == null)
            return;
        // we do have data

//        do we have new data
        if(!hasChanged)
            return;
        indexBuffer = new IndexBuffer();

        finalResult = null;

        state = null;

        while(data.hasNext()){
            if(state == ParserState.ERROR)
                break;

            char next = data.get();
            if(next == 0){
                state = ParserState.ERROR;
                break;
            }

            if (next == '<') {
                tokenizeTag();
            }
            else{
                tokenizeString();
            }

        }
        //parsing done

        if(state == ParserState.ERROR){
            hasChanged = true;
            return;
        }

        state = ParserState.SUCCESS;
        hasChanged = false;
    }

    private void tokenizeTag(){
        if(!data.hasNext()){
            state = ParserState.ERROR;
            return;
        }

        StringBuilder sb = new StringBuilder();
        int startingPosition = data.getPosition();
        boolean isEnding = false;

        char next = data.next();
        if(next == '/') {
            isEnding = true;
            next = data.next();
        }

        switch(next){
            case 'c':{
                sb.append('c');
                char c;
                while(data.hasNext()){
                    c = data.next();
                    if(c != '>')
                        sb.append(c);
                    else
                        break;
                }
                if(data.get() != '>'){
                    state = ParserState.ERROR;
                    break;
                }
                if(!sb.toString().equals("code")){
                    state = ParserState.ERROR;
                    break;
                }

                indexBuffer.addElement(startingPosition, data.getPosition()-startingPosition+1, isEnding ? ElementType.CODE_END : ElementType.CODE);

                break;
            }
            case 'l':{
                sb.append('l');
                char c;
                while(data.hasNext()){
                    c = data.next();
                    if(c != '>')
                        sb.append(c);
                    else
                        break;
                }
                if(data.get() != '>'){
                    state = ParserState.ERROR;
                    break;
                }

                // indent on line - thought to use \t but i think its prone to user errors, so i think its better to manually indent lines
                if(isEnding){
                    if(!sb.toString().equals("line")){
                        state = ParserState.ERROR;
                        break;
                    }
                    indexBuffer.addElement(startingPosition, data.getPosition()-startingPosition+1, ElementType.LINE_END);
                }else{
                    if(!sb.toString().startsWith("line")){
                        state = ParserState.ERROR;
                        break;
                    }
                    // check if there is a description
                    int indIndex = sb.toString().indexOf("indent=");

                    indexBuffer.addElement(startingPosition, data.getPosition()-startingPosition+1, ElementType.LINE);
                    if(indIndex >= 0)
                        indexBuffer.addElement(startingPosition+indIndex+9, data.getPosition()-(startingPosition+indIndex+9)-1, ElementType.LINE_INDENT);

                }

                break;
            }
            case 'e':{
                sb.append('e');
                char c;
                while(data.hasNext()){
                    c = data.next();
                    if(c != '>')
                        sb.append(c);
                    else
                        break;
                }
                if(data.get() != '>'){
                    state = ParserState.ERROR;
                    break;
                }

                if(isEnding){
                    if(!sb.toString().equals("element")){
                        state = ParserState.ERROR;
                        break;
                    }
                    indexBuffer.addElement(startingPosition, data.getPosition()-startingPosition+1, ElementType.ELEMENT_END);
                }else{
                    if(!sb.toString().startsWith("element")){
                        state = ParserState.ERROR;
                        break;
                    }
                    // check if there is a description
                    int descIndex = sb.toString().indexOf("desc=");

                    //no description
                    indexBuffer.addElement(startingPosition, data.getPosition()-startingPosition+1, ElementType.ELEMENT);
                    if(descIndex >= 0)
                        indexBuffer.addElement(startingPosition+descIndex+7, data.getPosition()-(startingPosition+descIndex+8), ElementType.ELEMENT_DESCRIPTION);

                }
                break;
            }
            case 'i': {
                sb.append('i');
                char c;
                while(data.hasNext()){
                    c = data.next();
                    if(c != '>')
                        sb.append(c);
                    else
                        break;
                }
                if(data.get() != '>'){
                    state = ParserState.ERROR;
                    break;
                }
                if(!sb.toString().startsWith("image")){
                    state = ParserState.ERROR;
                    break;
                }
                //add image index
                indexBuffer.addElement(startingPosition, data.getPosition()-startingPosition+1, ElementType.IMAGE);

                //find image localId
                int idIndex = sb.toString().indexOf("id=");

                if(idIndex < 0){
                    state = ParserState.ERROR;
                    break;
                }

                String idString = sb.toString().substring(idIndex+4); // without "
                int idIndexEnd = idString.indexOf("\"");

                indexBuffer.addElement(startingPosition+idIndex+4+1, data.getPosition() - (startingPosition+idIndex+4+idIndexEnd-1), ElementType.IMAGE_ID);

                break;
            }
            default: state = ParserState.ERROR;
        }

        if(state != ParserState.ERROR)
            data.next();

    }

    private void tokenizeString(){
        int startingPosition = data.getPosition();

        StringBuilder sb = new StringBuilder();
        if(data.get() != '\n')
            sb.append(data.get());
        else
            startingPosition += 1;

        char c;
        while(data.hasNext()){
            c = data.next();
            if(c == '<'){
                // if \ before < then it is not a tag
                if(data.getData()[(data.getPosition()-1)] != '\\')
                    break;
            }
            sb.append(c);
        }

        if(sb.toString().trim().equals(""))
            return;

//        indexBuffer.addElement(startingPosition, data.getPosition()-startingPosition, ElementType.TEXT);
        indexBuffer.addElement(startingPosition, sb.length(), ElementType.TEXT);
    }

    public List<CourseData> parse(){
        if(data == null)
            return null;
        if(indexBuffer == null)
            return null;
        if(state != ParserState.SUCCESS)
            return null;

        if(finalResult == null){
            List<CourseData> results = new ArrayList<>();
            resultIndex = 0;
            resultState = ParserResultState.UNKNOWN;

            if(indexBuffer.indexes.size() < 1){
                resultState = ParserResultState.ERROR_NO_DATA;
                return null;
            }

            CourseData next;
            while(true){
                next = getNextCourseData();
                //if error in getting result
                if( !(resultState == ParserResultState.UNKNOWN))
                    return null;

                if(next == null)
                    break;
                next.setOrder(results.size());
                results.add(next);
            }
            finalResult = results;
            resultState = ParserResultState.SUCCESS;
        }

        return finalResult;
    }

//    Too messy, should rewrite
    private CourseData getNextCourseData(){
        CourseData result;

        if(resultIndex >= indexBuffer.indexes.size())
            return null;

        // check if code or text
        if(indexBuffer.indexes.get(resultIndex).type == ElementType.CODE){
            result = new CourseData();
            result.setType(CourseDataType.CODE);

            LinkedList<CourseDataLine> lines = new LinkedList<>();

            boolean isLineStarted = false;
            ++resultIndex;

            if(!(resultIndex < indexBuffer.indexes.size())){
                resultState = ParserResultState.ERROR_MISSING_CODE_END;
                return null;
            }

            for(;resultIndex < indexBuffer.indexes.size(); ++resultIndex){
                Index index = indexBuffer.indexes.get(resultIndex);

                if(index.type == ElementType.CODE){
                    resultState = ParserResultState.ERROR_UNEXPECTED_TAG;
                    return null;
                }
                if(index.type == ElementType.IMAGE){
                    resultState = ParserResultState.ERROR_UNEXPECTED_TAG;
                    return null;
                }

                if(isLineStarted){
                    if(index.type == ElementType.LINE){
                        resultState = ParserResultState.ERROR_UNEXPECTED_TAG;
                        return null;
                    }
                    if(index.type == ElementType.CODE_END){
                        resultState = ParserResultState.ERROR_MISSING_LINE_END;
                        return null;
                    }

//                     line elements loop
                    LinkedList<CourseDataElement> elements = new LinkedList<>();
                    CourseDataLine line = lines.getLast();
                    int depth = 0;
                    Map<Integer, Boolean> isElementOpened = new HashMap<>();
                    Map<Integer, Boolean> hasData = new HashMap<>();
                    isElementOpened.put(0, false);
                    hasData.put(0, false);
                    while(true){
                        Index indexElement;
                        try{
                            indexElement = indexBuffer.indexes.get(resultIndex);
                        }catch (IndexOutOfBoundsException exception){
                            if(depth > 0)
                                resultState = ParserResultState.ERROR_MISSING_ELEMENT_END;
                            else
                                resultState = ParserResultState.ERROR_MISSING_LINE_END;

                            return null;
                        }
                        CourseDataElement element;

                        if(indexElement.type == ElementType.ELEMENT){
//                                start new element
//                                check for description

                            element = new CourseDataElement();
                            element.setCourseDataLine(line);
                            ++depth;
                            element.setDepth(depth);
                            element.setOrder(elements.size());

                            isElementOpened.put(depth, true);
                            hasData.put(depth, false);


//                                check for description
                            Index nextIndex;
                            try{
                                nextIndex = indexBuffer.indexes.get(resultIndex+1);
                            }catch (IndexOutOfBoundsException exception){
                                resultState = ParserResultState.ERROR_MISSING_ELEMENT_END;

                                return null;
                            }

                            if(nextIndex.type == ElementType.ELEMENT_DESCRIPTION){
                                ++resultIndex;
//                                desc
                                if(nextIndex.length != 0){
                                    String descData = new String(data.getData(), nextIndex.position, nextIndex.length).replace("\\<", "<");
                                    element.setDescription(descData);
                                } else{
                                    element.setDescription(null);
                                }
                                try{
                                    nextIndex = indexBuffer.indexes.get(resultIndex+1);
                                }catch (IndexOutOfBoundsException exception){
                                    resultState = ParserResultState.ERROR_MISSING_ELEMENT_DATA;

                                    return null;
                                }
                            } else{
                                if(index.type == ElementType.IMAGE){
                                    resultState = ParserResultState.ERROR_UNEXPECTED_TAG;
                                    return null;
                                }
                                element.setDescription(null);
                            }

                            if(nextIndex.type == ElementType.TEXT){
                                hasData.put(depth,true);
                                ++resultIndex;
//                                element data
                                String elemData = new String(data.getData(), nextIndex.position, nextIndex.length).replace("\\<", "<");
                                element.setData(elemData);
                            }else if(nextIndex.type == ElementType.LINE_END) {
                                resultState = ParserResultState.ERROR_MISSING_ELEMENT_END;
                                return null;
                            }else{
                                element.setData(null);
                            }

                        }else if(indexElement.type == ElementType.ELEMENT_END){
                            if(!hasData.get(depth)){
                                resultState = ParserResultState.ERROR_MISSING_ELEMENT_DATA;
                                return null;
                            }

//                                end element
                            isElementOpened.put(depth,false);
                            --depth;
                            ++resultIndex;
                            continue;
                        } else if(indexElement.type == ElementType.TEXT){
                            hasData.put(depth,true);

                            String elemData = new String(data.getData(), indexElement.position, indexElement.length).replace("\\<", "<");

                            element = new CourseDataElement();
                            element.setCourseDataLine(line);
                            element.setDepth(depth);
                            element.setDescription(null);
                            element.setOrder(elements.size());
                            element.setData(elemData);

                        } else if(indexElement.type == ElementType.LINE_END){
                            //end line
                            if(isElementOpened.get(depth)){
                                resultState = ParserResultState.ERROR_MISSING_ELEMENT_END;
                                return null;
                            }
                            isLineStarted = false;
                            break;
                        }else{
                            resultState = ParserResultState.ERROR_UNEXPECTED_TAG;
                            return null;
                        }



                        ++resultIndex;
                        elements.add(element);
                    }
                    line.getCourseDataElementList().addAll(elements);


                }else{ // isLineStarted == false
                    if(index.type == ElementType.LINE_END){
                        resultState = ParserResultState.ERROR_UNEXPECTED_TAG;
                        return null;
                    }
                    if(index.type == ElementType.ELEMENT
                            || index.type == ElementType.ELEMENT_DESCRIPTION
                            || index.type == ElementType.ELEMENT_END)
                    {
                        resultState = ParserResultState.ERROR_UNEXPECTED_TAG;
                        return null;
                    }

                    if(index.type == ElementType.CODE_END){
                        result.getCourseDataLineList().addAll(lines);
                        ++resultIndex;
                        return result;
                    }

                    if(index.type == ElementType.LINE){
                        //make new line
                        CourseDataLine line = new CourseDataLine();
                        line.setCourseData(result);
                        line.setOrder(lines.size());
                        line.setIndent(0);

                        // check for indent
                        try{
                            if(indexBuffer.indexes.get(resultIndex+1).type == ElementType.LINE_INDENT){
                                ++resultIndex;
                                if(indexBuffer.indexes.get(resultIndex).length != 0){
                                    Index indexIndent = indexBuffer.indexes.get(resultIndex);
                                    Integer indent = Integer.parseInt(
                                            new String(data.getData(), indexIndent.position, indexIndent.length)
                                    );
                                    line.setIndent(indent);
                                }
                            }
                            if(index.type == ElementType.IMAGE){
                                resultState = ParserResultState.ERROR_UNEXPECTED_TAG;
                                return null;
                            }
                        }catch (IndexOutOfBoundsException exception){
                            resultState = ParserResultState.ERROR_MISSING_LINE_END;
                            return null;
                        }catch (NumberFormatException exception){
                            resultState = ParserResultState.ERROR_INDENT;
                            return null;
                        }

                        //add to lines list
                        lines.add(line);
                        isLineStarted = true;
                     }
                }


            }
        } else if(indexBuffer.indexes.get(resultIndex).type == ElementType.TEXT){
            //make a text CourseData
            result = new CourseData();
            result.setType(CourseDataType.TEXT);
            Index index = indexBuffer.indexes.get(resultIndex);
            CourseDataLine line = new CourseDataLine();

            String elemData = new String(data.getData(), index.position, index.length).replace("\\<", "<");

            CourseDataElement element = new CourseDataElement(
                    0,
                    0,
                    elemData,
                    null,
                    line
            );
            line.getCourseDataElementList().add(element);
            line.setOrder(0);
            line.setIndent(0);
            line.setCourseData(result);

            result.getCourseDataLineList().add(line);
            ++resultIndex;
        }else if(indexBuffer.indexes.get(resultIndex).type == ElementType.IMAGE){
            result = new CourseData();
            result.setType(CourseDataType.IMG);
            Index index = indexBuffer.indexes.get(resultIndex);

            CourseDataLine line = new CourseDataLine();

            String imageLocalId = "";

            try{
                if(indexBuffer.indexes.get(resultIndex+1).type == ElementType.IMAGE_ID){
                    ++resultIndex;
                    if(indexBuffer.indexes.get(resultIndex).length != 0){
                        Index indexId = indexBuffer.indexes.get(resultIndex);
                        imageLocalId = new String(data.getData(), indexId.position, indexId.length);
                    }
                }
                else{
                    resultState = ParserResultState.ERROR_MISSING_IMG_ID;
                    return null;
                }
            }catch (IndexOutOfBoundsException exception){
                resultState = ParserResultState.ERROR_MISSING_IMG_ID;
                return null;
            }

            CourseDataElement element = new CourseDataElement(
                    0,
                    0,
                    imageLocalId,
                    null,
                    line
            );
            line.getCourseDataElementList().add(element);
            line.setOrder(0);
            line.setIndent(0);
            line.setCourseData(result);

            result.getCourseDataLineList().add(line);
            ++resultIndex;

            return result;
        }else{
            resultState = ParserResultState.ERROR_UNEXPECTED_TAG;
            return null;
        }

        return result;
    }

}
