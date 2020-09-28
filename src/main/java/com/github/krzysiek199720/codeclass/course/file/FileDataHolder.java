package com.github.krzysiek199720.codeclass.course.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FileDataHolder {
    private String name;
    private byte[] data;
}
