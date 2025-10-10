package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPagamentoAluguel {

    @Test
    public void testPagarAluguel() {
        Jogador dono = new Jogador("Leônidas", 4000);
        Jogador inquilino = new Jogador("Ana", 4000);
        Propriedade p = new Propriedade("Copacabana", 200, 50);

        dono.comprarPropriedade(p);
        inquilino.pagar(p.calcularAluguel());
        dono.receber(p.calcularAluguel());

        assertEquals(4050, dono.getSaldo(), 0.01);
        assertEquals(3950, inquilino.getSaldo(), 0.01);
    }
}