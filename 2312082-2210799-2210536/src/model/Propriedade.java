package model;

class Propriedade {
    private String nome;
    private double preco;
    private double aluguelBase;
    private Jogador dono;
    private int casas;
    private boolean hotel;

    Propriedade(String nome, double preco, double aluguelBase) {
        this.nome = nome;
        this.preco = preco;
        this.aluguelBase = aluguelBase;
        this.dono = null;
        this.casas = 0;
        this.hotel = false;
    }

    boolean temDono() { return dono != null; }
    Jogador getDono() { return dono; }
    void setDono(Jogador j) { dono = j; }

    double getPreco() { return preco; }

    int getCasas() { return casas; }

    double calcularAluguel() {
        if (hotel) return aluguelBase * 5;
        return aluguelBase * (1 + casas);
    }

    void construirCasa() {
        if (casas < 4) casas++;
    }

    void construirHotel() {
        if (casas >= 1 && !hotel) hotel = true;
    }

    String getNome() { return nome; }
    boolean temHotel() { return hotel; }
}