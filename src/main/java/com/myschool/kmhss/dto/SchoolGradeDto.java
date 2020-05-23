package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SchoolGradeDto {

    private Long id;
    private Long schoolId;
    private Long gradeId;
    private Long teacherId;
    private Long academicYearId;
    private String sectionName;

    private String gradeName;
    private String teacherName;
    private String academicYear;
}
