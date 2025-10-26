package model;

import java.util.List;
import java.util.Random;

// Facade: fornece uma interface simplificada entre o model e a view
public class JogoFacade {

    private Jogo jogo = Jogo.getInstancia();
    private Random random = new Random();

    // Cria jogadores e define cores fixas pela ordem de entrada
    public void criarJogadores(int qtd) {
        String[] cores = {"#FF0000", "#0000FF", "#00FF00", "#FFFF00", "#FFA500", "#800080"}; // vermelho, azul, verde, amarelo, laranja, roxo
        for (int i = 0; i < qtd; i++) {
            jogo.adicionarJogador(new Jogador("Jogador " + (i + 1), 4000, cores[i]));
        }
    }

    public void iniciarPartida() {
        jogo.iniciarPartida();
    }

    public Jogador getJogadorAtual() {
        return jogo.getJogadorAtual();
    }

    public List<Jogador> getJogadores() {
        return jogo.getJogadores();
    }

    public void proximaJogada(int valorDado) {
        jogo.proximaJogada(valorDado);
    }

    // === Métodos adicionados para integração com a camada View ===

    /** Retorna o número total de jogadores */
    public int getNumeroJogadores() {
        return jogo.getJogadores().size();
    }

    /** Retorna o nome do jogador pelo índice */
    public String getJogadorNome(int indice) {
        if (indice >= 0 && indice < jogo.getJogadores().size()) {
            return jogo.getJogadores().get(indice).getNome();
        }
        return "";
    }

    /** Lança dois dados e retorna um array com os resultados */
    public int[] lancarDados() {
        return new int[]{random.nextInt(6) + 1, random.nextInt(6) + 1};
    }

    /** Move o jogador atual de acordo com o valor obtido nos dados */
    public void moverJogadorAtual(int casas) {
        jogo.moverJogadorAtual(casas);
    }

    // === Métodos de observação (padrão Observer) ===
    public void adicionarObservador(Observer o) {
        jogo.adicionarObservador(o);
    }

    public void removerObservador(Observer o) {
        jogo.removerObservador(o);
    }
}
