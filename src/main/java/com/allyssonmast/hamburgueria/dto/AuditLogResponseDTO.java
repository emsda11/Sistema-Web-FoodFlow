package com.allyssonmast.hamburgueria.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditLogResponseDTO {

    private Long id;
    private String acao;
    private String entidade;
    private Long recursoId;
    private LocalDateTime dataHora;
}
