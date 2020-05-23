package com.myschool.kmhss.dto;

import lombok.Data;

import java.util.List;

@Data
public class ParentsPaginatedDto {

    private Long Count;
    private List<ParentsDto> parentsList;

}
