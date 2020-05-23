package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.SubjectDao;
import com.myschool.kmhss.dto.SubjectDto;
import com.myschool.kmhss.repositories.SubjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    public List<SubjectDto> listSubjects() {
        List<SubjectDao> subjectDaos = subjectRepository.findAll();
        return convertDaoToDto(subjectDaos);
    }

    public void createUpdateSubject(SubjectDao subjectDao) {
        if(subjectDao.getId() == null) {
            subjectDao.setCreatedDate(new Date());
        } else {
            subjectDao.setUpdatedDate(new Date());
        }
        subjectRepository.save(subjectDao);
    }

    public List<SubjectDto> listSchoolActiveSubjects(Long schoolId) {
        List<SubjectDao> subjectDaos = subjectRepository.findSchoolActiveSubjects(schoolId);
        return convertDaoToDto(subjectDaos);
    }

    private List<SubjectDto> convertDaoToDto(List<SubjectDao> subjectDaos) {
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for(SubjectDao subjectDao: subjectDaos) {
            SubjectDto dto = new SubjectDto();
            BeanUtils.copyProperties(subjectDao, dto);
            subjectDtos.add(dto);
        }
        return subjectDtos;
    }

}
