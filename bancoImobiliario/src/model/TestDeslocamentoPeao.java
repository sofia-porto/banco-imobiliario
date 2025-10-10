package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDeslocamentoPeao {

    @Test
    public void testMoverPeao() {
        Jogador j = new Jogador("Ana", 4000);
        j.mover(5, 10);
        assertEquals(5, j.getPosicao());
        j.mover(7, 10);
        assertEquals(2, j.getPosicao()); // (5+7)%10 = 2
    }
}
