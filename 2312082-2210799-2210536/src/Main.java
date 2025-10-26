import model.JogoFacade;
import view.JanelaInicialView;

public class Main {
    public static void main(String[] args) {
        System.out.println("🎯 Iniciando Banco Imobiliário...");
        try {
            // Cria a fachada do jogo (camada de controle simplificada)
            JogoFacade jogo = new JogoFacade();

            // Cria e exibe a janela inicial
            JanelaInicialView janela = new JanelaInicialView(jogo);
            janela.exibir();

            System.out.println("✅ Programa finalizado normalmente.");
        } catch (Exception e) {
            System.err.println("❌ Erro ao executar o jogo:");
            e.printStackTrace();
        }
    }
}
