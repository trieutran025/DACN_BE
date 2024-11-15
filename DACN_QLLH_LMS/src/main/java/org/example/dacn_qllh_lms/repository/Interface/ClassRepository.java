package org.example.dacn_qllh_lms.repository.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    List<Class> findByTeacherId(Long teacherId);
}
