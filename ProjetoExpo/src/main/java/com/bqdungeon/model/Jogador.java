package com.bqdungeon.model;

import java.util.ArrayList;
import java.util.List;

public class Jogador extends Entidade {

    // --- Atributos de Jogo ---
    private int dinheiro;
    private List<Item> inventario;

    // --- Atributos do Sistema de Nível ---
    private int nivel;
    private int xpAtual;
    private int xpParaProximoNivel;

    // --- Atributos de Mana!

    private int manaAtual;
    private int manaMax;

    // --- HABILIDADE DO JOGADOR (NOVO) ---
    private Habilidade habilidadePrincipal;

    // --- Atributos do Sistema de Defesa ---
    private int defesa;

    // --- ATRIBUTOS DE EQUIPAMENTO (NOVOS) ---
    private Item armaEquipada;
    private Item armaduraEquipada;

    public Jogador(String nome, int hpMax, int atk, int dinheiroInicial, int defesa) {
        super(nome, hpMax, atk);
        this.dinheiro = dinheiroInicial;
        this.inventario = new ArrayList<>();
        this.nivel = 1;
        this.xpAtual = 0;
        this.xpParaProximoNivel = 50;
        this.defesa = defesa;
        // Garante que o jogador começa sem itens equipados.
        this.armaEquipada = null;
        this.armaduraEquipada = null;
        // Mana related atributos;
        this.manaMax = 50; // Valor inicial de Mana Máxima
        this.manaAtual = this.manaMax; // Começa com mana cheia
        this.habilidadePrincipal = new Habilidade("Golpe Poderoso", 15, 20, "Um ataque concentrado que causa dano extra.");
    }

    // --- MÉTODOS DE CÁLCULO DE STATUS (NOVOS) ---
    /**
     * Calcula o ataque total do jogador (base + bônus da arma).
     * @return O valor total do ataque.
     */
    public int getTotalAtk() {
        int totalAtk = this.atk; // Começa com o ataque base do jogador.
        if (armaEquipada != null) {
            totalAtk += armaEquipada.getBonusAtk();
        }
        return totalAtk;
    }

    /**
     * Calcula a defesa total do jogador (base + bônus da armadura).
     * @return O valor total da defesa.
     */
    public int getTotalDef() {
        int totalDef = this.defesa; // Começa com a defesa base do jogador.
        if (armaduraEquipada != null) {
            totalDef += armaduraEquipada.getBonusDef();
        }
        return totalDef;
    }

    // --- MÉTODO DE COMBATE (SOBRESCRITO) ---
    @Override
    public void atacar(Entidade alvo) {
        // O jogador ataca usando o seu ataque total (base + bônus do item).
        int danoTotal = this.getTotalAtk();
        alvo.receberDano(danoTotal);
    }

    // --- MÉTODOS DE GERENCIAMENTO DE ITENS (ATUALIZADOS) ---

    /**
     * Equipa um item. Se já houver um item no slot, ele é devolvido ao inventário.
     * @param itemParaEquipar O item do inventário a ser equipado.
     * @return Uma mensagem de resultado.
     */
    public String equiparItem(Item itemParaEquipar) {
        if (!inventario.contains(itemParaEquipar)) {
            return "Você não possui este item.";
        }

        switch (itemParaEquipar.getTipo()) {
            case EQUIPAMENTO_ARMA:
                // Se já tiver uma arma equipada, devolve ela para o inventário primeiro.
                if (this.armaEquipada != null) {
                    this.adicionarItem(this.armaEquipada);
                }
                this.armaEquipada = itemParaEquipar;
                this.removerItem(itemParaEquipar); // Remove o item do inventário, pois agora está equipado.
                return "Você equipou: " + itemParaEquipar.getNome();

            case EQUIPAMENTO_ARMADURA:
                // Se já tiver uma armadura equipada, devolve ela para o inventário.
                if (this.armaduraEquipada != null) {
                    this.adicionarItem(this.armaduraEquipada);
                }
                this.armaduraEquipada = itemParaEquipar;
                this.removerItem(itemParaEquipar);
                return "Você equipou: " + itemParaEquipar.getNome();

            default:
                return "Este item não pode ser equipado.";
        }
    }

