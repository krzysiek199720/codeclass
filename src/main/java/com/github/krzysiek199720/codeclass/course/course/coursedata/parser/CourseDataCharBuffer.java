package com.github.krzysiek199720.codeclass.course.course.coursedata.parser;

import lombok.Getter;

public class CourseDataCharBuffer {

    @Getter
    private char[] data = null;
    @Getter
    private int length = 0;

    @Getter
    private int position = 0;


    public CourseDataCharBuffer(String dataString) {
        this.data = dataString.toCharArray();
        this.length = data.length;
    }

    public void setData(String dataString){
        this.data = dataString.toCharArray();
        this.length = data.length;
    }

    public void setPosition(int pos){
        if(pos >= 0 && pos<this.length)
            this.position = pos;
    }

    public void addToPosition(int value) {
        int newPosition = this.position + value;
        if(newPosition >= 0 && newPosition<this.length)
            this.position = newPosition;
    }

    public boolean hasNext() {
        return (this.position+1) < this.length;
    }

    public char next(){
        ++position;
        return get();
    }

    // returns 0 if invalid
    public char get(){
        if(this.position >= 0 && this.position<this.length){
            return data[position];
        }
        return (char) 0;
    }

}
