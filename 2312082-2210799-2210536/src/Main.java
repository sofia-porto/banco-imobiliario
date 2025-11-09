import model.JogoFacade;
import view.JanelaInicialView;
import controller.JogoController; // NOVO IMPORT

public class Main {
    public static void main(String[] args) {
        System.out.println("🎯 Iniciando Banco Imobiliário...");
        try {
            // 1. Cria a fachada do jogo (Model)
            JogoFacade jogo = new JogoFacade();
            
            // 2. NOVO: Cria o Controller
            JogoController controller = new JogoController(jogo);

            // 3. ATUALIZADO: Cria e exibe a janela inicial (passando o controller)
            JanelaInicialView janela = new JanelaInicialView(jogo, controller);
            janela.exibir();

            System.out.println("✅ Programa finalizado normalmente.");
        } catch (Exception e) {
            System.err.println("❌ Erro ao executar o jogo:");
            e.printStackTrace();
        }
    }
}