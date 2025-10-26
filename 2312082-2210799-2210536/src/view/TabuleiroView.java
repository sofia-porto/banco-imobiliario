package view;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import model.JogoFacade;
import model.Observer;

public class TabuleiroView extends Canvas implements KeyListener, MouseListener, Observer {

    private static final long serialVersionUID = 1L;

    private JogoFacade jogo;
    private Frame janela;
    private Image imagemTabuleiro;
    private Image[] imagensPioes = new Image[6];
    private int[] posicoes;
    private int jogadorDaVezView = 0;

    // Constantes geométricas do tabuleiro
    private static final int TOTAL_CASAS = 40;
    private static final int TAMANHO = 700;

    public TabuleiroView(JogoFacade jogo) {
        this.jogo = jogo;
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        carregarImagens();

        jogo.adicionarObservador(this);

        int n = jogo.getNumeroJogadores();
        posicoes = new int[n];
        for (int i = 0; i < n; i++) posicoes[i] = 0;
    }

    private void carregarImagens() {
        try {
            URL urlTabuleiro = getClass().getResource("/view/tabuleiro.png");
            System.out.println("🔍 Caminho da imagem: " + urlTabuleiro);
            imagemTabuleiro = ImageIO.read(urlTabuleiro);

            for (int i = 0; i < imagensPioes.length; i++) {
                URL u = getClass().getResource("/view/pin" + i + ".png");
                if (u != null) {
                    imagensPioes[i] = ImageIO.read(u);
                    System.out.println("✅ Pião " + i + " carregado de " + u);
                } else {
                    System.out.println("⚠️ Pião " + i + " não encontrado");
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Imagem do tabuleiro ou piões não encontrada.");
            imagemTabuleiro = null;
        }
    }

    public void exibir() {
        janela = new Frame("Banco Imobiliário - Tabuleiro");
        janela.add(this);
        janela.setSize(900, 800);
        janela.setVisible(true);

        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        janela.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                janela.dispose();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // fundo branco
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // desenha tabuleiro
        if (imagemTabuleiro != null) {
            g2.drawImage(imagemTabuleiro, 80, 50, TAMANHO, TAMANHO, this);
        } else {
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(80, 50, TAMANHO, TAMANHO);
            g2.setColor(Color.BLACK);
            g2.drawRect(80, 50, TAMANHO, TAMANHO);
            g2.drawString("TABULEIRO", 380, 400);
        }

        // desenha piões (um por pista)
        int numJogadores = jogo.getNumeroJogadores();
        for (int i = 0; i < numJogadores; i++) {
            int pos = posicoes[i];
            Point p = getCoordenadaDaCasa(pos, i);
            Image img = imagensPioes[i];

            if (img != null) {
            	g2.drawImage(img, p.x + 32, p.y + 32, 24, 24, this);
            } else {
                g2.setColor(Color.BLACK);
                g2.fillOval(p.x, p.y, 20, 20);
            }

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            g2.drawString(jogo.getJogadorNome(i), p.x, p.y + 40);
        }

        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.setColor(Color.BLACK);
        g2.drawString("Pressione ESPAÇO para lançar os dados", 200, 30);
    }

    /**
     * Retorna a posição (x, y) da casa considerando a pista do jogador.
     */
    private Point getCoordenadaDaCasa(int idx, int pista) {
        // parâmetros fixos do tabuleiro
        final int margem = 7;
        final int quina = 92;
        final int casa = 55;
        final int espaco = 2;
        final int tamanho = 700;

        // deslocamento uniforme pra dentro (pista 0 = mais externa)
        int deslocamento = pista * 8; // ~8 px entre pistas dá bom espaçamento

        // bordas internas da pista
        final int xIni = margem + deslocamento;
        final int yIni = margem + deslocamento;
        final int lado = tamanho - deslocamento * 2;
        final int passo = casa + espaco;

        int x = 0, y = 0;

        if (idx <= 10) { // base inferior (da direita pra esquerda)
            x = xIni + lado - quina - (idx * passo);
            y = yIni + lado - quina;
        } else if (idx <= 20) { // lado esquerdo (de baixo pra cima)
            x = xIni;
            y = yIni + lado - quina - ((idx - 10) * passo);
        } else if (idx <= 30) { // topo (da esquerda pra direita)
            x = xIni + ((idx - 20) * passo);
            y = yIni;
        } else if (idx <= 39) { // lado direito (de cima pra baixo)
            x = xIni + lado - quina;
            y = yIni + ((idx - 30) * passo);
        }

        // ajuste de offset global do tabuleiro na tela
        return new Point(80 + x, 50 + y);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            int[] dados = jogo.lancarDados();
            int soma = dados[0] + dados[1];
            System.out.println("🎲 Lançamento: " + dados[0] + " + " + dados[1] + " = " + soma);

            posicoes[jogadorDaVezView] = (posicoes[jogadorDaVezView] + soma) % TOTAL_CASAS;
            jogo.moverJogadorAtual(soma);
            jogadorDaVezView = (jogadorDaVezView + 1) % jogo.getNumeroJogadores();

            repaint();
        }
    }

    @Override public void atualizar() { repaint(); }
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}