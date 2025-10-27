package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Jogo {
    private static Jogo instancia;
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

    private List<Jogador> jogadores;
    private Tabuleiro tabuleiro;
    private int jogadorAtual; 
    private List<Observer> observadores = new ArrayList<>();
    private String ultimaCartaGlobal;

    public void adicionarJogador(Jogador j) {
        jogadores.add(j);
        notificarObservadores();
    }

    public void iniciarPartida() {
        sortearOrdemJogadores();
        jogadorAtual = 0;
        notificarObservadores();
        System.out.println("🎯 Ordem de jogo definida:");
        for (int i = 0; i < jogadores.size(); i++) {
            System.out.println("  " + (i+1) + "º: " + jogadores.get(i).getNome());
        }
    }
    
    private void sortearOrdemJogadores() {
        if (jogadores.size() <= 1) return;
        Collections.shuffle(jogadores, new Random());
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
    
    public void limparCartaGlobal() {
        ultimaCartaGlobal = null;
    }

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

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
    
    void construirCasa() {
        Jogador jogador = getJogadorAtual();
        Propriedade prop = tabuleiro.getCasa(jogador.getPosicao());

        if (prop != null && prop.getDono() == jogador) {
            double custo = prop.getPreco() * 0.5;
            if (jogador.getSaldo() >= custo) {
                if (prop.getCasas() < 4 && !prop.temHotel()) {
                    jogador.pagar(custo);
                    prop.construirCasa();
                    System.out.println(jogador.getNome() + " construiu uma casa em " + prop.getNome() +
                            " → total de casas: " + prop.getCasas() +
                            " → saldo: R$" + jogador.getSaldo());
                } else {
                    System.out.println(prop.getNome() + " já possui o máximo de casas ou um hotel.");
                }
            } else {
                System.out.println(jogador.getNome() + " não tem saldo para construir em " + prop.getNome());
            }
        } else {
            System.out.println("Só é possível construir em uma propriedade que pertença ao jogador da vez.");
        }
        notificarObservadores();
    }

    void construirHotel() {
        Jogador jogador = getJogadorAtual();
        Propriedade prop = tabuleiro.getCasa(jogador.getPosicao());

        if (prop != null && prop.getDono() == jogador) {
            double custo = prop.getPreco();
            if (jogador.getSaldo() >= custo) {
                if (prop.getCasas() >= 4 && !prop.temHotel()) {
                    jogador.pagar(custo);
                    prop.construirHotel();
                    System.out.println(jogador.getNome() + " construiu um HOTEL em " + prop.getNome() +
                            " → saldo: R$" + jogador.getSaldo());
                } else {
                    System.out.println(prop.getNome() + " ainda não tem 4 casas ou já possui um hotel.");
                }
            } else {
                System.out.println(jogador.getNome() + " não tem saldo para construir um hotel em " + prop.getNome());
            }
        } else {
            System.out.println("Só é possível construir em uma propriedade que pertença ao jogador da vez.");
        }
        notificarObservadores();
    }

    void venderPropriedade() {
        Jogador jogador = getJogadorAtual();
        Propriedade prop = tabuleiro.getCasa(jogador.getPosicao());

        if (prop != null && prop.getDono() == jogador) {
            double valorVenda = prop.getPreco() * 0.8;
            jogador.receber(valorVenda);
            prop.setDono(null);
            jogador.getPropriedades().remove(prop);
            System.out.println(jogador.getNome() + " vendeu " + prop.getNome() +
                    " por R$" + valorVenda + " → novo saldo: R$" + jogador.getSaldo());
        } else {
            System.out.println(jogador.getNome() + " não é dono da propriedade atual ou ela não é vendável.");
        }
        notificarObservadores();
    }
}