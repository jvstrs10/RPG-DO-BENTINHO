package com.bqdungeon;
import javax.swing.SwingUtilities;

import com.bqdungeon.model.*;

public class Main {

    /**
     * Ponto de entrada principal da aplicação.
     * A sua única missão é iniciar a interface gráfica (GameGUI) de forma segura.
     */
    public static void main(String[] args) {
        // Protocolo de segurança para iniciar a nossa interface gráfica
        // na thread correta, garantindo uma operação estável.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Damos a ordem para construir e exibir o nosso quartel-general visual.
                new GameGUI();
            }
        });
    }
}
