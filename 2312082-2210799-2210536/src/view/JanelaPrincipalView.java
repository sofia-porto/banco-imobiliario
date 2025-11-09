package view;

import java.awt.*;
import java.awt.event.*;
import model.JogoFacade;
import model.Observer;
import controller.JogoController;

public class JanelaPrincipalView extends Frame implements Observer {
    
    private JogoFacade jogo;
    private JogoController controller;

    private TabuleiroView tabuleiroView;
    private PainelJogadorView painelJogadorView;
    private PainelLogFinanceiroView painelLogFinanceiroView; 

    public JanelaPrincipalView(JogoFacade jogo, JogoController controller) {
        this.jogo = jogo;
        this.controller = controller;

        this.jogo.adicionarObservador(this);

        setTitle("Banco Imobiliário");
        setSize(1100, 840);
        setLayout(new BorderLayout());

        tabuleiroView = new TabuleiroView(jogo, controller);
        add(tabuleiroView, BorderLayout.CENTER); 

        Panel painelLateral = new Panel(new BorderLayout());
        painelLateral.setPreferredSize(new Dimension(260, 800));
        painelLateral.setBackground(Color.WHITE); 

        painelJogadorView = new PainelJogadorView(jogo, controller);
        painelLogFinanceiroView = new PainelLogFinanceiroView(jogo); 
        
        painelLateral.add(painelJogadorView, BorderLayout.NORTH);
        painelLateral.add(painelLogFinanceiroView, BorderLayout.CENTER);
        
        add(painelLateral, BorderLayout.EAST);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Saindo do jogo...");
                dispose();
                System.exit(0); 
            }
        });

        jogo.adicionarObservador(tabuleiroView); 
        jogo.adicionarObservador(painelJogadorView);
        jogo.adicionarObservador(painelLogFinanceiroView); 
    }

    public void exibir() {
        setVisible(true);
        tabuleiroView.requestFocus(); 

        JanelaDadosView janelaDados = new JanelaDadosView(jogo, controller);
        janelaDados.abrirParaJogadorAtual();
    }

    @Override
    public void atualizar() {}
}