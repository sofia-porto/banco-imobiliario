package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestConstrucaoCasa {

    @Test
    public void testConstruirCasa() {
        Propriedade p = new Propriedade("Ipanema", 200, 25);
        for (int i = 0; i < 4; i++) {
            p.construirCasa();
        }
        p.construirHotel();
        assertEquals(5 * 25, p.calcularAluguel(), 0.01); // aluguel com hotel = 5x base
    }
}
