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
    private int duplasSeguidas;
    private Set<Propriedade> propriedades;
    private Piao piao;
    private String ultimaCartaId;

    Jogador(String nome, double saldoInicial, String corPiao) {
        this.nome = nome;
        this.saldo = saldoInicial;
        this.posicao = 0;
        this.preso = false;
        this.falido = false;
        this.duplasSeguidas = 0;
        this.temCartaoSaidaLivre = false;
        this.propriedades = new HashSet<>();
        this.piao = new Piao(corPiao);
    }

    void mover(int casas, int tamanhoTabuleiro) {
        if (!preso) {
            piao.mover(casas, tamanhoTabuleiro);
            posicao = piao.getPosicao();
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
        System.out.println("💰 Jogador pagou R$" + valor + " → saldo: R$" + saldo);
    }

    void receber(double valor) {
        saldo += valor;
        System.out.println("💰 Jogador recebeu R$" + valor + " → saldo: R$" + saldo);
    }

    void comprarPropriedade(Propriedade p) {
        if (!p.temDono() && saldo >= p.getPreco()) {
            pagar(p.getPreco());
            p.setDono(this);
            propriedades.add(p);
            System.out.println(" Jogador comprou a propriedade " + p.getNome() + " → saldo: R$" + saldo);
        }
    }

    void prender() { // Cair na casa "Vá para a Prisão"
    	if (temCartaoSaidaLivre) {
    		usarCartaoSaidaLivre();
    	} else {
    		  preso = true;
    	      posicao = 10; // posição da casa "Prisão" conforme tabuleiro real
    	      duplasSeguidas = 0;
    	}
    }

    void registrarDupla() { // Tirar três duplas seguidas
        duplasSeguidas++;
        if (duplasSeguidas == 3) {
            prender();
        }
    }

    void resetarDuplas() {
        duplasSeguidas = 0;
    }

    void tentarSairDaPrisaoComDupla() { // Sair da prisão tirando uma dupla
        if (preso) {
            preso = false;
            duplasSeguidas = 0;
        }
    }

    void usarCartaoSaidaLivre() { // Sair com o cartão “Saída Livre da Prisão”
        if (preso && temCartaoSaidaLivre) {
            preso = false;
            temCartaoSaidaLivre = false; // consome o cartão
            System.out.println("🃏 Você usou sua carta de Saída da Prisão!");
        }
    }

    void receberCartaoSaidaLivre() {
        temCartaoSaidaLivre = true;
        System.out.println("🃏 Você recebeu uma carta de Saída da Prisão!");
    }
    
    void tentarEvitarFalencia() {
    	//TODO: Implementar na próxima iteração
    }

    boolean estaPreso() { return preso; }
    boolean estaFalido() { return falido; }
    boolean temCartaoSaidaLivre() { return temCartaoSaidaLivre; }

    int getPosicao() { return posicao; }
    double getSaldo() { return saldo; }
    String getNome() { return nome; }

    Set<Propriedade> getPropriedades() { return propriedades; }
    
    public void setUltimaCarta(String id) { this.ultimaCartaId = id; }
    public String getUltimaCarta() { return ultimaCartaId; }

    public Piao getPiao() {
        return piao;
    }

}