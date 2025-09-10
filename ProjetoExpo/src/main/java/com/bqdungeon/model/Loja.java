package com.bqdungeon.model;

import java.util.ArrayList;
import java.util.List;

// A classe Loja representa o inventário do mercante e as interações de comércio.
public class Loja {

    private List<Item> itensParaVenda;

    public Loja() {
        this.itensParaVenda = new ArrayList<>();
        abastecerLoja();
    }

    // Método que define os itens iniciais disponíveis na loja.
    private void abastecerLoja() {
        itensParaVenda.add(new Item("Poção de Cura Menor", Item.TipoItem.CONSUMIVEL_CURA, 20, 25, 0 ,0));
        itensParaVenda.add(new Item("Poção de Cura Média", Item.TipoItem.CONSUMIVEL_CURA, 50, 60, 0, 0));
        itensParaVenda.add(new Item("Espada Curta de Ferro", Item.TipoItem.EQUIPAMENTO_ARMA, 5, 100, 20, 0));
        itensParaVenda.add(new Item("Escudo de Madeira", Item.TipoItem.EQUIPAMENTO_ARMADURA, 10, 80, 0, 10));
    }

    // Permite que a GameGUI aceda à lista de itens para exibi-los.
    public List<Item> getItensParaVenda() {
        return itensParaVenda;
    }

    // --- NOVO MÉTODO PARA VENDER ITENS ---
    /**
     * Processa a venda de um item do inventário do jogador para a loja.
     * @param jogador O jogador que está realizando a venda.
     * @param itemParaVender O item do inventário que será vendido.
     * @return Uma String com o resultado da transação para ser exibida na GUI.
     */
    public String realizarVenda(Jogador jogador, Item itemParaVender) {
        // Verifica se o jogador realmente tem o item
        if (jogador.getInventario().contains(itemParaVender)) {

            int precoDeVenda = itemParaVender.getPreco();

            // 1. Remove o item do inventário do jogador
            jogador.removerItem(itemParaVender);

            // 2. Adiciona o dinheiro ao jogador
            jogador.adicionarDinheiro(precoDeVenda);

            // 3. Retorna uma mensagem de sucesso
            return "Você vendeu " + itemParaVender.getNome() + " por " + precoDeVenda + " de dinheiro.";

        } else {
            // Se, por algum motivo, o item não estiver lá, retorna uma mensagem de erro
            return "Você não possui este item para vender.";
        }
    }
}