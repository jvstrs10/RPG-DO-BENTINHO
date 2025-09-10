package com.bqdungeon.model;

// A classe Inimigo agora herda todos os atributos e métodos da classe Entidade.
public class Inimigo extends Entidade {

    private int xpConcedido;
    private Item drop;

    // --- Construtor ---
    // O construtor foi atualizado para receber a quantidade de XP que o inimigo concede.
    public Inimigo(String nome, int hpMax, int atk, int xpConcedido, Item drop) {
        super(nome, hpMax, atk);
        this.xpConcedido = xpConcedido;
        this.drop = drop;
    }

    // --- Getters e Setters ---

    public int getXpConcedido() {
        return xpConcedido;
    }

    public Item getDrop() {
        return drop;
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }


     // ✅ MÉTODO DE ATAQUE COM DEFESA
    public int atacar(Jogador jogador) {
        int dano = this.atk - jogador.getDefesa();
        if (dano < 0) {
            dano = 0;
        }
        jogador.receberDano(dano);
        return dano;
    }

}


