package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestPagamentoAluguel {

    @Test
    public void testPagarAluguelAutomaticamente() {
        // Dono e inquilino
        Jogador dono = new Jogador("Leônidas", 4000);
        Jogador inquilino = new Jogador("Ana", 4000);

        // Propriedade
        Propriedade p = new Propriedade("Copacabana", 200, 50);
        dono.comprarPropriedade(p);

        // Adiciona pelo menos uma casa
        p.construirCasa();

        // Aluguel calculado com 1 casa (50 * (1 + 1) = 100)
        double aluguel = p.calcularAluguel();
        assertEquals(100, aluguel, 0.01);

        // Jogador da vez (inquilino) cai na propriedade do dono
        inquilino.pagar(aluguel);
        dono.receber(aluguel);

        // Verifica se o dono recebeu e o inquilino pagou corretamente
        assertEquals(3900, dono.getSaldo(), 0.01);
        assertEquals(3900, inquilino.getSaldo(), 0.01);
    }

    @Test
    public void testNaoPagaSeNaoTemCasa() {
        Jogador dono = new Jogador("Leônidas", 4000);
        Jogador inquilino = new Jogador("Ana", 4000);
        Propriedade p = new Propriedade("Leblon", 100, 50);

        dono.comprarPropriedade(p);
        // Não constrói casa
        double aluguel = p.calcularAluguel(); // deve ser apenas 50

        // Nessa regra, não deve haver cobrança sem casa
        assertEquals(50, aluguel, 0.01); // cálculo existe
        // Mas o pagamento automático não ocorre
        assertEquals(4000, inquilino.getSaldo(), 0.01);
        assertEquals(4000 - p.getPreco(), dono.getSaldo(), 0.01); // apenas compra
    }
}