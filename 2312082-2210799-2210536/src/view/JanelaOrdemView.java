package view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;
import model.*;

class JanelaOrdemView extends Frame {

    private static final long serialVersionUID = 1L;
    private JogoFacade jogo;
    private Image[] pins = new Image[6];
    private final Color[] cores = {
        new Color(220, 0, 0),      // vermelho
        new Color(0, 80, 255),     // azul
        new Color(255, 140, 0),    // laranja
        new Color(255, 215, 0),    // amarelo
        new Color(128, 0, 128),    // roxo
        new Color(120, 120, 120)   // cinza
    };

    public JanelaOrdemView(JogoFacade jogo) {
        this.jogo = jogo;
        setTitle("Ordem dos Jogadores");
        setSize(340, 320);
        setLayout(new BorderLayout());
        setResizable(false);

        carregarPins();

        Panel centro = new Panel(new GridLayout(jogo.getNumeroJogadores(), 1, 0, 10));
        centro.setBackground(Color.WHITE);

        Label titulo = new Label("Ordem de Jogo Sorteada", Label.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        for (int i = 0; i < jogo.getNumeroJogadores(); i++) {
            final int idx = i;

            Panel linha = new Panel(new FlowLayout(FlowLayout.LEFT, 15, 5));
            linha.setBackground(Color.WHITE);

            Image pin = pins[idx];
            if (pin != null) {
                linha.add(new Canvas() {
                    public void paint(Graphics g) {
                        g.drawImage(pin, 0, 0, 24, 24, this);
                    }

                    public Dimension getPreferredSize() {
                        return new Dimension(28, 28);
                    }
                });
            } else {
                linha.add(new Canvas() {
                    public void paint(Graphics g) {
                        g.setColor(cores[idx]);
                        g.fillOval(5, 5, 18, 18);
                    }

                    public Dimension getPreferredSize() {
                        return new Dimension(28, 28);
                    }
                });
            }

            Label lbl = new Label((idx + 1) + "º - " + jogo.getJogadorNome(idx));
            lbl.setFont(new Font("Arial", Font.PLAIN, 14));
            linha.add(lbl);

            centro.add(linha);
        }

        add(centro, BorderLayout.CENTER);

        Panel rodape = new Panel(new FlowLayout(FlowLayout.CENTER));
        Button btnOk = new Button("OK");
        btnOk.setFont(new Font("Arial", Font.BOLD, 14));
        rodape.add(btnOk);
        add(rodape, BorderLayout.SOUTH);

        btnOk.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void carregarPins() {
        for (int i = 0; i < pins.length; i++) {
            try {
                URL u = getClass().getResource("/resources/pin" + i + ".png");
                if (u != null) pins[i] = ImageIO.read(u);
            } catch (IOException e) {}
        }
    }
}
