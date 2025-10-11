package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestConstrucaoCasa {

    @Test
    public void testConstruirCasaIncrementaCorretamente() {
        Propriedade p = new Propriedade("Leblon", 100, 10);

        // Nenhuma casa no início
        assertEquals(0, p.getCasas());

        // Constrói uma casa
        p.construirCasa();
        assertEquals(1, p.getCasas());

        // Constrói até o limite
        p.construirCasa();
        p.construirCasa();
        p.construirCasa();
        assertEquals(4, p.getCasas());

        // Tenta construir além do limite
        p.construirCasa();
        assertEquals(4, p.getCasas()); // não ultrapassa
    }

    @Test
    public void testNaoConstruiMaisQueQuatro() {
        Propriedade p = new Propriedade("Copacabana", 200, 20);

        // Constrói 4 casas
        for (int i = 0; i < 4; i++) p.construirCasa();

        // Tenta construir mais uma
        p.construirCasa();

        // Continua 4
        assertEquals(4, p.getCasas());
    }
}