package com.allyssonmast.hamburgueria.repository.audit;

import com.allyssonmast.hamburgueria.model.audit.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> { }
