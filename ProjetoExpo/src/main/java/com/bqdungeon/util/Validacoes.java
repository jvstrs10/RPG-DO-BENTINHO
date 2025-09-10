package com.bqdungeon.util;
/**
 * Classe de Utilidade (Utility Class) que contém métodos estáticos
 * para realizar validações e outras operações de apoio em todo o projeto.
 *
 * Pense nisto como um "kit de ferramentas" para o nosso jogo.
 */
public final class Validacoes {

    /**
     * Construtor privado para impedir a instanciação desta classe de utilidade.
     * Ninguém pode criar um objeto 'new Validacoes()'.
     */
    private Validacoes() {
        // Esta classe não deve ser instanciada.
    }

    /**
     * Limpa e padroniza um comando inserido pelo utilizador.
     * Remove espaços em branco no início e no fim e converte tudo para letras minúsculas.
     *
     * @param input O texto original inserido pelo utilizador.
     * @return O texto limpo e padronizado.
     */
    public static String limparComando(String input) {
        if (input == null) {
            return ""; // Protocolo de segurança para evitar erros com entradas nulas.
        }
        return input.trim().toLowerCase();
    }

    

    // Futuramente, podemos adicionar aqui outras ferramentas, como:
    // public static boolean ehNumero(String texto) { ... }
    // public static int gerarNumeroAleatorio(int min, int max) { ... }
}
