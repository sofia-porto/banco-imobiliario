package view;

import java.awt.*;
import model.JogoFacade;
import model.Observer;

class PainelLogFinanceiroView extends Panel implements Observer {
    private static final long serialVersionUID = 1L;

    private JogoFacade jogo;
    private TextArea logTextArea;
    private String ultimoLogProcessado = null;

    public PainelLogFinanceiroView(JogoFacade jogo) {
        this.jogo = jogo;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        setPreferredSize(new Dimension(260, 150)); 

        Label lblTitulo = new Label("Log de Ações", Label.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTitulo, BorderLayout.NORTH);

        logTextArea = new TextArea("", 4, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);
        logTextArea.setEditable(false);
        logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logTextArea.setBackground(new Color(250, 250, 250));
        add(logTextArea, BorderLayout.CENTER);
    }

    @Override
    public void atualizar() {
        String novoLog = jogo.getUltimoEventoLog();

        if (novoLog != null && !novoLog.equals(ultimoLogProcessado)) {
            
            if (novoLog.startsWith("💸") || 
                novoLog.startsWith("💰") || 
                novoLog.startsWith("🛒") || 
                novoLog.startsWith("🏦") || 
                novoLog.startsWith("🏠") || 
                novoLog.startsWith("🏨") || 
                novoLog.startsWith("🚓") || 
                novoLog.startsWith("🃏"))   
            {
                logTextArea.append(novoLog + "\n");
                ultimoLogProcessado = novoLog;
            }
        } 
        else if (novoLog == null) {
            ultimoLogProcessado = null;
        }
    }
}