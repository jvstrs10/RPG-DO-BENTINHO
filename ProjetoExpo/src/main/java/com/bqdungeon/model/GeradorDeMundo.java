package com.bqdungeon.model;

import java.util.Random;

// A classe GeradorDeMundo é uma unidade de apoio (utilitária) para criar
// novas salas e inimigos de forma procedural.
public class GeradorDeMundo {

    private static Random rand = new Random();
    private static String[] adjetivosInimigo = {"Fraco", "Raivoso", "Pálido", "Antigo", "Amaldiçoado"};
    private static String[] tiposInimigo = {"Zumbi", "Esqueleto", "Morcego da Caverna", "Rato Gigante"};
    private static Item[] dropsPossiveis = {
            // CORREÇÃO: Adicionado o preço (valor de venda) para cada item.
            new Item("Osso Velho", Item.TipoItem.EQUIPAMENTO_ARMA, 2, 5),
            new Item("Pedaço de Armadura", Item.TipoItem.EQUIPAMENTO_ARMADURA, 2, 10),
            new Item("Asa de Morcego", Item.TipoItem.CONSUMIVEL_CURA, 5, 8),
            new Item("Poção de Cura Menor", Item.TipoItem.CONSUMIVEL_CURA, 20, 25)
    };

    /**
     * Gera uma nova sala conectada a uma sala existente.
     * @param profundidade O nível atual de profundidade na cripta.
     * @param salaDeOrigem A sala a partir da qual o jogador está a mover-se.
     * @param direcao A direção do movimento.
     * @return A nova Sala gerada.
     */
    public static Sala gerarNovaSala(int profundidade, Sala salaDeOrigem, String direcao) {
        int novoX = salaDeOrigem.getMapX();
        int novoY = salaDeOrigem.getMapY();
        switch (direcao) {
            case "norte": novoY--; break;
            case "sul":   novoY++; break;
            case "leste": novoX++; break;
            case "oeste": novoX--; break;
        }

        String descricao = "Uma gruta ecoante coberto de teias de aranha.";
        Sala novaSala = new Sala(descricao, novoX, novoY);

        // A cada 3 níveis de profundidade, a chance de encontrar um inimigo aumenta.
        if (rand.nextInt(100) < 25 + (profundidade * 3)) {
            novaSala.setInimigo(gerarInimigo(profundidade));
        }

        return novaSala;
    }

    /**
     * Gera um novo inimigo com atributos baseados na profundidade.
     * @param profundidade O nível atual de profundidade.
     * @return O novo Inimigo gerado.
     */
    public static Inimigo gerarInimigo(int profundidade) {
        String nome = adjetivosInimigo[rand.nextInt(adjetivosInimigo.length)] + " " + tiposInimigo[rand.nextInt(tiposInimigo.length)];
        int hp = 20 + (profundidade * 5);
        int atk = 4 + (profundidade * 2);
        int xp = 20 + (profundidade * 2);
        Item drop = dropsPossiveis[rand.nextInt(dropsPossiveis.length)];

        return new Inimigo(nome, hp, atk, xp, drop);
    }
}
