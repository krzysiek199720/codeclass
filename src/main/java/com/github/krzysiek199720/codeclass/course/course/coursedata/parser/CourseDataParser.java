package com.github.krzysiek199720.codeclass.course.course.coursedata.parser;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CourseDataParser {

    private CourseDataCharBuffer data = null;

    private IndexBuffer indexBuffer;

    @Getter
    private ParserState state;

    public void parse(String data){
        this.data = new CourseDataCharBuffer(data);
        this.parse();
    }

    public void parse(){
        if(data == null)
            return;
        // we do have data
        indexBuffer = new IndexBuffer();

        //TODO reset results

        state = ParserState.UNKNOWN;

        while(data.hasNext()){
            if(state == ParserState.ERROR)
                break;

            char next = data.get();
            if(next == 0){
                state = ParserState.ERROR;
                break;
            }

            if (next == '<') {
                parseTag();
            }
            else{
                parseString();
            }

        }
        //parsing done

        // TODO check state
//            if error save current position
        state = ParserState.SUCCESS;

    }

    private void parseTag(){
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
                if(!sb.toString().equals("line")){
                    state = ParserState.ERROR;
                    break;
                }

                indexBuffer.addElement(startingPosition, data.getPosition()-startingPosition+1, isEnding ? ElementType.LINE_END : ElementType.LINE);

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
                    if(descIndex < 0)
                        indexBuffer.addElement(startingPosition, data.getPosition()-startingPosition+1, ElementType.ELEMENT);
                    else{
                        indexBuffer.addElement(startingPosition, startingPosition+descIndex-1, ElementType.ELEMENT);
                        indexBuffer.addElement(startingPosition+descIndex+5, data.getPosition()-startingPosition-1, ElementType.ELEMENT_DESCRIPTION);
                    }

                }
                break;
            }
            default: state = ParserState.ERROR;
        }

    }

    private void parseString(){
        int startingPosition = data.getPosition();

        char c;
        while(data.hasNext()){
            c = data.next();
            if(c == '<')
                break;
        }
        indexBuffer.addElement(startingPosition, data.getPosition()-startingPosition, ElementType.TEXT);
    }


}
