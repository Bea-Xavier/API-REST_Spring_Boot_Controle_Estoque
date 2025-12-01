package com.controleestoque.api_estoque.dto;

// DTO para representar a requisição de venda

import java.util.List; 

public class VendaRequest {
    private Long clienteId;
    private List<ItemVendaRequest> itensVenda;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public List<ItemVendaRequest> getItensVenda() { return itensVenda; }
    public void setItensVenda(List<ItemVendaRequest> itens) { this.itensVenda = itens; }
    @Override
    public String toString() {
        return "VendaRequest [clienteId=" + clienteId + ", itensVenda=" + itensVenda + ", getClienteId()=" + getClienteId()
                + ", getItens()=" + getItensVenda() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }
    
}
