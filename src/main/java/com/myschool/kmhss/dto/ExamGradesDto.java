package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExamGradesDto {

    public Long id;
    public Long examId;
    public Long schoolGradeId;
    public Long subjectId;
    public Date examDate;
    public Long maxMark;
    public String timeFrom;
    public String timeTo;
    public String examName;
    public String gradeName;
    public String sectionName;
    public String subjectName;
}
