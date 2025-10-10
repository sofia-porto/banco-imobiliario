package model;

import java.util.ArrayList;
import java.util.List;

class Jogador {
    private String nome;
    private int posicao;
    private double saldo;
    private boolean preso;
    private boolean falido;
    private List<Propriedade> propriedades;

    Jogador(String nome, double saldoInicial) {
        this.nome = nome;
        this.saldo = saldoInicial;
        this.posicao = 0;
        this.preso = false;
        this.falido = false;
        this.propriedades = new ArrayList<>();
    }

    void mover(int casas, int tamanhoTabuleiro) {
        posicao = (posicao + casas) % tamanhoTabuleiro;
    }

    void pagar(double valor) {
        saldo -= valor;
        if (saldo < 0) falido = true;
    }

    void receber(double valor) {
        saldo += valor;
    }

    void comprarPropriedade(Propriedade p) {
        if (!p.temDono() && saldo >= p.getPreco()) {
            pagar(p.getPreco());
            p.setDono(this);
            propriedades.add(p);
        }
    }

    void prender() { preso = true; }
    void libertar() { preso = false; }
    boolean estaPreso() { return preso; }

    boolean estaFalido() { return falido; }

    int getPosicao() { return posicao; }
    double getSaldo() { return saldo; }
    String getNome() { return nome; }

    List<Propriedade> getPropriedades() { return propriedades; }
}
