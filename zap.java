package view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.JogoFacade;
import model.Observer;

/**
 * Este painel implementa o requisito "Propriedades e Finanças do Jogadores".
 * Ele usa componentes Swing (JPanel, JLabel, JComboBox) para exibir as
 * informações do jogador da vez, ficando ao lado do tabuleiro (Canvas).
 */
public class InfoView extends JPanel implements Observer {

    private JogoFacade jogo;
    private JLabel jogadorNomeLabel;
    private JLabel saldoLabel;
    private JComboBox<String> propriedadesComboBox;

    public InfoView(JogoFacade jogo) {
        this.jogo = jogo;
        this.jogo.adicionarObservador(this);

        // Configuração do Layout do Painel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Define um tamanho preferido para o painel lateral
        setPreferredSize(new Dimension(220, 800)); 

        // Título do Painel
        TitledBorder title = BorderFactory.createTitledBorder("Informações do Jogador");
        title.setTitleFont(new Font("Arial", Font.BOLD, 16));
        setBorder(title);

        // Componentes Swing para exibir dados
        jogadorNomeLabel = new JLabel("Jogador: ");
        jogadorNomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        saldoLabel = new JLabel("Saldo: R$ 0.00");
        saldoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel propsLabel = new JLabel("Propriedades:");
        propsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        propsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Espaçamento

        propriedadesComboBox = new JComboBox<>(); // ComboBox sugerida
        propriedadesComboBox.setFont(new Font("Arial", Font.PLAIN, 12));

        // Adiciona listener para exibir dados da propriedade selecionada (conforme sugestão)
        propriedadesComboBox.addActionListener(e -> {
            String nomeProp = (String) propriedadesComboBox.getSelectedItem();
            if (nomeProp != null) {
                // Ação futura: exibir detalhes da propriedade selecionada
                // (Por exemplo, em uma nova janela de diálogo)
                System.out.println("Jogador selecionou: " + nomeProp);
            }
        });


        // Adiciona componentes ao painel
        add(jogadorNomeLabel);
        add(saldoLabel);
        add(propsLabel);
        add(propriedadesComboBox);

        atualizar(); // Carga inicial
    }

    @Override
    public void atualizar() {
        // Atualiza os dados do jogador da vez buscando-os da Facade
        
        String nome = jogo.getJogadorAtualNome(); // Usa Facade
        double saldo = jogo.getJogadorAtualSaldo(); // Usa Facade
        
        if (nome != null) {
            jogadorNomeLabel.setText("Jogador: " + nome);
            saldoLabel.setText(String.format("Saldo: R$ %.2f", saldo));

            // Atualiza a ComboBox de propriedades
            propriedadesComboBox.removeAllItems();
            Set<String> nomesProps = jogo.getJogadorAtualPropriedadesNomes(); // Usa Facade
            
            if (nomesProps != null) {
                for (String nomeProp : nomesProps) {
                    propriedadesComboBox.addItem(nomeProp);
                }
            }
        }
    }
}
