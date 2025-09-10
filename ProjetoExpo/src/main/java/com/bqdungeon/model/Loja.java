package com.bqdungeon.model;

import java.util.ArrayList;
import java.util.List;

// A classe Loja representa o inventário do mercante.
public class Loja {

    private List<Item> itensParaVenda;

    public Loja() {
        this.itensParaVenda = new ArrayList<>();
        abastecerLoja();
    }

    // Método que define os itens iniciais disponíveis na loja.
    private void abastecerLoja() {
        itensParaVenda.add(new Item("Poção de Cura Menor", Item.TipoItem.CONSUMIVEL_CURA, 20, 25));
        itensParaVenda.add(new Item("Poção de Cura Média", Item.TipoItem.CONSUMIVEL_CURA, 50, 60));
        itensParaVenda.add(new Item("Espada Curta de Ferro", Item.TipoItem.EQUIPAMENTO_ARMA, 5, 100));
        itensParaVenda.add(new Item("Escudo de Madeira", Item.TipoItem.EQUIPAMENTO_ARMADURA, 10, 80));
    }

    // Permite que a GameGUI aceda à lista de itens para exibi-los.
    public List<Item> getItensParaVenda() {
        return itensParaVenda;
    }
}

