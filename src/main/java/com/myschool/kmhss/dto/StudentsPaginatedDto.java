package com.myschool.kmhss.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentsPaginatedDto {

    private Long count;
    private List<StudentsDto> studentsList;

}

