package view;

import java.awt.*;
import java.awt.event.*;
import model.JogoFacade;
import controller.JogoController; 

public class JanelaInicialView extends Canvas implements KeyListener, MouseListener {
    private static final long serialVersionUID = 1L;

    private JogoFacade jogo;
    private JogoController controller; 
    
    private int numeroJogadores = 0;
    private String mensagem = "Digite um número de jogadores (3 a 6) e pressione Enter";

    private Frame janela; 
    private boolean ativo = true;

    public JanelaInicialView(JogoFacade jogo, JogoController controller) {
        this.jogo = jogo;
        this.controller = controller; 
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

        while (ativo) {
            repaint();
            try { Thread.sleep(16); } catch (InterruptedException e) {}
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

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
            
            Frame frameAtual = this.janela; 
            
            janela.dispose(); 

            JanelaOrdemView janelaOrdem = new JanelaOrdemView(frameAtual, jogo);
            
            janelaOrdem.setLocationRelativeTo(frameAtual); 
            janelaOrdem.setVisible(true); 
            
            JanelaPrincipalView janelaPrincipal = new JanelaPrincipalView(jogo, controller);
            janelaPrincipal.exibir();
            
        } else {
            mensagem = "Número inválido! Escolha entre 3 e 6.";
        }
    }
    
    @Override public void keyPressed(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        JogoFacade jogo = new JogoFacade();
        JogoController controller = new JogoController(jogo); 
        JanelaInicialView janela = new JanelaInicialView(jogo, controller); 
        janela.exibir();
    }
}