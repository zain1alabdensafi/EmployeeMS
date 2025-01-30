package com.example.employeemanagementsys.Repository;

import com.example.employeemanagementsys.Tables.Department;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

