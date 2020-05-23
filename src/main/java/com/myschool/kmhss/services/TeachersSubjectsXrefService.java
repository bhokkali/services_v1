package com.myschool.kmhss.services;

import com.myschool.kmhss.repositories.TeachersSubjectsXrefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeachersSubjectsXrefService {

    @Autowired
    TeachersSubjectsXrefRepository teachersSubjectsXrefRepository;
}

