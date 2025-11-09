package view;

import java.awt.*;
import java.awt.event.*;
import model.*;
import controller.JogoController;

class JanelaDadosView extends Frame implements Observer {
    private static final long serialVersionUID = 1L;

    private JogoFacade jogo;
    private PainelDadosView painelDados;

    public JanelaDadosView(JogoFacade jogo, JogoController controller) {
        this.jogo = jogo;
        setTitle("Lançar Dados");
        setSize(300, 220);
        setResizable(false);
        setLayout(new BorderLayout());

        painelDados = new PainelDadosView(jogo, controller); 
        add(painelDados, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void abrirParaJogadorAtual() {
        int indice = jogo.getIndiceJogadorAtual(); 
        Color cor = getCorJogador(indice);
        setBackground(cor);
        setVisible(true);
    }

    public void fechar() {
        setVisible(false);
    }

    private Color getCorJogador(int i) {
        switch (i) {
            case 0: return new Color(220, 0, 0);      // vermelho
            case 1: return new Color(0, 80, 255);     // azul
            case 2: return new Color(255, 140, 0);    // laranja
            case 3: return new Color(255, 215, 0);    // amarelo
            case 4: return new Color(128, 0, 128);    // roxo
            case 5: return new Color(120, 120, 120);  // cinza
            default: return Color.WHITE;
        }
    }

    @Override
    public void atualizar() {
        int indice = jogo.getIndiceJogadorAtual();
        setBackground(getCorJogador(indice));
        repaint();
    }
}