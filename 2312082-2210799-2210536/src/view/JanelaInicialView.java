package view;

import java.awt.*;
import java.awt.event.*;
import model.JogoFacade;

public class JanelaInicialView extends Canvas implements KeyListener, MouseListener {
    private static final long serialVersionUID = 1L;

    private JogoFacade jogo;
    private int numeroJogadores = 0;
    private String mensagem = "Digite um número de jogadores (3 a 6) e pressione Enter";

    private Frame janela; // substitui JFrame
    private boolean ativo = true;

    public JanelaInicialView(JogoFacade jogo) {
        this.jogo = jogo;
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
    }

    public void exibir() {
        janela = new Frame("Banco Imobiliário - Início");
        janela.add(this);
        janela.setSize(800, 600);
        janela.setVisible(true);
        janela.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ativo = false;
                janela.dispose();
            }
        });

        // Loop de renderização (sem Swing)
        while (ativo) {
            repaint();
            try { Thread.sleep(16); } catch (InterruptedException e) {}
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // fundo branco
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // título e mensagens
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString(mensagem, 80, 150);

        if (numeroJogadores > 0) {
            g2.drawString("Número escolhido: " + numeroJogadores, 80, 200);
        }

        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString("Clique ou pressione Enter para confirmar", 80, 250);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isDigit(c)) {
            numeroJogadores = Character.getNumericValue(c);
        } else if (c == KeyEvent.VK_ENTER) {
            confirmarJogadores();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        confirmarJogadores();
    }

    private void confirmarJogadores() {
        if (numeroJogadores >= 3 && numeroJogadores <= 6) {
            jogo.criarJogadores(numeroJogadores);
            ativo = false;
            janela.dispose();

            // abre tabuleiro sem Swing
            TabuleiroView tabuleiro = new TabuleiroView(jogo);
            tabuleiro.exibir();
        } else {
            mensagem = "Número inválido! Escolha entre 3 e 6.";
        }
    }

    // Métodos obrigatórios
    @Override public void keyPressed(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // Método main para testar isoladamente
    public static void main(String[] args) {
        JogoFacade jogo = new JogoFacade();
        JanelaInicialView janela = new JanelaInicialView(jogo);
        janela.exibir();
    }
}
