package com.allyssonmast.hamburgueria.service.impl;

import com.allyssonmast.hamburgueria.dto.ClienteRequestDTO;
import com.allyssonmast.hamburgueria.dto.ClienteResponseDTO;
import com.allyssonmast.hamburgueria.exception.NotFoundException;
import com.allyssonmast.hamburgueria.model.Cliente;
import com.allyssonmast.hamburgueria.repository.primary.ClienteRepository;
import com.allyssonmast.hamburgueria.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public ClienteResponseDTO criar(ClienteRequestDTO clienteRequestDTO) {

        Cliente cliente = new Cliente();

        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setEmail(clienteRequestDTO.getEmail());

        Cliente salvo = repository.save(cliente);

        return toResponseDTO(salvo);
    }

    @Override
    public List<ClienteResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public ClienteResponseDTO buscarPorId(Long id) {

        Cliente cliente = buscarEntidadePorId(id);

        return toResponseDTO(cliente);
    }

    @Override
    public ClienteResponseDTO atualizar(
            Long id,
            ClienteRequestDTO dto
    ) {

        Cliente existente = buscarEntidadePorId(id);

        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());

        Cliente atualizado = repository.save(existente);

        return toResponseDTO(atualizado);
    }

    @Override
    public void deletar(Long id) {

        Cliente cliente = buscarEntidadePorId(id);

        repository.delete(cliente);
    }

    private Cliente buscarEntidadePorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Cliente não encontrado")
                );
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {

        ClienteResponseDTO dto = new ClienteResponseDTO();

        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());

        return dto;
    }
}