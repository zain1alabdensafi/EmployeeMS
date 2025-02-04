package com.example.employeemanagementsys.Repository;


import com.example.employeemanagementsys.Tables.EmployeeProject;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
}