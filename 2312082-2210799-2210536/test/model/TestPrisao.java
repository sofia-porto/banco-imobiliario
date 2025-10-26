package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestPrisao {

    @Test // Jogador cai na casa "Vá para a Prisão"
    public void testCairNaCasaVaParaPrisao() {
        Tabuleiro tabuleiro = new Tabuleiro();
        Jogador j = new Jogador("Ana", 4000, null);

        j.mover(25, tabuleiro.tamanho());
        tabuleiro.verificarCasaAtual(j);
        
        assertTrue(j.estaPreso());
        assertEquals(9, j.getPosicao());
    }

    @Test // Jogador tira 3 duplas seguidas
    public void testTresDuplasSeguidasVaiParaPrisao() {
        Jogador j = new Jogador("Ana", 4000, null);

        j.registrarDupla();
        j.registrarDupla();
        j.registrarDupla(); 

        assertTrue(j.estaPreso());
        assertEquals(9, j.getPosicao());
    }

    @Test //Jogador sai da prisão tirando uma dupla
    public void testSairDaPrisaoComDupla() {
        Jogador j = new Jogador("Ana", 4000, null);
        j.prender(); 

        j.tentarSairDaPrisaoComDupla();

        assertFalse(j.estaPreso());
    }

    @Test // Jogador sai da prisão usando o cartão "Saída Livre da Prisão"
    public void testSairDaPrisaoComCartao() {
        Jogador j = new Jogador("Ana", 4000, null);
        j.prender(); 
        j.receberCartaoSaidaLivre();
        j.usarCartaoSaidaLivre();

        assertFalse(j.estaPreso());
        assertFalse(j.temCartaoSaidaLivre());
    }
}

