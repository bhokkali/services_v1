package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AcademicStudentsDto {

    public String studentName;
    public String gradeName;
    public String promotionStatus;
    public Long promotionGradeId;
    public String academicYear;
    public Long id;
    public Long schoolId;
    public Long academicYearId;
    public Long schoolGradeId;
    public Long studentId;
    public String promotedGradeName;



}
