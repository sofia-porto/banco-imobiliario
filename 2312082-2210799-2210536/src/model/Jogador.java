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

    void pagar(double valor, Jogador beneficiario) {
        pagar(valor);
        Jogo.getInstancia().setUltimoEventoLog(
            String.format("💸 %s pagou R$%.2f para %s", this.nome, valor, beneficiario.getNome())
        );
    }
    
    void pagar(double valor, String beneficiario) { 
        pagar(valor);
        Jogo.getInstancia().setUltimoEventoLog(
            String.format("💸 %s pagou R$%.2f para o %s", this.nome, valor, beneficiario)
        );
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

    void receber(double valor, Jogador pagador) {
        receber(valor); 
        Jogo.getInstancia().setUltimoEventoLog(
            String.format("💰 %s recebeu R$%.2f de %s", this.nome, valor, pagador.getNome())
        );
    }
    
    void receber(double valor, String pagador) {
        receber(valor);
        Jogo.getInstancia().setUltimoEventoLog(
             String.format("💰 %s recebeu R$%.2f do %s", this.nome, valor, pagador)
        );
    }

    void receber(double valor) {
        saldo += valor;
    }

    void comprarPropriedade(Propriedade p) {
        if (!p.temDono() && saldo >= p.getPreco()) {
            pagar(p.getPreco());
            p.setDono(this);
            propriedades.add(p);
            Jogo.getInstancia().setUltimoEventoLog(
                String.format("🛒 %s comprou %s por R$%.2f", this.nome, p.getNome(), p.getPreco())
            );
        }
    }

    void removerPropriedade(Propriedade p) {
        if (p != null) {
            propriedades.remove(p);
        }
    }

    void prender() { 
    	if (temCartaoSaidaLivre) {
    		usarCartaoSaidaLivre();
    	} else {
    		  preso = true;
    	      posicao = 10; 
    	      duplasSeguidas = 0;
              Jogo.getInstancia().setUltimoEventoLog("🚓 " + this.nome + " foi para a Prisão!");
    	}
    }

    void registrarDupla() { 
        duplasSeguidas++;
        if (duplasSeguidas == 3) {
            prender();
        }
    }

    void resetarDuplas() { duplasSeguidas = 0; }

    void tentarSairDaPrisaoComDupla() { 
        if (preso) {
            preso = false;
            duplasSeguidas = 0;
        }
    }

    void usarCartaoSaidaLivre() { 
        if (preso && temCartaoSaidaLivre) {
            preso = false;
            temCartaoSaidaLivre = false; 
            Jogo.getInstancia().setUltimoEventoLog("🃏 " + this.nome + " usou a carta de Saída Livre da Prisão!");
        }
    }

    void receberCartaoSaidaLivre() {
        temCartaoSaidaLivre = true;
        Jogo.getInstancia().setUltimoEventoLog("🃏 " + this.nome + " recebeu uma carta de Saída Livre da Prisão!");
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
    public Piao getPiao() { return piao; }
}