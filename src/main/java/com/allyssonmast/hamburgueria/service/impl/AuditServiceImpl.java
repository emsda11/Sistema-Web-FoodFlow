package com.allyssonmast.hamburgueria.service.impl;

import com.allyssonmast.hamburgueria.dto.AuditLogResponseDTO;
import com.allyssonmast.hamburgueria.model.audit.AuditLog;
import com.allyssonmast.hamburgueria.repository.audit.AuditLogRepository;
import com.allyssonmast.hamburgueria.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditLogRepository repository;

    @Override
    public List<AuditLogResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public void registrar(String entidade, Long recursoId, String acao) {

        AuditLog log = new AuditLog();

        log.setEntidade(entidade);
        log.setRecursoId(recursoId);
        log.setAcao(acao);
        log.setDataHora(LocalDateTime.now());

        repository.save(log);
    }

    private AuditLogResponseDTO toDTO(AuditLog log) {

        AuditLogResponseDTO dto = new AuditLogResponseDTO();

        dto.setId(log.getId());
        dto.setAcao(log.getAcao());
        dto.setEntidade(log.getEntidade());
        dto.setRecursoId(log.getRecursoId());
        dto.setDataHora(log.getDataHora());

        return dto;
    }
}
