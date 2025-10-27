package view;

import java.awt.*;
import java.awt.event.*;
import model.*;

class PainelJogadorView extends Panel implements Observer {
    private static final long serialVersionUID = 1L;

    private JogoFacade jogo;

    private Label lblNome;
    private Label lblSaldo;
    private Choice comboPropriedades;
    private Button btnCasa;
    private Button btnHotel;
    private Button btnVender;
    private Button btnEncerrar;

    public PainelJogadorView(JogoFacade jogo) {
        this.jogo = jogo;
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(new Color(245, 245, 245));
        setPreferredSize(new Dimension(250, 250));

        configurarComponentes();
        configurarEventos();
    }

    private void configurarComponentes() {
        lblNome = new Label("Jogador: ");
        lblNome.setFont(new Font("Arial", Font.BOLD, 14));

        lblSaldo = new Label("Saldo: ");
        lblSaldo.setFont(new Font("Arial", Font.PLAIN, 13));

        comboPropriedades = new Choice();
        comboPropriedades.add("Selecione uma propriedade");

        btnCasa = new Button("Construir Casa");
        btnHotel = new Button("Construir Hotel");
        btnVender = new Button("Vender Propriedade");
        btnEncerrar = new Button("Encerrar Turno");

        add(lblNome);
        add(lblSaldo);
        add(new Label(" | Propriedades: "));
        add(comboPropriedades);
        add(btnCasa);
        add(btnHotel);
        add(btnVender);
        add(btnEncerrar);
    }

    private void configurarEventos() {
        btnCasa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jogo.construirCasa();
            }
        });

        btnHotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jogo.construirHotel();
            }
        });

        btnVender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jogo.venderPropriedade();
            }
        });
        
        btnEncerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JanelaDadosView janelaDados = new JanelaDadosView(jogo);
                janelaDados.abrirParaJogadorAtual();
            }
        });
    }

    @Override
    public void atualizar() {
        int indiceAtual = jogo.getIndiceJogadorAtual();

        lblNome.setText("Jogador: " + jogo.getJogadorNome(indiceAtual));
        lblSaldo.setText("Saldo: R$" + jogo.getJogadorSaldo(indiceAtual));

        comboPropriedades.removeAll();
        comboPropriedades.add("Selecione uma propriedade");
        String[] props = jogo.getPropriedadesDoJogador(indiceAtual);
        for (String nome : props) comboPropriedades.add(nome);

        repaint();
    }
}
