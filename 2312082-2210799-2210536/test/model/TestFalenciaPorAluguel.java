package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestFalenciaPorAluguel {

    @Test
    public void testFalirAoPagarAluguel() {
        // Jogador dono com saldo normal
        Jogador dono = new Jogador("Leônidas", 4000, null);
        // Inquilino com pouco saldo
        Jogador inquilino = new Jogador("Ana", 50, null);
        Propriedade p = new Propriedade("Copacabana", 200, 100);

        // Dono compra a propriedade
        dono.comprarPropriedade(p);

        // Inquilino tenta pagar o aluguel, mas não tem saldo suficiente
        inquilino.pagar(p.calcularAluguel());

        // Verifica que o inquilino ficou falido
        assertTrue(inquilino.estaFalido());
        // E que o dono recebeu normalmente o valor (independente da falência)
        dono.receber(p.calcularAluguel());
        assertEquals(3900, dono.getSaldo(), 0.01); // 4000 - 200 (compra) + 100 (aluguel)
    }
}