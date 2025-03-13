package com.feedhanjum.core.repository;

import com.feedhanjum.core.domain.JobRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRecordRepository extends JpaRepository<JobRecord, JobRecord.JobName> {
}