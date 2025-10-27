package model;

import java.util.Random;

public class JogoFacade {

    private Jogo jogo = Jogo.getInstancia();
    private Random random = new Random();

    public void criarJogadores(int qtd) {
        String[] cores = {"#FF0000", "#0000FF", "#00FF00", "#FFFF00", "#FFA500", "#800080"};
        for (int i = 0; i < qtd; i++) {
            jogo.adicionarJogador(new Jogador("Jogador " + (i + 1), 4000, cores[i]));
        }
    }

    public void iniciarPartida() {
        jogo.iniciarPartida();
    }

   
    public int[] lancarDados() {
        return new int[]{random.nextInt(6) + 1, random.nextInt(6) + 1};
    }

    public void moverJogadorAtual(int casas) {
        jogo.moverJogadorAtual(casas);
    }

    public void passarVez() {
        jogo.limparCartaGlobal();
        jogo.proximaJogada(0);
    }

    public void adicionarObservador(Observer o) {
        jogo.adicionarObservador(o);
    }

    public void removerObservador(Observer o) {
        jogo.removerObservador(o);
    }

   
    public int getNumeroJogadores() {
        return jogo.getJogadores().size();
    }

    public int getIndiceJogadorAtual() {
        return jogo.getJogadores().indexOf(jogo.getJogadorAtual());
    }
    
    public int getIndiceJogadorPorNome(String nome) {
        for (int i = 0; i < jogo.getJogadores().size(); i++) {
            if (jogo.getJogadores().get(i).getNome().equals(nome)) {
                return i;
            }
        }
        return -1;
    }

    public String getJogadorNome(int indice) {
        if (indice >= 0 && indice < jogo.getJogadores().size()) {
            return jogo.getJogadores().get(indice).getNome();
        }
        return "";
    }

    public double getJogadorSaldo(int indice) {
        if (indice >= 0 && indice < jogo.getJogadores().size()) {
            return jogo.getJogadores().get(indice).getSaldo();
        }
        return 0;
    }

    public int getJogadorPosicao(int indice) {
        if (indice >= 0 && indice < jogo.getJogadores().size()) {
            return jogo.getJogadores().get(indice).getPosicao();
        }
        return 0;
    }

    public String[] getPropriedadesDoJogador(int indice) {
        if (indice >= 0 && indice < jogo.getJogadores().size()) {
            return jogo.getJogadores().get(indice).getPropriedades()
                        .stream()
                        .map(Propriedade::getNome)
                        .toArray(String[]::new);
        }
        return new String[0];
    }

    public String getPropriedadeNome(int posicao) {
        Propriedade p = jogo.getTabuleiro().getCasa(posicao);
        return (p != null) ? p.getNome() : "";
    }

    public double getPropriedadePreco(int posicao) {
        Propriedade p = jogo.getTabuleiro().getCasa(posicao);
        return (p != null) ? p.getPreco() : 0;
    }

    public boolean propriedadeTemDono(int posicao) {
        Propriedade p = jogo.getTabuleiro().getCasa(posicao);
        return (p != null && p.temDono());
    }

    public String getPropriedadeDonoNome(int posicao) {
        Propriedade p = jogo.getTabuleiro().getCasa(posicao);
        return (p != null && p.temDono()) ? p.getDono().getNome() : null;
    }

    public int getPropriedadeCasas(int posicao) {
        Propriedade p = jogo.getTabuleiro().getCasa(posicao);
        return (p != null) ? p.getCasas() : 0;
    }

    public boolean getPropriedadeTemHotel(int posicao) {
        Propriedade p = jogo.getTabuleiro().getCasa(posicao);
        return (p != null && p.temHotel());
    }

    public String getUltimaCartaGlobal() {
        return jogo.getUltimaCartaGlobal();
    }

    public void construirCasa() {
        jogo.construirCasa();
    }

    public void construirHotel() {
        jogo.construirHotel();
    }

    public void venderPropriedade() {
        jogo.venderPropriedade();
    }
}
