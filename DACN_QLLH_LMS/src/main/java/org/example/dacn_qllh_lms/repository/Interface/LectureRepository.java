package org.example.dacn_qllh_lms.repository.Interface;

import org.example.dacn_qllh_lms.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByClassId(Long classId);
}

