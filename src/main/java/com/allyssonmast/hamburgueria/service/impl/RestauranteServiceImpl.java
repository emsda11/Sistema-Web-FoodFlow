package com.allyssonmast.hamburgueria.service.impl;

import com.allyssonmast.hamburgueria.dto.RestauranteRequestDTO;
import com.allyssonmast.hamburgueria.dto.RestauranteResponseDTO;
import com.allyssonmast.hamburgueria.exception.NotFoundException;
import com.allyssonmast.hamburgueria.model.Restaurante;
import com.allyssonmast.hamburgueria.repository.primary.RestauranteRepository;
import com.allyssonmast.hamburgueria.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository repository;

    @Override
    public List<RestauranteResponseDTO> listar() {

        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public List<RestauranteResponseDTO>
    listarAtivos() {

        return repository.findByAtivoTrue()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public RestauranteResponseDTO buscarPorId(Long id) {

        Restaurante restaurante = buscarEntidade(id);

        return toDTO(restaurante);
    }

    @Override
    public RestauranteResponseDTO atualizar(Long id, RestauranteRequestDTO dto) {

        Restaurante restaurante = buscarEntidade(id);

        restaurante.setNome(dto.getNome());

        restaurante.setDescricao(dto.getDescricao());

        restaurante.setEndereco(dto.getEndereco());

        Restaurante atualizado = repository.save(restaurante);

        return toDTO(atualizado);
    }

    @Override
    public List<RestauranteResponseDTO> buscarPorNome(String nome) {

        return repository
                .findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private Restaurante buscarEntidade(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Restaurante não encontrado"));
    }

    private RestauranteResponseDTO toDTO(Restaurante restaurante) {

        RestauranteResponseDTO dto = new RestauranteResponseDTO();

        dto.setId(restaurante.getId());

        dto.setNome(restaurante.getNome());

        dto.setDescricao(restaurante.getDescricao());

        dto.setEndereco(restaurante.getEndereco());

        dto.setAtivo(restaurante.getAtivo());

        return dto;
    }
}