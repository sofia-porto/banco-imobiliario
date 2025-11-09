package view;

import java.awt.*;
import java.awt.event.*;
import model.*;
import controller.JogoController; 

class PainelJogadorView extends Panel implements Observer {
    private static final long serialVersionUID = 1L;

    private JogoFacade jogo;
    private JogoController controller; 

    private Label lblNome;
    private Label lblSaldo;
    private Choice comboPropriedades;
    private Button btnCasa;
    private Button btnHotel;
    private Button btnVender;
    private Button btnEncerrar;
    private Button btnComprar;

    public PainelJogadorView(JogoFacade jogo, JogoController controller) {
        this.jogo = jogo;
        this.controller = controller; 
        
        setLayout(new BorderLayout(10, 10)); 
        setBackground(new Color(245, 245, 245));
        
        Panel painelInfo = new Panel(new GridLayout(2, 1));
        lblNome = new Label("Jogador: ");
        lblNome.setFont(new Font("Arial", Font.BOLD, 16));
        painelInfo.add(lblNome);

        lblSaldo = new Label("Saldo: ");
        lblSaldo.setFont(new Font("Arial", Font.PLAIN, 14));
        painelInfo.add(lblSaldo);
        
        add(painelInfo, BorderLayout.NORTH);

        Panel painelAcoes = new Panel(new GridLayout(0, 1, 5, 10));
        
        btnComprar = new Button("Comprar Propriedade");
        btnComprar.setFont(new Font("Arial", Font.BOLD, 12));
        painelAcoes.add(btnComprar);

        painelAcoes.add(new Label("Ações em Propriedades:", Label.CENTER));
        
        comboPropriedades = new Choice();
        comboPropriedades.add("Selecione uma propriedade");
        painelAcoes.add(comboPropriedades);

        btnCasa = new Button("Construir Casa");
        btnHotel = new Button("Construir Hotel");
        btnVender = new Button("Vender Propriedade");
        
        painelAcoes.add(btnCasa);
        painelAcoes.add(btnHotel);
        painelAcoes.add(btnVender);
        
        add(painelAcoes, BorderLayout.CENTER);

        btnEncerrar = new Button("Encerrar Turno"); 
        btnEncerrar.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnEncerrar, BorderLayout.SOUTH);

        configurarEventos();
    }


    private void configurarEventos() {
        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.comprarPropriedadeAtual();
            }
        });
        
        btnCasa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selecionada = comboPropriedades.getSelectedItem();
                controller.construirCasa(selecionada);
            }
        });

        btnHotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selecionada = comboPropriedades.getSelectedItem();
                controller.construirHotel(selecionada);
            }
        });

        btnVender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selecionada = comboPropriedades.getSelectedItem();
                controller.venderPropriedade(selecionada);
            }
        });
        
        btnEncerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.encerrarTurnoEChamarProximo();
                
                JanelaDadosView janelaDados = new JanelaDadosView(jogo, controller);
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
        for (String nome : props) {
            comboPropriedades.add(nome);
        }

        int posAtual = jogo.getJogadorPosicao(indiceAtual);
        boolean podeComprar = !jogo.propriedadeTemDono(posAtual) && jogo.getPropriedadePreco(posAtual) > 0;
        btnComprar.setEnabled(podeComprar); 

        repaint();
    }
}