package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLancamentoDados {

    @Test
    public void testLancarDoisDados() {
        Dado dado = new Dado();
        int[] resultado = dado.lancarDoisDados();
        assertEquals(2, resultado.length);
        assertTrue(resultado[0] >= 1 && resultado[0] <= 6);
        assertTrue(resultado[1] >= 1 && resultado[1] <= 6);
    }
}