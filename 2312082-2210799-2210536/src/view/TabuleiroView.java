package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import model.*;

public class TabuleiroView extends Canvas implements KeyListener, MouseListener, Observer {

    private static final long serialVersionUID = 1L;

    private JogoFacade jogo;
    private Frame janela;
    private Image imagemTabuleiro;
    private Image[] imagensPioes = new Image[6];
    private int[] posicoes;
    private Map<String, Image> imagensCartas = new HashMap<>();

    private JanelaDadosView janelaDados;

    private static final int TAMANHO_TABULEIRO = 700;

    public TabuleiroView(JogoFacade jogo) {
        this.jogo = jogo;
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        carregarImagens();
        carregarCartas();

        jogo.adicionarObservador(this);

        int n = jogo.getNumeroJogadores();
        posicoes = new int[n];
        for (int i = 0; i < n; i++) posicoes[i] = 0;
    }

    private void carregarImagens() {
        try {
            URL urlTabuleiro = getClass().getResource("/resources/tabuleiro.png");
            imagemTabuleiro = ImageIO.read(urlTabuleiro);

            for (int i = 0; i < imagensPioes.length; i++) {
                URL u = getClass().getResource("/resources/pin" + i + ".png");
                if (u != null) imagensPioes[i] = ImageIO.read(u);
            }
        } catch (IOException e) {
            imagemTabuleiro = null;
        }
    }

    private void carregarCartas() {
        for (int i = 1; i <= 30; i++) {
            String id = "chance" + i;
            try {
                URL u = getClass().getResource("/resources/" + id + ".png");
                if (u != null) imagensCartas.put(id, ImageIO.read(u));
            } catch (IOException e) {}
        }
    }

    public void exibir() {
        janela = new Frame("Banco Imobiliário - Tabuleiro");
        janela.setLayout(new BorderLayout());

        janela.add(this, BorderLayout.CENTER);

        Panel painelDireita = new Panel(new BorderLayout());
        painelDireita.setPreferredSize(new Dimension(480, 800));
        painelDireita.setBackground(Color.WHITE);

        Panel coluna = new Panel(new GridLayout(2, 1, 0, 10));
        coluna.setBackground(Color.WHITE);

        PainelInfoPropriedadeView painelProp = new PainelInfoPropriedadeView(jogo);
        PainelJogadorView painelJog = new PainelJogadorView(jogo);

        coluna.add(painelProp);
        coluna.add(painelJog);

        painelDireita.add(coluna, BorderLayout.CENTER);
        janela.add(painelDireita, BorderLayout.EAST);

        jogo.adicionarObservador(painelProp);
        jogo.adicionarObservador(painelJog);

        janela.setSize(1280, 800);
        janela.setResizable(false);
        janela.setVisible(true);

        janela.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                janela.dispose();
            }
        });

        jogo.iniciarPartida();

        JanelaOrdemView ordem = new JanelaOrdemView(jogo);
        ordem.setLocationRelativeTo(null);
        ordem.setVisible(true);

        janelaDados = new JanelaDadosView(jogo);
        janelaDados.abrirParaJogadorAtual();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (imagemTabuleiro != null) {
            g2.drawImage(imagemTabuleiro, 80, 50, TAMANHO_TABULEIRO, TAMANHO_TABULEIRO, this);
        } else {
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(80, 50, TAMANHO_TABULEIRO, TAMANHO_TABULEIRO);
            g2.setColor(Color.BLACK);
            g2.drawRect(80, 50, TAMANHO_TABULEIRO, TAMANHO_TABULEIRO);
            g2.drawString("TABULEIRO", 380, 400);
        }

        int numJogadores = jogo.getNumeroJogadores();
        for (int i = 0; i < numJogadores; i++) {
            int pos = jogo.getJogadorPosicao(i);
            Point p = getCoordenadaDaCasa(pos, i);
            Image img = imagensPioes[i];
            if (img != null) {
                g2.drawImage(img, p.x + 32, p.y + 32, 24, 24, this);
            }
        }

        String idCarta = jogo.getUltimaCartaGlobal();
        if (idCarta != null) {
            Image cartaImg = imagensCartas.get(idCarta);
            if (cartaImg != null) {
                g2.drawImage(cartaImg, 370, 300, 200, 200, this);
                g2.setColor(Color.BLACK);
                g2.drawRect(370, 300, 200, 200);
            }
        }
    }

    private Point getCoordenadaDaCasa(int idx, int pista) {
        final int margem = 7;
        final int quina = 92;
        final int casa = 55;
        final int espaco = 2;
        final int tamanho = 700;
        int deslocamento = pista * 8;
        final int xIni = margem + deslocamento;
        final int yIni = margem + deslocamento;
        final int lado = tamanho - deslocamento * 2;
        final int passo = casa + espaco;

        int x = 0, y = 0;
        if (idx <= 10) {
            x = xIni + lado - quina - (idx * passo);
            y = yIni + lado - quina;
        } else if (idx <= 20) {
            x = xIni;
            y = yIni + lado - quina - ((idx - 10) * passo);
        } else if (idx <= 30) {
            x = xIni + ((idx - 20) * passo);
            y = yIni;
        } else if (idx <= 39) {
            x = xIni + lado - quina;
            y = yIni + ((idx - 30) * passo);
        }
        return new Point(80 + x, 50 + y);
    }

    @Override public void atualizar() { repaint(); }
    @Override public void keyPressed(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
