package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExamReportDto {

    public Long id;
    public Long examId;
    public Long schoolGradeId;
    public Long studentId;
    public Double totalMarks;
    public Double totalMaxMarks;
    public Double overallPercentage;
    public String overallGrade;
    public String examName;
    public String gradeName;
    public String studentName;
}
