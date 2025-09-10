package com.bqdungeon.model;

import java.util.HashMap;

public class Sala {

    // --- Atributos Fundamentais ---
    private String descricao;
    private Inimigo inimigo;
    private HashMap<String, Sala> saidas;
    private int mapX;
    private int  mapY; // Coordenadas para o nosso mapa tático.

    // --- Construtor ATUALIZADO ---
    // O construtor agora aceita a descrição e as coordenadas.
    public Sala(String descricao, int mapX, int mapY) {
        this.descricao = descricao;
        this.mapX = mapX;
        this.mapY = mapY;
        this.saidas = new HashMap<>();
        this.inimigo = null;
    }

    // Construtor antigo, pode ser mantido para salas sem mapa ou removido.
    public Sala(String descricao) {
        this(descricao, 0, 0); // Chama o novo construtor com coordenadas padrão.
    }

    // --- Métodos de Ação e Configuração ---
    public void adicionarSaida(String direcao, Sala salaVizinha) {
        saidas.put(direcao, salaVizinha);
    }

    public void setInimigo(Inimigo inimigo) {
        this.inimigo = inimigo;
    }

    // --- Métodos de Acesso (Getters) ---
    public String getDescricao() {
        return descricao;
    }

    public Inimigo getInimigo() {
        return inimigo;
    }

    public Sala getSaida(String direcao) {
        return saidas.get(direcao);
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }
}
