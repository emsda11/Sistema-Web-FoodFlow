package com.allyssonmast.hamburgueria.service;

import com.allyssonmast.hamburgueria.dto.AuditLogResponseDTO;
import java.util.List;

public interface AuditService {
    List<AuditLogResponseDTO> listar();
    void registrar(String entidade, Long recursoId, String acao);
}
