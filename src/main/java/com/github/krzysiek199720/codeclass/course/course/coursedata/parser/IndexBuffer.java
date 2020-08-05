package com.github.krzysiek199720.codeclass.course.course.coursedata.parser;

import java.util.ArrayList;

public class IndexBuffer {

    public final ArrayList<Index> indexes;

    public IndexBuffer() {
        this.indexes = new ArrayList<>();
    }


    public void addElement(int position, int length, ElementType type){
        Index result = new Index(
                position,
                length,
                type
        );
        indexes.add(result);
    }
}
