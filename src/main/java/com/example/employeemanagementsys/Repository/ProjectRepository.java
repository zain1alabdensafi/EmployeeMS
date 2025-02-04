package com.example.employeemanagementsys.Repository;

import com.example.employeemanagementsys.Tables.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
