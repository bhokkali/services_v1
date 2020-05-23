package com.myschool.kmhss.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="teacherssubjectsxref")
public class TeachersSubjectsXrefDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name="teacher_id")
    private Long teacherId;

    @Column(name="subject_id")
    private Long subjectId;

    @ManyToOne
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    private SubjectDao subjectInfo;

}
