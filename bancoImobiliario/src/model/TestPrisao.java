package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPrisao {

    @Test
    public void testEntrarESairDaPrisao() {
        Jogador j = new Jogador("Ana", 4000);
        j.prender();
        assertTrue(j.estaPreso());
        j.libertar();
        assertFalse(j.estaPreso());
    }
}