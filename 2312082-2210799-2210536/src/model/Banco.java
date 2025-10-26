package model;

class Banco {
    private double saldo;

    Banco(double saldoInicial) {
        saldo = saldoInicial;
    }

    void receber(double valor) {
        saldo += valor;
        System.out.println("🏦 Banco recebeu R$" + valor + " → saldo: R$" + saldo);
    }

    void pagar(double valor) {
        saldo -= valor;
        System.out.println("🏦 Banco pagou R$" + valor + " → saldo: R$" + saldo);
    }

    double getSaldo() {
        return saldo;
    }
}