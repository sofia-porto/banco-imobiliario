package model;

import java.util.HashSet;
import java.util.Set;

class Jogador {
    private String nome;
    private int posicao;
    private double saldo;
    private boolean preso;
    private boolean falido;
    private boolean temCartaoSaidaLivre;
    private int duplasSeguidas; // controla quantas duplas o jogador tirou
    private Set<Propriedade> propriedades;

    // Construtor
    Jogador(String nome, double saldoInicial) {
        this.nome = nome;
        this.saldo = saldoInicial;
        this.posicao = 0;
        this.preso = false;
        this.falido = false;
        this.duplasSeguidas = 0;
        this.temCartaoSaidaLivre = false;
        this.propriedades = new HashSet<>();
    }

    // ========= AÇÕES BÁSICAS =========

    void mover(int casas, int tamanhoTabuleiro) {
        if (!preso) {
            posicao = (posicao + casas) % tamanhoTabuleiro;
        }
    }

    void pagar(double valor) {
        saldo -= valor;
        if (saldo < 0) { 
        	tentarEvitarFalencia();
        	if (saldo < 0) {
            	falido = true;
            }
        }
        
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

    // ========= PRISÃO: ENTRAR =========

    // (a) Cair na casa "Vá para a Prisão"
    void prender() {
        preso = true;
        posicao = 9; // posição da casa "Prisão" conforme tabuleiro real
        duplasSeguidas = 0;
    }

    // (b) Tirar três duplas seguidas
    void registrarDupla() {
        duplasSeguidas++;
        if (duplasSeguidas == 3) {
            prender();
        }
    }

    void resetarDuplas() {
        duplasSeguidas = 0;
    }

    // ========= PRISÃO: SAIR =========

    // (c) Sair da prisão tirando uma dupla
    void tentarSairDaPrisaoComDupla() {
        if (preso) {
            preso = false;
            duplasSeguidas = 0;
        }
    }

    // (d) Sair com o cartão “Saída Livre da Prisão”
    void usarCartaoSaidaLivre() {
        if (preso && temCartaoSaidaLivre) {
            preso = false;
            temCartaoSaidaLivre = false; // consome o cartão
        }
    }

    void receberCartaoSaidaLivre() {
        temCartaoSaidaLivre = true;
    }
    
    void tentarEvitarFalencia() {
    	//TODO: Implementar na próxima iteração
    }

    // ========= STATUS DO JOGADOR =========

    boolean estaPreso() { return preso; }
    boolean estaFalido() { return falido; }
    boolean temCartaoSaidaLivre() { return temCartaoSaidaLivre; }

    int getPosicao() { return posicao; }
    double getSaldo() { return saldo; }
    String getNome() { return nome; }

    Set<Propriedade> getPropriedades() { return propriedades; }
}