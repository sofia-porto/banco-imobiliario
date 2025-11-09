package controller;

import model.JogoFacade;

public class JogoController {
    
    private JogoFacade jogo;
    
    public JogoController(JogoFacade jogo) {
        this.jogo = jogo;
    }

    public void realizarJogada(int valor1, int valor2) {
        if (jogo.getNumeroJogadores() == 0) return;
        int soma = valor1 + valor2;
        System.out.println("🎲 Controller: Movendo jogador com " + valor1 + " + " + valor2 + " = " + soma);
        jogo.moverJogadorAtual(soma);
    }
    
    public int[] lancarDadosRandom() {
        return jogo.lancarDados();
    }

    public void setProximosDados(int dado1, int dado2) {
        if (dado1 >= 1 && dado1 <= 6 && dado2 >= 1 && dado2 <= 6) {
            jogo.setProximosDados(dado1, dado2);
        } else {
            System.err.println("⚠️ Valores de dados inválidos. Use 1 a 6.");
        }
    }

    public void comprarPropriedadeAtual() {
        jogo.comprarPropriedadeAtual();
    }
    
    public void construirCasa(String nomePropriedade) {
        if (nomePropriedade == null || nomePropriedade.equals("Selecione uma propriedade")) {
            System.err.println("Selecione uma propriedade válida para construir.");
            return;
        }
        jogo.construirCasa(nomePropriedade);
    }
    
    public void construirHotel(String nomePropriedade) {
        if (nomePropriedade == null || nomePropriedade.equals("Selecione uma propriedade")) {
            System.err.println("Selecione uma propriedade válida para construir.");
            return;
        }
        jogo.construirHotel(nomePropriedade);
    }
    
    public void venderPropriedade(String nomePropriedade) {
        if (nomePropriedade == null || nomePropriedade.equals("Selecione uma propriedade")) {
            System.err.println("Selecione uma propriedade válida para vender.");
            return;
        }
        jogo.venderPropriedade(nomePropriedade);
    }
    
    public void encerrarTurnoEChamarProximo() {
        jogo.encerrarTurno();
    }
}