    // --- MÉTODOS DE MANA (NOVOS) ---

    public int getManaAtual() {
        return manaAtual;
    }

    public int getManaMax() {
        return manaMax;
    }

    /**
     * Tenta consumir uma quantidade de mana.
     * @param custo A quantidade de mana a ser consumida.
     * @return true se a mana foi consumida com sucesso, false caso contrário.
     */
    public boolean consumirMana(int custo) {
        if (this.manaAtual >= custo) {
            this.manaAtual -= custo;
            return true;
        }
        return false; // Mana insuficiente
    }

    // Metódo da habilidade signalda
    public String usarHabilidade(Habilidade skill, Entidade alvo) {
        if (consumirMana(skill.getCustoDeMana())) {
            // O dano da skill é a sua base + metade do ataque total do jogador
            int danoTotal = skill.getDanoBase() + (this.getTotalAtk() / 2);
            alvo.receberDano(danoTotal);
            return "Você usou '" + skill.getNome() + "' e causou " + danoTotal + " de dano!";
        } else {
            return "Mana insuficiente para usar '" + skill.getNome() + "'!";
        }
    }

    // Adicione um getter para a GUI poder acessar a habilidade
    public Habilidade getHabilidadePrincipal() {
        return habilidadePrincipal;
    }

    /**
     * Usa um item consumível do inventário.
     * @param indiceDoItem O índice do item na lista do inventário.
     * @return Uma mensagem de resultado.
     */
    public String usarItem(int indiceDoItem) {
        if (indiceDoItem < 0 || indiceDoItem >= inventario.size()) {
            return "Seleção de item inválida.";
        }

        Item item = inventario.get(indiceDoItem);
        String resultado = "";

        // Este método agora foca apenas em itens consumíveis.
        if (item.getTipo() == Item.TipoItem.CONSUMIVEL_CURA) {
            this.hpAtual += item.getValorEfeito();
            if (this.hpAtual > this.hpMax) {
                this.hpAtual = this.hpMax;
            }
            resultado = "Você usa " + item.getNome() + " e recupera " + item.getValorEfeito() + " de HP.";
            inventario.remove(indiceDoItem);
        } else {
            resultado = "Este item não pode ser usado, apenas equipado.";
        }
        return resultado;
    }

    public void adicionarItem(Item item) {
        this.inventario.add(item);
    }

    public void removerItem(Item item) {
        this.inventario.remove(item);
    }

    // --- MÉTODOS DE EXPERIÊNCIA E NÍVEL ---

    public String ganharXp(int xpGanha) {
        this.xpAtual += xpGanha;
        String mensagemLevelUp = "";
        while (this.xpAtual >= this.xpParaProximoNivel) {
            subirDeNivel();
            mensagemLevelUp += "Você subiu para o nível " + this.nivel + "! Seus atributos aumentaram!\n";
        }
        return mensagemLevelUp;
    }

    private void subirDeNivel() {
        this.nivel++;
        this.xpAtual -= this.xpParaProximoNivel;
        this.xpParaProximoNivel = (int) (this.xpParaProximoNivel * 1.5);

        this.hpMax += 20;
        this.atk += 5;
        this.defesa += 7;
        this.hpAtual = this.hpMax;
        this.manaMax += 10; // Aumenta a mana máxima
        this.hpAtual = this.hpMax; // Recupera vida
        this.manaAtual = this.manaMax; // Recupera mana
    }

    // --- GETTERS E SETTERS ---

    public List<Item> getInventario() { return inventario; }
    public int getDinheiro() { return dinheiro; }
    public void adicionarDinheiro(int quantia) { if (quantia > 0) this.dinheiro += quantia; }
    public void removerDinheiro(int quantia) { if (quantia > 0) this.dinheiro -= quantia; }
    public int getDefesa(){ return defesa; }
    public void setDefesa(int defesa){ this.defesa = defesa; }
    public int getNivel() { return nivel; }
    public int getXpAtual() { return xpAtual; }
    public int getXpParaProximoNivel() { return xpParaProximoNivel; }
}