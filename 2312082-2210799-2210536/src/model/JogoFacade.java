package model;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class JogoFacade {
    private Jogo jogo = Jogo.getInstancia();
    private Random random = new Random();
    
    private int[] dadosFixos = null; 

    public void criarJogadores(int qtd) {
        String[] cores = {"#FF0000", "#0000FF", "#00FF00", "#FFFF00", "#FFA500", "#800080"}; 
        for (int i = 0; i < qtd; i++) {
            jogo.adicionarJogador(new Jogador("Jogador " + (i + 1), 4000, cores[i]));
        }
    }
    public void iniciarPartida() { jogo.iniciarPartida(); }
    public int[] lancarDados() {
        if (dadosFixos != null) {
            int[] resultado = dadosFixos;
            dadosFixos = null; 
            return resultado;
        }
        return new int[]{random.nextInt(6) + 1, random.nextInt(6) + 1};
    }
    public void setProximosDados(int dado1, int dado2) { this.dadosFixos = new int[]{dado1, dado2}; }
    public void moverJogadorAtual(int casas) { jogo.moverJogadorAtual(casas); }
    public void encerrarTurno() { jogo.encerrarTurno(); }
    public boolean comprarPropriedadeAtual() { return jogo.comprarPropriedadeAtual(); }
    public boolean construirCasa(String nomePropriedade) { return jogo.construirCasa(nomePropriedade); }
    public boolean construirHotel(String nomePropriedade) { return jogo.construirHotel(nomePropriedade); }
    public boolean venderPropriedade(String nomePropriedade) { return jogo.venderPropriedade(nomePropriedade); }
    public Jogador getJogadorAtual() { return jogo.getJogadorAtual(); }
    public int getIndiceJogadorAtual() { return jogo.getIndiceJogadorAtual(); }
    public int getIndiceJogadorPorNome(String nome) { return jogo.getIndicePorNome(nome); }
    public double getJogadorSaldo(int indice) { return jogo.getJogadorSaldo(indice); }
    public String[] getPropriedadesDoJogador(int indice) {
        Set<String> nomes = jogo.getPropriedadesDoJogador(indice);
        if (nomes != null) { return nomes.toArray(new String[0]); }
        return new String[0]; 
    }
    public String getUltimaCartaGlobal() { return jogo.getUltimaCartaGlobal(); }
    public List<Jogador> getJogadores() { return jogo.getJogadores(); }
    public int getNumeroJogadores() { return jogo.getJogadores().size(); }
    public String getJogadorNome(int indice) {
        if (indice >= 0 && indice < jogo.getJogadores().size()) {
            return jogo.getJogadores().get(indice).getNome();
        }
        return "";
    }
    public int getJogadorPosicao(int indice) {
        if (indice >= 0 && indice < jogo.getJogadores().size()) {
            return jogo.getJogadores().get(indice).getPosicao();
        }
        return 0;
    }
    public String getPropriedadeNome(int pos) { return jogo.getTabuleiro().getCasa(pos).getNome(); }
    public double getPropriedadePreco(int pos) { return jogo.getTabuleiro().getCasa(pos).getPreco(); }
    public int getPropriedadeCasas(int pos) { return jogo.getTabuleiro().getCasa(pos).getCasas(); }
    public boolean getPropriedadeTemHotel(int pos) { return jogo.getTabuleiro().getCasa(pos).temHotel(); }
    public boolean propriedadeTemDono(int pos) { return jogo.getTabuleiro().getCasa(pos).temDono(); }
    public String getPropriedadeDonoNome(int pos) {
        Propriedade p = jogo.getTabuleiro().getCasa(pos);
        if (p.temDono()) { return p.getDono().getNome(); }
        return null;
    }

    public String getUltimoEventoLog() {
        return jogo.getUltimoEventoLog();
    }
        
    public void adicionarObservador(Observer o) {
        jogo.adicionarObservador(o);
    }

    public void removerObservador(Observer o) {
        jogo.removerObservador(o);
    }
}