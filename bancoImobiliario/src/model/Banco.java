package model;

class Banco {
    private double saldo;

    Banco(double saldoInicial) {
        saldo = saldoInicial;
    }

    void receber(double valor) {
        saldo += valor;
    }

    void pagar(double valor) {
        saldo -= valor;
    }

    double getSaldo() {
        return saldo;
    }
}
