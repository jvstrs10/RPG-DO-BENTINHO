package com.bqdungeon.model;

import com.bqdungeon.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameGUI extends JFrame {

    // --- Unidades Táticas e Estado do Jogo ---
    private Jogador jogador;
    private Sala salaAtual;
    private Inimigo inimigoAtual;
    private List<Sala> todasAsSalas;
    private int profundidade;
    private Loja loja; // Nova unidade para a loja.

    // --- Componentes da Interface Principal ---
    private CardLayout cardLayout;
    private JPanel painelCentral;
    private JLabel hpLabel, atkLabel, dinheiroLabel, nivelLabel, defesaLabel;
    private JProgressBar xpBar;
    private JTextArea displayPrincipal;
    private DefaultListModel<Item> modeloListaInventario;
    private JList<Item> listaInventario;
    private JPanel painelMapa;

    // --- Componentes do Ecrã de Batalha ---
    private JLabel nomeJogadorBatalha, nomeInimigoBatalha;
    private JProgressBar hpJogadorBar, hpInimigoBar;
    private JTextArea logBatalha;

    public GameGUI() {
        prepararMundo();
        inicializarUI();
        atualizarDisplay();
    }

    private void prepararMundo() {
        jogador = new Jogador("Aventureiro", 100, 15, 50, 3);
        jogador.adicionarItem(new Item("Adaga Enferrujada", Item.TipoItem.EQUIPAMENTO_ARMA, 2, 5));
        loja = new Loja(); // Inicializa a loja.

        todasAsSalas = new ArrayList<>();
        profundidade = 1;

        Sala entradaCripta = new Sala("Você está na entrada de uma cripta antiga, com paredes instáveis e passagens em todas as direções.", 0, 0);
        todasAsSalas.add(entradaCripta);
        salaAtual = entradaCripta;
    }

    private void inicializarUI() {
        setTitle("A Cripta do Rei Esquecido");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout(10, 10));

        add(criarPainelSuperior(), BorderLayout.NORTH);
        add(criarPainelEsquerdo(), BorderLayout.WEST);
        add(criarPainelCentral(), BorderLayout.CENTER);
        add(criarPainelMapa(), BorderLayout.EAST);

        setVisible(true);
    }

    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel painelStatus = new JPanel();
        painelStatus.setOpaque(false);
        painelStatus.setLayout(new BoxLayout(painelStatus, BoxLayout.Y_AXIS));

        JPanel painelAtributos = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        painelAtributos.setOpaque(false);
        nivelLabel = new JLabel("Nível 1");
        hpLabel = new JLabel("HP: 100/100");
        atkLabel = new JLabel("ATK: 15");
        defesaLabel = new JLabel("DEF: 3");
        estilizarLabel(nivelLabel, Color.CYAN);
        estilizarLabel(hpLabel, Color.WHITE);
        estilizarLabel(atkLabel, Color.WHITE);
        estilizarLabel(defesaLabel, Color.ORANGE);
        painelAtributos.add(nivelLabel);
        painelAtributos.add(hpLabel);
        painelAtributos.add(atkLabel);
        painelAtributos.add(defesaLabel);

        xpBar = new JProgressBar(0, 100);
        xpBar.setStringPainted(true);
        xpBar.setForeground(new Color(100, 100, 255));
        xpBar.setBackground(Color.DARK_GRAY);
        xpBar.setBorder(new LineBorder(Color.BLACK));

        painelStatus.add(painelAtributos);
        painelStatus.add(Box.createRigidArea(new Dimension(0, 5)));
        painelStatus.add(xpBar);

        dinheiroLabel = new JLabel("Ouro: 50");
        estilizarLabel(dinheiroLabel, Color.YELLOW);
        dinheiroLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        painel.add(painelStatus, BorderLayout.CENTER);
        painel.add(dinheiroLabel, BorderLayout.EAST);
        return painel;
    }

    private JPanel criarPainelEsquerdo() {
        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel areasTitulo = new JLabel("Áreas");
        estilizarLabel(areasTitulo, Color.WHITE);

        JButton btnExplorar = criarBotaoEstilizado("Explorar");
        btnExplorar.addActionListener(e -> cardLayout.show(painelCentral, "EXPLORACAO"));

        JButton btnLoja = criarBotaoEstilizado("Loja");
        btnLoja.addActionListener(e -> cardLayout.show(painelCentral, "LOJA"));

        JButton btnRestart = criarBotaoEstilizado("Reiniciar Missão");
        btnRestart.addActionListener(e -> reiniciarJogo());

        JLabel inventarioTitulo = new JLabel("Inventário");
        estilizarLabel(inventarioTitulo, Color.WHITE);
        modeloListaInventario = new DefaultListModel<>();
        listaInventario = new JList<>(modeloListaInventario);
        listaInventario.setBackground(new Color(50, 50, 50));
        listaInventario.setForeground(Color.WHITE);
        listaInventario.setSelectionBackground(new Color(0, 100, 0));
        JScrollPane painelScrollInventario = new JScrollPane(listaInventario);
        painelScrollInventario.setMaximumSize(new Dimension(200, 250));
        painelScrollInventario.setBorder(new LineBorder(Color.BLACK));

        JButton btnUsarItem = criarBotaoEstilizado("Usar Item");
        btnUsarItem.addActionListener(e -> usarItemSelecionado());

        painel.add(areasTitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(btnExplorar);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(btnLoja);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(btnRestart);
        painel.add(Box.createRigidArea(new Dimension(0, 20)));
        painel.add(inventarioTitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(painelScrollInventario);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(btnUsarItem);
        painel.add(Box.createVerticalGlue());

        return painel;
    }

    private JPanel criarPainelCentral() {
        cardLayout = new CardLayout();
        painelCentral = new JPanel(cardLayout);
        painelCentral.setOpaque(false);

        painelCentral.add(criarPainelExploracao(), "EXPLORACAO");
        painelCentral.add(criarPainelBatalha(), "BATALHA");
        painelCentral.add(criarPainelLoja(), "LOJA"); // Adiciona o painel da loja.

        return painelCentral;
    }

    private JPanel criarPainelLoja() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel tituloLoja = new JLabel("Mercadora da Cripta", SwingConstants.CENTER);
        tituloLoja.setFont(new Font("Tahoma", Font.BOLD, 24));
        tituloLoja.setForeground(Color.CYAN);

        DefaultListModel<Item> modeloListaLoja = new DefaultListModel<>();
        for (Item item : loja.getItensParaVenda()) {
            modeloListaLoja.addElement(item);
        }
        JList<Item> listaItensLoja = new JList<>(modeloListaLoja);
        listaItensLoja.setBackground(new Color(20, 20, 20));
        listaItensLoja.setForeground(Color.WHITE);
        listaItensLoja.setSelectionBackground(new Color(80, 80, 80));
        listaItensLoja.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JButton btnComprar = criarBotaoEstilizado("Comprar Item Selecionado");
        btnComprar.addActionListener(e -> {
            Item itemSelecionado = listaItensLoja.getSelectedValue();
            if (itemSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Nenhum item selecionado para compra.");
                return;
            }

            if (jogador.getDinheiro() >= itemSelecionado.getPreco()) {
                jogador.removerDinheiro(itemSelecionado.getPreco());
                jogador.adicionarItem(itemSelecionado);
                JOptionPane.showMessageDialog(this, "Você comprou: " + itemSelecionado.getNome());
                atualizarDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "Ouro insuficiente para comprar este item.");
            }
        });

        painel.add(tituloLoja, BorderLayout.NORTH);
        painel.add(new JScrollPane(listaItensLoja), BorderLayout.CENTER);
        painel.add(btnComprar, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelMapa() {
        painelMapa = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int tamanhoCelula = 30;
                int offsetX = (getWidth() / 2) - (salaAtual.getMapX() * tamanhoCelula);
                int offsetY = (getHeight() / 2) - (salaAtual.getMapY() * tamanhoCelula);

                for (Sala sala : todasAsSalas) {
                    int x = sala.getMapX() * tamanhoCelula + offsetX;
                    int y = sala.getMapY() * tamanhoCelula + offsetY;

                    if (sala == salaAtual) g.setColor(Color.YELLOW);
                    else if (sala.getInimigo() != null && sala.getInimigo().estaVivo()) g.setColor(Color.RED);
                    else g.setColor(Color.GRAY);

                    g.fillRect(x, y, tamanhoCelula - 5, tamanhoCelula - 5);
                    g.setColor(Color.WHITE);
                    g.drawRect(x, y, tamanhoCelula - 5, tamanhoCelula - 5);
                }
            }
        };
        painelMapa.setOpaque(false);
        painelMapa.setPreferredSize(new Dimension(250, 0));
        painelMapa.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Mapa da Cripta", 0, 0, null, Color.WHITE));
        return painelMapa;
    }

    private JPanel criarPainelExploracao() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));
        displayPrincipal = new JTextArea();
        displayPrincipal.setEditable(false);
        displayPrincipal.setFont(new Font("Monospaced", Font.PLAIN, 16));
        displayPrincipal.setLineWrap(true);
        displayPrincipal.setWrapStyleWord(true);
        displayPrincipal.setBackground(Color.BLACK);
        displayPrincipal.setForeground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(displayPrincipal);
        JPanel painelAcoes = new JPanel(new GridLayout(2, 2, 10, 10));
        painelAcoes.setOpaque(false);
        JButton btnNorte = new JButton("Ir Norte");
        JButton btnSul = new JButton("Ir Sul");
        JButton btnLeste = new JButton("Ir Leste");
        JButton btnOeste = new JButton("Ir Oeste");
        painelAcoes.add(btnNorte);
        painelAcoes.add(btnSul);
        painelAcoes.add(btnLeste);
        painelAcoes.add(btnOeste);
        btnNorte.addActionListener(e -> mover("norte"));
        btnSul.addActionListener(e -> mover("sul"));
        btnLeste.addActionListener(e -> mover("leste"));
        btnOeste.addActionListener(e -> mover("oeste"));
        painel.add(scrollPane, BorderLayout.CENTER);
        painel.add(painelAcoes, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarPainelBatalha() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel painelCombatentes = new JPanel(new GridLayout(1, 2, 20, 0));
        painelCombatentes.setOpaque(false);
        JPanel painelInfoJogador = new JPanel();
        painelInfoJogador.setLayout(new BoxLayout(painelInfoJogador, BoxLayout.Y_AXIS));
        painelInfoJogador.setOpaque(false);
        nomeJogadorBatalha = new JLabel("Jogador", SwingConstants.CENTER);
        nomeJogadorBatalha.setForeground(Color.WHITE);
        hpJogadorBar = new JProgressBar(0, 100);
        hpJogadorBar.setStringPainted(true);
        hpJogadorBar.setForeground(Color.GREEN);
        painelInfoJogador.add(nomeJogadorBatalha);
        painelInfoJogador.add(hpJogadorBar);
        JPanel painelInfoInimigo = new JPanel();
        painelInfoInimigo.setLayout(new BoxLayout(painelInfoInimigo, BoxLayout.Y_AXIS));
        painelInfoInimigo.setOpaque(false);
        nomeInimigoBatalha = new JLabel("Inimigo", SwingConstants.CENTER);
        nomeInimigoBatalha.setForeground(Color.WHITE);
        hpInimigoBar = new JProgressBar(0, 100);
        hpInimigoBar.setStringPainted(true);
        hpInimigoBar.setForeground(Color.RED);
        painelInfoInimigo.add(nomeInimigoBatalha);
        painelInfoInimigo.add(hpInimigoBar);
        painelCombatentes.add(painelInfoJogador);
        painelCombatentes.add(painelInfoInimigo);
        logBatalha = new JTextArea();
        logBatalha.setEditable(false);
        logBatalha.setBackground(Color.BLACK);
        logBatalha.setForeground(Color.WHITE);
        JPanel painelAcoesBatalha = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelAcoesBatalha.setOpaque(false);
        JButton btnAtacar = new JButton("Atacar");
        JButton btnHabilidade1 = new JButton("Habilidade 1");
        JButton btnFugir = new JButton("Fugir");
        painelAcoesBatalha.add(btnAtacar);
        painelAcoesBatalha.add(btnHabilidade1);
        painelAcoesBatalha.add(btnFugir);
        btnAtacar.addActionListener(e -> executarTurnoBatalha());
        btnFugir.addActionListener(e -> fugirBatalha());
        painel.add(painelCombatentes, BorderLayout.NORTH);
        painel.add(new JScrollPane(logBatalha), BorderLayout.CENTER);
        painel.add(painelAcoesBatalha, BorderLayout.SOUTH);
        return painel;
    }

    private void reiniciarJogo() {
        prepararMundo();
        inimigoAtual = null;
        cardLayout.show(painelCentral, "EXPLORACAO");
        atualizarDisplay();
    }

    private void atualizarDisplay() {
        nivelLabel.setText("Nível " + jogador.getNivel());
        hpLabel.setText("HP: " + jogador.getHpAtual() + "/" + jogador.getHpMax());
        atkLabel.setText("ATK: " + jogador.getAtk());
        defesaLabel.setText("DEF: " + jogador.getDefesa());
        dinheiroLabel.setText("Ouro: " + jogador.getDinheiro());

        xpBar.setMaximum(jogador.getXpParaProximoNivel());
        xpBar.setValue(jogador.getXpAtual());
        xpBar.setString("XP: " + jogador.getXpAtual() + " / " + jogador.getXpParaProximoNivel());

        displayPrincipal.setText(salaAtual.getDescricao());

        modeloListaInventario.clear();
        for (Item item : jogador.getInventario()) {
            modeloListaInventario.addElement(item);
        }

        if (salaAtual.getInimigo() != null && salaAtual.getInimigo().estaVivo()) {
            iniciarBatalha(salaAtual.getInimigo());
        }

        painelMapa.repaint();
    }

    private void mover(String direcao) {
        if (inimigoAtual != null && inimigoAtual.estaVivo()) {
            JOptionPane.showMessageDialog(this, "Você não pode sair durante uma batalha!", "Ação Bloqueada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Sala proximaSala = salaAtual.getSaida(direcao);

        if (proximaSala == null) {
            profundidade++;
            proximaSala = GeradorDeMundo.gerarNovaSala(profundidade, salaAtual, direcao);
            todasAsSalas.add(proximaSala);
            salaAtual.adicionarSaida(direcao, proximaSala);
            String direcaoOposta = getDirecaoOposta(direcao);
            proximaSala.adicionarSaida(direcaoOposta, salaAtual);
        }

        salaAtual = proximaSala;
        atualizarDisplay();
    }

    private String getDirecaoOposta(String direcao) {
        switch (direcao) {
            case "norte": return "sul";
            case "sul": return "norte";
            case "leste": return "oeste";
            case "oeste": return "leste";
            default: return "";
        }
    }

    private void iniciarBatalha(Inimigo inimigo) {
        inimigoAtual = inimigo;
        cardLayout.show(painelCentral, "BATALHA");

        nomeJogadorBatalha.setText(jogador.getNome());
        hpJogadorBar.setMaximum(jogador.getHpMax());
        hpJogadorBar.setValue(jogador.getHpAtual());
        hpJogadorBar.setString(jogador.getHpAtual() + "/" + jogador.getHpMax());

        nomeInimigoBatalha.setText(inimigoAtual.getNome());
        hpInimigoBar.setMaximum(inimigoAtual.getHpMax());
        hpInimigoBar.setValue(inimigoAtual.getHpAtual());
        hpInimigoBar.setString(inimigoAtual.getHpAtual() + "/" + inimigoAtual.getHpMax());

        logBatalha.setText("Você encontrou um " + inimigoAtual.getNome() + "!\n");
    }

    private void executarTurnoBatalha() {
        if (inimigoAtual == null || !inimigoAtual.estaVivo()) return;

        jogador.atacar(inimigoAtual);
        logBatalha.append("Você ataca o " + inimigoAtual.getNome() + ", causando " + jogador.getAtk() + " de dano.\n");
        hpInimigoBar.setValue(inimigoAtual.getHpAtual());
        hpInimigoBar.setString(inimigoAtual.getHpAtual() + "/" + inimigoAtual.getHpMax());

        if (!inimigoAtual.estaVivo()) {
            logBatalha.append("Você derrotou o " + inimigoAtual.getNome() + "!\n");

            int xpGanha = inimigoAtual.getXpConcedido();
            String mensagemLevelUp = jogador.ganharXp(xpGanha);
            logBatalha.append("Você ganhou " + xpGanha + " de XP!\n");

            if (!mensagemLevelUp.isEmpty()) {
                logBatalha.append(mensagemLevelUp);
                JOptionPane.showMessageDialog(this, mensagemLevelUp);
            }

            jogador.adicionarDinheiro(10);
            Item itemDropado = inimigoAtual.getDrop();
            if (itemDropado != null) {
                jogador.adicionarItem(itemDropado);
                logBatalha.append("Você obteve: " + itemDropado.getNome() + "!\n");
            }
            inimigoAtual = null;
            cardLayout.show(painelCentral, "EXPLORACAO");
            atualizarDisplay();
            return;
        }

        int danoReal = inimigoAtual.atacar(jogador); // ✅ aplica e retorna
        logBatalha.append("O " + inimigoAtual.getNome() + " ataca, causando " + danoReal + " de dano.\n");

    
        hpJogadorBar.setValue(jogador.getHpAtual());
        hpJogadorBar.setString(jogador.getHpAtual() + "/" + jogador.getHpMax());
        hpLabel.setText("HP: " + jogador.getHpAtual() + "/" + jogador.getHpMax());

        if (!jogador.estaVivo()) {
            logBatalha.append("Você foi derrotado...\n");
            JOptionPane.showMessageDialog(this, "Você foi derrotado!", "Fim de Jogo", JOptionPane.ERROR_MESSAGE);
            reiniciarJogo(); // ✅ Aqui está a mudança!
        }
    }

    private void usarItemSelecionado() {
        int indiceSelecionado = listaInventario.getSelectedIndex();
        if (indiceSelecionado != -1) {
            String resultado = jogador.usarItem(indiceSelecionado);
            JOptionPane.showMessageDialog(this, resultado);
            atualizarDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum item selecionado.");
        }
    }

    private void fugirBatalha() {
        logBatalha.append("Você fugiu da batalha!\n");
        inimigoAtual = null;
        cardLayout.show(painelCentral, "EXPLORACAO");
    }

    // --- MÉTODOS DE APOIO E ESTILIZAÇÃO ---

    private void estilizarLabel(JLabel label, Color cor) {
        label.setForeground(cor);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
    }

    private JButton criarBotaoEstilizado(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(80, 80, 80));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(new LineBorder(Color.BLACK));
        return botao;
    }
}