package model;

class Piao {
    private String cor;
    private int posicao;

    Piao(String cor) {
        this.cor = cor;
        this.posicao = 0;
    }

    String getCor() { return cor; }
    int getPosicao() { return posicao; }

    void mover(int casas, int tamanhoTabuleiro) {
        posicao = (posicao + casas) % tamanhoTabuleiro;
    }

    void setPosicao(int posicao) {
        this.posicao = posicao;
    }
}
