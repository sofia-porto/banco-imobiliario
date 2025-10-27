package view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;
import model.*;

class PainelDadosView extends Panel implements Observer {
    private static final long serialVersionUID = 1L;
    private JogoFacade jogo;

    private Checkbox chkManual;
    private Choice dado1, dado2;
    private Button btnLancar;
    private Image img1, img2;
    private int valor1 = 1, valor2 = 1;

    private final Color[] coresJogadores = {
        new Color(220, 0, 0),      // vermelho
        new Color(0, 80, 255),     // azul
        new Color(255, 140, 0),    // laranja
        new Color(255, 215, 0),    // amarelo
        new Color(128, 0, 128),    // roxo
        new Color(120, 120, 120)   // cinza
    };

    public PainelDadosView(JogoFacade jogo) {
        this.jogo = jogo;
        setPreferredSize(new Dimension(260, 180));
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        chkManual = new Checkbox("Modo manual", false);

        dado1 = new Choice();
        dado2 = new Choice();
        for (int i = 1; i <= 6; i++) {
            dado1.add(String.valueOf(i));
            dado2.add(String.valueOf(i));
        }

        btnLancar = new Button("🎲 Lançar Dados");
        btnLancar.setFont(new Font("Arial", Font.BOLD, 13));

        add(chkManual);
        add(btnLancar);

        carregarImagensDados(1, 1);

        chkManual.addItemListener(e -> atualizarVisibilidadeModo());

        btnLancar.addActionListener(e -> {
            if (chkManual.getState()) {
                valor1 = Integer.parseInt(dado1.getSelectedItem());
                valor2 = Integer.parseInt(dado2.getSelectedItem());
            } else {
                int[] valores = jogo.lancarDados();
                valor1 = valores[0];
                valor2 = valores[1];
            }

            carregarImagensDados(valor1, valor2);
            repaint();
            jogo.moverJogadorAtual(valor1 + valor2);
        
            Window parent = (Window) this.getParent();
            if (parent instanceof Frame) {
                parent.setVisible(false);
            }
        });
    }

    private void atualizarVisibilidadeModo() {
        removeAll();
        add(chkManual);

        if (chkManual.getState()) {
            add(new Label("Dado 1:"));
            add(dado1);
            add(new Label("Dado 2:"));
            add(dado2);
        }

        add(btnLancar);
        revalidate();
        repaint();
    }

    private void carregarImagensDados(int v1, int v2) {
        img1 = carregarImagem("die_face_" + v1 + ".png");
        img2 = carregarImagem("die_face_" + v2 + ".png");
    }

    private Image carregarImagem(String nomeArquivo) {
        try {
            URL url = getClass().getResource("/resources/" + nomeArquivo);
            return (url != null) ? ImageIO.read(url) : null;
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem: " + nomeArquivo);
            return null;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int indice = jogo.getIndiceJogadorAtual();
        if (indice >= 0 && indice < coresJogadores.length) {
            g.setColor(coresJogadores[indice]);
            g.fillRoundRect(15, 60, 160, 80, 15, 15);
        }

        if (img1 != null) g.drawImage(img1, 35, 75, 50, 50, this);
        if (img2 != null) g.drawImage(img2, 105, 75, 50, 50, this);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.drawString("Valores: " + valor1 + " + " + valor2 + " = " + (valor1 + valor2), 25, 170);
    }

    @Override
    public void atualizar() {
        repaint();
    }
}