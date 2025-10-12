package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestPagamentoAluguel {

    @Test
    public void testPagamentoAutomaticoComCasa() {
        Jogador dono = new Jogador("Leônidas", 4000);
        Jogador inquilino = new Jogador("Ana", 4000);
        Tabuleiro tabuleiro = new Tabuleiro();

        Propriedade p = tabuleiro.getCasa(1);
        dono.comprarPropriedade(p);
        p.construirCasa();

        // Inquilino cai na casa
        inquilino.mover(1, tabuleiro.tamanho());
        tabuleiro.processaAluguel(inquilino);

        assertEquals(4000 - p.calcularAluguel(), inquilino.getSaldo(), 0.01);
        assertEquals(4000 - p.getPreco() + p.calcularAluguel(), dono.getSaldo(), 0.01); 
    }

    @Test
    public void testNaoPagaSeNaoTemCasa() {
        Jogador dono = new Jogador("Leônidas", 4000);
        Jogador inquilino = new Jogador("Ana", 4000);
        Tabuleiro tabuleiro = new Tabuleiro();

        Propriedade p = tabuleiro.getCasa(1);
        dono.comprarPropriedade(p);

        inquilino.mover(1, tabuleiro.tamanho());
        tabuleiro.processaAluguel(inquilino);

        // Sem casa → não deve ocorrer pagamento
        assertEquals(4000, inquilino.getSaldo(), 0.01);
        assertEquals(4000 - p.getPreco(), dono.getSaldo(), 0.01);
    }
}
