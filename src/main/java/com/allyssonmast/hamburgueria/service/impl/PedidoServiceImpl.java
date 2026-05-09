package com.allyssonmast.hamburgueria.service.impl;

import com.allyssonmast.hamburgueria.dto.*;
import com.allyssonmast.hamburgueria.enums.StatusPedido;
import com.allyssonmast.hamburgueria.exception.BusinessException;
import com.allyssonmast.hamburgueria.exception.NotFoundException;
import com.allyssonmast.hamburgueria.model.*;
import com.allyssonmast.hamburgueria.repository.primary.ClienteRepository;
import com.allyssonmast.hamburgueria.repository.primary.PedidoRepository;
import com.allyssonmast.hamburgueria.repository.primary.ProdutoRepository;
import com.allyssonmast.hamburgueria.repository.primary.RestauranteRepository;
import com.allyssonmast.hamburgueria.service.AuditService;
import com.allyssonmast.hamburgueria.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private AuditService auditService;

    @Override
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId()).orElseThrow(() -> new NotFoundException("Restaurante não encontrado"));
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setValorTotal(0.0);
        double total = 0.0;
        for (ItemPedidoRequestDTO itemDto : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(() -> new NotFoundException("Produto não encontrado: " + itemDto.getProdutoId()));
            if (!produto.getRestaurante().getId().equals(restaurante.getId()))
                throw new BusinessException("Produto não pertence ao restaurante informado");
            if (Boolean.FALSE.equals(produto.getDisponivel()))
                throw new BusinessException("Produto indisponível: " + produto.getNome());
            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDto.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            pedido.getItens().add(item);
            total += produto.getPreco() * itemDto.getQuantidade();
        }
        pedido.setValorTotal(total);
        Pedido salvo = pedidoRepository.save(pedido);
        auditService.registrar("Pedido", salvo.getId(), "CREATE");
        return toDTO(salvo);
    }

    @Override
    public List<PedidoResponseDTO> listar() {
        return pedidoRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public PedidoResponseDTO buscarPorId(Long id) {
        return toDTO(buscarEntidade(id));
    }

    @Override
    public List<PedidoResponseDTO> historicoCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId).stream().map(this::toDTO).toList();
    }

    @Override
    public List<PedidoResponseDTO> listarPorRestaurante(Long restauranteId) {
        return pedidoRepository.findByRestauranteId(restauranteId).stream().map(this::toDTO).toList();
    }

    @Override
    public PedidoResponseDTO atualizarStatus(Long id, StatusPedido status) {
        Pedido pedido = buscarEntidade(id);
        pedido.setStatus(status);
        Pedido atualizado = pedidoRepository.save(pedido);
        auditService.registrar("Pedido", id, "UPDATE_STATUS_" + status.name());
        return toDTO(atualizado);
    }

    @Override
    public PedidoResponseDTO repetirPedido(Long id) {
        Pedido original = buscarEntidade(id);
        PedidoRequestDTO novo = new PedidoRequestDTO();
        novo.setClienteId(original.getCliente().getId());
        novo.setRestauranteId(original.getRestaurante().getId());
        novo.setItens(original.getItens().stream().map(item -> {
            ItemPedidoRequestDTO dto = new ItemPedidoRequestDTO();
            dto.setProdutoId(item.getProduto().getId());
            dto.setQuantidade(item.getQuantidade());
            return dto;
        }).toList());
        PedidoResponseDTO repetido = criar(novo);
        auditService.registrar("Pedido", repetido.getId(), "REPEAT_FROM_" + id);
        return repetido;
    }

    @Override
    public void cancelar(Long id) {
        Pedido pedido = buscarEntidade(id);
        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
        auditService.registrar("Pedido", id, "CANCEL");
    }

    private Pedido buscarEntidade(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
    }

    private PedidoResponseDTO toDTO(Pedido p) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(p.getId());
        dto.setClienteId(p.getCliente().getId());
        dto.setClienteNome(p.getCliente().getNome());
        dto.setRestauranteId(p.getRestaurante().getId());
        dto.setRestauranteNome(p.getRestaurante().getNome());
        dto.setStatus(p.getStatus());
        dto.setValorTotal(p.getValorTotal());
        dto.setCriadoEm(p.getCriadoEm());
        dto.setItens(p.getItens().stream().map(item -> {
            ItemPedidoResponseDTO ir = new ItemPedidoResponseDTO();
            ir.setProdutoId(item.getProduto().getId());
            ir.setProdutoNome(item.getProduto().getNome());
            ir.setQuantidade(item.getQuantidade());
            ir.setPrecoUnitario(item.getPrecoUnitario());
            ir.setSubtotal(item.getPrecoUnitario() * item.getQuantidade());
            return ir;
        }).toList());
        return dto;
    }
}
