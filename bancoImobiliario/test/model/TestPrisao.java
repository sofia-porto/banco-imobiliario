package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestPrisao {

    // (a) Jogador cai na casa "Vá para a Prisão"
    @Test
    public void testCairNaCasaVaParaPrisao() {
        Tabuleiro tabuleiro = new Tabuleiro();
        Jogador j = new Jogador("Ana", 4000);

        // Simula o movimento até a casa "Vá para a Prisão"
        // (posição conforme planilha: índice 25)
        j.mover(25, tabuleiro.tamanho());
        tabuleiro.verificarCasaAtual(j);
        
        assertTrue(j.estaPreso());
        assertEquals(8, j.getPosicao()); // enviado para a casa "Prisão"
    }

    // (b) Jogador tira 3 duplas seguidas
    @Test
    public void testTresDuplasSeguidasVaiParaPrisao() {
        Jogador j = new Jogador("Ana", 4000);

        // Simula tirar três duplas seguidas
        j.registrarDupla();
        j.registrarDupla();
        j.registrarDupla(); // deve prender

        assertTrue(j.estaPreso());
        assertEquals(8, j.getPosicao());
    }

    // (c) Jogador sai da prisão tirando uma dupla
    @Test
    public void testSairDaPrisaoComDupla() {
        Jogador j = new Jogador("Ana", 4000);
        j.prender(); // começa preso

        j.tentarSairDaPrisaoComDupla();

        assertFalse(j.estaPreso());
    }

    // (d) Jogador sai da prisão usando o cartão "Saída Livre da Prisão"
    @Test
    public void testSairDaPrisaoComCartao() {
        Jogador j = new Jogador("Ana", 4000);
        j.prender(); // está preso
        j.receberCartaoSaidaLivre(); // recebe o cartão
        j.usarCartaoSaidaLivre(); // usa o cartão

        assertFalse(j.estaPreso());
        assertFalse(j.temCartaoSaidaLivre());
    }
}