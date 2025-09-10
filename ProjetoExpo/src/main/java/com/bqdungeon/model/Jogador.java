package com.bqdungeon.model;

import java.util.ArrayList;
import java.util.List;

public class Jogador extends Entidade {

    private int dinheiro;
    private List<Item> inventario;

    // --- Atributos do Sistema de Nível ---
    private int nivel;
    private int xpAtual;
    private int xpParaProximoNivel;

    // Atributos do Sistema de Defesa
    private int defesa;

    public Jogador(String nome, int hpMax, int atk, int dinheiroInicial, int defesa) {
        super(nome, hpMax, atk);
        this.dinheiro = dinheiroInicial;
        this.inventario = new ArrayList<>();
        // O jogador sempre começa no nível 1 com 0 de XP.
        this.nivel = 1;
        this.xpAtual = 0;
        this.xpParaProximoNivel = 50; // Define a meta inicial de XP para o nível 2.
        this.defesa = defesa;
    }



    /**
     * Protocolo para ganhar experiência.
     * Adiciona o XP ganho e verifica se o jogador subiu de nível.
     * @param xpGanha A quantidade de XP recebida.
     * @return Uma string com a mensagem se o jogador subiu de nível, ou vazia caso contrário.
     */
    public String ganharXp(int xpGanha) {
        this.xpAtual += xpGanha;
        String mensagemLevelUp = "";
        // Usa um 'while' para o caso de o jogador ganhar XP suficiente para vários níveis.
        while (this.xpAtual >= this.xpParaProximoNivel) {
            subirDeNivel();
            mensagemLevelUp += "Você subiu para o nível " + this.nivel + "! Seus atributos aumentaram!\n";
        }
        return mensagemLevelUp;
    }

    /**
     * Protocolo privado para subir de nível.
     * Aumenta o nível, melhora os atributos, recupera o HP e define a nova meta de XP.
     */
    private void subirDeNivel() {
        this.nivel++;
        this.xpAtual -= this.xpParaProximoNivel; // Mantém o XP excedente.
        this.xpParaProximoNivel = (int) (this.xpParaProximoNivel * 1.5); // Aumenta a dificuldade para o próximo nível.

        // Fortalece os atributos.
        this.hpMax += 20;
        this.atk += 5;
        this.defesa += 7;
        this.hpAtual = this.hpMax; // Recupera toda a vida.
    }

    public String usarItem(int indiceDoItem) {
        if (indiceDoItem < 0 || indiceDoItem >= inventario.size()) {
            return "Seleção de item inválida.";
        }

        Item item = inventario.get(indiceDoItem);
        String resultado = "";

        switch (item.getTipo()) {
            case CONSUMIVEL_CURA:
                this.hpAtual += item.getValorEfeito();
                if (this.hpAtual > this.hpMax) {
                    this.hpAtual = this.hpMax;
                }
                resultado = "Você usa " + item.getNome() + " e recupera " + item.getValorEfeito() + " de HP.";
                inventario.remove(indiceDoItem);
                break;
            case EQUIPAMENTO_ARMA:
                resultado = "Você equipa " + item.getNome() + ".";
                break;
            default:
                resultado = "Este item não pode ser usado desta forma.";
                break;
        }
        return resultado;
    }

    // Getters e Setters
    public void adicionarItem(Item item) { this.inventario.add(item); }
    public List<Item> getInventario() { return inventario; }
    public int getDinheiro() { return dinheiro; }
    public void adicionarDinheiro(int quantia) { if (quantia > 0) this.dinheiro += quantia; }
    public void removerDinheiro(int quantia) { if (quantia > 0) this.dinheiro -= quantia; }

    public int getDefesa(){
        return defesa;
    }
    public void setDefesa(int defesa){
        this.defesa = defesa;
    }

    // Getters para o sistema de nível
    public int getNivel() { return nivel; }
    public int getXpAtual() { return xpAtual; }
    public int getXpParaProximoNivel() { return xpParaProximoNivel; }
}
