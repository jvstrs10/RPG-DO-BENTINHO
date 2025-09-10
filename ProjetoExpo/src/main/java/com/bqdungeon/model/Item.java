package com.bqdungeon.model;

public class Item {

    public enum TipoItem {
        CONSUMIVEL_CURA,
        EQUIPAMENTO_ARMA,
        EQUIPAMENTO_ARMADURA
    }

    private String nome;
    private TipoItem tipo;
    private int valorEfeito; // Para poções, continua sendo a cura. Para equipamentos, pode ser ignorado ou usado para outra coisa.
    private int preco;

    // --- NOVOS ATRIBUTOS DE STATUS ---
    private int bonusAtk;
    private int bonusDef;

    // Construtor atualizado para incluir os bônus.
    public Item(String nome, TipoItem tipo, int valorEfeito, int preco, int bonusAtk, int bonusDef) {
        this.nome = nome;
        this.tipo = tipo;
        this.valorEfeito = valorEfeito;
        this.preco = preco;
        this.bonusAtk = bonusAtk;
        this.bonusDef = bonusDef;
    }

    // --- Getters ---
    public String getNome() { return nome; }
    public TipoItem getTipo() { return tipo; }
    public int getValorEfeito() { return valorEfeito; }
    public int getPreco() { return preco; }
    public int getBonusAtk() { return bonusAtk; } // Novo getter
    public int getBonusDef() { return bonusDef; } // Novo getter

    // O método toString agora mostrará os bônus do item.
    @Override
    public String toString() {
        String stats = "";
        if (bonusAtk > 0) stats += " +" + bonusAtk + " ATK";
        if (bonusDef > 0) stats += " +" + bonusDef + " DEF";

        if (!stats.isEmpty()) {
            return nome + " (" + stats.trim() + ") [" + preco + " Ouro]";
        } else {
            return nome + " [" + preco + " Ouro]";
        }
    }
}