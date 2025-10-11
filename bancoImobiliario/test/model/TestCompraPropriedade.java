package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCompraPropriedade {

    @Test
    public void testComprarPropriedade() {
        Jogador j = new Jogador("Ana", 4000);
        Propriedade p = new Propriedade("Leblon", 300, 30);
        assertFalse(p.temDono());
        j.comprarPropriedade(p);
        assertTrue(p.temDono());
        assertEquals(j, p.getDono());
        assertEquals(3700, j.getSaldo(), 0.01);
    }
}
