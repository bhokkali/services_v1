package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.AcademicStudentsDao;
import com.myschool.kmhss.dao.StudentsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicStudentsRepository extends JpaRepository<AcademicStudentsDao, Long> {
    public List<AcademicStudentsDao> findBySchoolIdAndAcademicYearIdAndSchoolGradeId(
            Long schoolId,
            Long academicYearId,
            Long schoolGradeId);


    @Query( value = "select * from academic_students where school_id=?1 and student_id=?2 and promotion_status=?3 order by academic_student_id limit 1", nativeQuery = true)
    public AcademicStudentsDao findBySchoolIdAndStudentIdAndPromotionStatus(Long schoolId, Long studentId, String promotionStatus);

    public AcademicStudentsDao findBySchoolIdAndAcademicYearIdAndStudentIdAndPromotionStatus(
            Long schoolId,
            Long academicYearId,
            Long studentId,
            String promotionStatus);

    @Query( value = "select count(*) from academic_students where academic_year_id=?1 ", nativeQuery = true)
    public Long findAcademicStudentsCount(Long academicYearId);

    public List<AcademicStudentsDao> findBySchoolGradeId(Long schoolGradeId);
}
