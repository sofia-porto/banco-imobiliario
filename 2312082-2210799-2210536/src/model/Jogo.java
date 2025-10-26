package model;

import java.util.ArrayList;
import java.util.List;

import view.TabuleiroView;

// Classe Singleton que gerencia o jogo
class Jogo {

    // ===================== SINGLETON =====================
    private static Jogo instancia; // única instância do jogo
    private Jogo() {
        jogadores = new ArrayList<>();
        tabuleiro = new Tabuleiro();
        jogadorAtual = 0;
    }

    public static Jogo getInstancia() {
        if (instancia == null) {
            instancia = new Jogo();
        }
        return instancia;
    }

    // ===================== ATRIBUTOS =====================
    private List<Jogador> jogadores;
    private Tabuleiro tabuleiro;
    private int jogadorAtual; // índice do jogador da vez
    private List<Observer> observadores = new ArrayList<>();
    private String ultimaCartaGlobal;

    // ===================== MÉTODOS DO JOGO =====================
    public void adicionarJogador(Jogador j) {
        jogadores.add(j);
        notificarObservadores();
    }

    public void iniciarPartida() {
        jogadorAtual = 0;
        notificarObservadores();
    }

    public Jogador getJogadorAtual() {
        return jogadores.get(jogadorAtual);
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void proximaJogada(int valorDado) {
        Jogador j = getJogadorAtual();
        j.mover(valorDado, tabuleiro.tamanho());
        tabuleiro.verificarCasaAtual(j);
        tabuleiro.processaAluguel(j);
        avancarJogadorDaVez();
        notificarObservadores();
    }

    private void avancarJogadorDaVez() {
        jogadorAtual = (jogadorAtual + 1) % jogadores.size();
    }
    
    void moverJogadorAtual(int casas) {
        Jogador atual = getJogadorAtual();
        if (atual != null && !atual.estaPreso()) {
            atual.mover(casas, tabuleiro.tamanho());
            tabuleiro.verificarCasaAtual(atual);
            notificarObservadores();
            avancarJogadorDaVez();
        }
    }
    
    public void setUltimaCartaGlobal(String id) {
        ultimaCartaGlobal = id;
    }

    public String getUltimaCartaGlobal() {
        return ultimaCartaGlobal;
    }

    // ===================== OBSERVER =====================
    public void adicionarObservador(Observer o) {
        observadores.add((Observer) o);
    }

    public void removerObservador(Observer o) {
        observadores.remove(o);
    }

    public void notificarObservadores() {
        for (Observer o : observadores) {
            o.atualizar();
        }
    }

    // ===================== GETTERS AUXILIARES =====================
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}

