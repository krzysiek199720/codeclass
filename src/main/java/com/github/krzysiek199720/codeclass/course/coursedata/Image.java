package com.github.krzysiek199720.codeclass.course.coursedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Image {
    private MediaType type;
    private byte[] data;
}
