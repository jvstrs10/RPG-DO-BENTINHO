package com.bqdungeon.model;

/**
 * Classe base abstrata para todas as criaturas e personagens do jogo.
 * Serve como um molde mestre, definindo os atributos e comportamentos
 * que são comuns a todos, como Jogadores, Inimigos e NPCs.
 */
public abstract class Entidade {

    // --- Atributos Fundamentais ---
    // 'protected' permite que esta classe e as suas classes filhas (Jogador, Inimigo)
    // acedam diretamente a estes atributos.
    protected String nome;
    protected int hpAtual; // Pontos de Vida Atuais
    protected int hpMax;   // Pontos de Vida Máximos
    protected int atk;     // Pontos de Ataque

    // --- Construtor ---
    // Protocolo de inicialização para qualquer entidade criada no jogo.
    public Entidade(String nome, int hpMax, int atk) {
        this.nome = nome;
        this.hpMax = hpMax;
        this.hpAtual = hpMax; // Toda a entidade começa com a vida cheia.
        this.atk = atk;
    }

    // --- Métodos de Ação ---

    /**
     * Executa um ataque contra outra entidade.
     * @param alvo A entidade que será atacada.
     */
    public void atacar(Entidade alvo) {
        // O alvo recebe dano igual aos pontos de ataque (atk) desta entidade.
        alvo.receberDano(this.atk);
    }

    /**
     * Reduz os pontos de vida da entidade com base no dano recebido.
     * @param dano A quantidade de dano a ser subtraída.
     */
    public void receberDano(int dano) {
        this.hpAtual -= dano;
        // Protocolo de segurança para garantir que a vida não fique negativa.
        if (this.hpAtual < 0) {
            this.hpAtual = 0;
        }
    }

    /**
     * Verifica se a entidade ainda está operacional (viva).
     * @return true se os pontos de vida forem maiores que zero, false caso contrário.
     */
    public boolean estaVivo() {
        return this.hpAtual > 0;
    }


    // --- Métodos de Acesso (Getters) ---
    // Protocolos para consultar o estado da entidade de forma segura.

    public String getNome() {
        return nome;
    }

    public int getHpAtual() {
        return hpAtual;
    }

    public int getHpMax() {
        return hpMax;
    }

    public int getAtk() {
        return atk;
    }
}


