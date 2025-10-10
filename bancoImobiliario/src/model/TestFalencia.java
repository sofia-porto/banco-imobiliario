package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestFalencia {

    @Test
    public void testFalencia() {
        Jogador j = new Jogador("Ana", 100);
        j.pagar(200);
        assertTrue(j.estaFalido());
    }
}