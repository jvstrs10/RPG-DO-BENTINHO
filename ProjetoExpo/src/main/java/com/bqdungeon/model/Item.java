package com.bqdungeon.model;

// A classe Item define a estrutura de todos os objetos que o jogador pode carregar.
public class Item {

    // Enum para categorizar os diferentes tipos de itens.
    public enum TipoItem {
        CONSUMIVEL_CURA,
        EQUIPAMENTO_ARMA,
        EQUIPAMENTO_ARMADURA
    }

    private String nome;
    private TipoItem tipo;
    private int valorEfeito;
    private int preco; // Novo atributo para o custo do item.

    // Construtor atualizado para incluir o preço.
    public Item(String nome, TipoItem tipo, int valorEfeito, int preco) {
        this.nome = nome;
        this.tipo = tipo;
        this.valorEfeito = valorEfeito;
        this.preco = preco;
    }

    // --- Getters ---
    public String getNome() { return nome; }
    public TipoItem getTipo() { return tipo; }
    public int getValorEfeito() { return valorEfeito; }
    public int getPreco() { return preco; }

    // O método toString é modificado para exibir o nome e o preço na interface.
    @Override
    public String toString() {
        return nome + " (" + preco + " Ouro)";
    }
}

