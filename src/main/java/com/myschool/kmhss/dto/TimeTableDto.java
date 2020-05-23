package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeTableDto {
    private Long id;
    private Long schoolId;
    private Long academicYearId;
    private String academicYear;
    private Long schoolGradeId;
    private String gradeName;
    private Long periodId;
    private String periodName;
    private Long subjectId;
    private String subjectName;
    private Long teacherId;
    private String teacherName;
    private String weekday;
}
