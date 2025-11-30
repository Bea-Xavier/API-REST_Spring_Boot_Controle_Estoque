package com.controleestoque.api_estoque.dto;

// DTO para representar a requisição de venda

import java.util.List; 

public class VendaRequest {
    private Long clienteId;
    private List<ItemVendaRequest> itens;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public List<ItemVendaRequest> getItens() { return itens; }
    public void setItens(List<ItemVendaRequest> itens) { this.itens = itens; }
}
