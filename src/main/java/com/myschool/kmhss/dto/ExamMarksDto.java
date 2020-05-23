package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExamMarksDto {
    public Long id;
    public Long examGradeId;
    public Long studentId;
    public String attendedStatus;
    public Double markObtained;
    public Double markPercentage;
    public String markGrade;
    public String examName;
    public String gradeName;
    public String subjectName;
    public Long maxMark;
    public String studentName;
}
