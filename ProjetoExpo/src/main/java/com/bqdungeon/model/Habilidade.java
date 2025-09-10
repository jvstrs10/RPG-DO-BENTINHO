package com.bqdungeon.model;

public class Habilidade {
    private String nome;
    private int custoDeMana;
    private int danoBase;
    private String descricao;

    public Habilidade(String nome, int custoDeMana, int danoBase, String descricao) {
        this.nome = nome;
        this.custoDeMana = custoDeMana;
        this.danoBase = danoBase;
        this.descricao = descricao;
    }

    // --- Getters ---
    public String getNome() { return nome; }
    public int getCustoDeMana() { return custoDeMana; }
    public int getDanoBase() { return danoBase; }
    public String getDescricao() { return descricao; }
}