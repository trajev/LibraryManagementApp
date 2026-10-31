package com.example.librarymanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.librarymanagement.entity.IssueRecord;

@Repository
public interface IssueRecordRepository extends JpaRepository<IssueRecord, UUID> {

}
