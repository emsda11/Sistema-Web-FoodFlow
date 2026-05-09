package com.allyssonmast.hamburgueria.service.impl;

import com.allyssonmast.hamburgueria.dto.ProdutoRequestDTO;
import com.allyssonmast.hamburgueria.dto.ProdutoResponseDTO;
import com.allyssonmast.hamburgueria.exception.NotFoundException;
import com.allyssonmast.hamburgueria.model.Categoria;
import com.allyssonmast.hamburgueria.model.Produto;
import com.allyssonmast.hamburgueria.model.Restaurante;
import com.allyssonmast.hamburgueria.repository.primary.CategoriaRepository;
import com.allyssonmast.hamburgueria.repository.primary.ProdutoRepository;
import com.allyssonmast.hamburgueria.repository.primary.RestauranteRepository;
import com.allyssonmast.hamburgueria.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<ProdutoResponseDTO> listar() {

        return repository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Override
    public ProdutoResponseDTO buscarPorId(Long id) {

        Produto produto = buscarEntidadePorId(id);

        return toResponseDTO(produto);
    }

    @Override
    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {

        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setDisponivel(dto.getDisponivel());

        Restaurante restaurante = getRestauranteAutenticado();

        produto.setRestaurante(restaurante);

        if (dto.getCategoriasIds() != null && !dto.getCategoriasIds().isEmpty()) {

            Set<Categoria> categorias = dto.getCategoriasIds().stream().map(id -> categoriaRepository.findById(id).orElseThrow(() -> new NotFoundException("Categoria não encontrada"))).collect(Collectors.toSet());

            produto.setCategorias(categorias);
        }

        Produto salvo = repository.save(produto);

        return toResponseDTO(salvo);
    }

    @Override
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {

        Produto existente = buscarEntidadePorId(id);

        Restaurante restauranteAutenticado = getRestauranteAutenticado();

        if (!existente.getRestaurante().getId().equals(restauranteAutenticado.getId())) {

            throw new RuntimeException("Você não pode editar este produto");
        }

        existente.setNome(dto.getNome());
        existente.setDescricao(dto.getDescricao());
        existente.setPreco(dto.getPreco());
        existente.setDisponivel(dto.getDisponivel());

        if (dto.getCategoriasIds() != null) {

            Set<Categoria> categorias = dto.getCategoriasIds().stream().map(catId -> categoriaRepository.findById(catId).orElseThrow(() -> new NotFoundException("Categoria não encontrada"))).collect(Collectors.toSet());

            existente.setCategorias(categorias);
        }

        Produto atualizado = repository.save(existente);

        return toResponseDTO(atualizado);
    }

    @Override
    public List<ProdutoResponseDTO> listarPorRestaurante(Long restauranteId) {

        return repository.findByRestauranteId(restauranteId).stream().map(this::toResponseDTO).toList();
    }

    @Override
    public void deletar(Long id) {

        Produto produto = buscarEntidadePorId(id);

        Restaurante restauranteAutenticado = getRestauranteAutenticado();

        if (!produto.getRestaurante().getId().equals(restauranteAutenticado.getId())) {

            throw new RuntimeException("Você não pode remover este produto");
        }

        repository.delete(produto);
    }

    @Override
    public List<ProdutoResponseDTO> buscarPorNome(String nome) {

        return repository.findByNomeContainingIgnoreCase(nome).stream().map(this::toResponseDTO).toList();
    }

    @Override
    public List<ProdutoResponseDTO> meusProdutos() {

        Restaurante restaurante = getRestauranteAutenticado();

        return repository.findByRestauranteId(restaurante.getId()).stream().map(this::toResponseDTO).toList();
    }

    private Restaurante getRestauranteAutenticado() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return restauranteRepository.findByUsuarioUsername(username).orElseThrow(() -> new NotFoundException("Restaurante não encontrado"));
    }

    private Produto buscarEntidadePorId(Long id) {

        return repository.findById(id).orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    private ProdutoResponseDTO toResponseDTO(Produto produto) {

        ProdutoResponseDTO dto = new ProdutoResponseDTO();

        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setDisponivel(produto.getDisponivel());

        dto.setRestauranteNome(produto.getRestaurante().getNome());

        dto.setCategorias(produto.getCategorias().stream().map(Categoria::getNome).collect(Collectors.toList()));

        return dto;
    }
}