package com.example.appecolim.clasificacion;

import org.junit.Test;
import static org.junit.Assert.*;

public class CantidadTest {
    @Test public void aceptaSeparadoresDecimalesYConservaPrecision() {
        assertEquals(1250, Cantidad.leer("12.50"));
        assertEquals(1250, Cantidad.leer("12,50"));
        assertEquals(1, Cantidad.leer("0.01"));
        assertEquals("12.50", Cantidad.mostrar(1250));
    }
    @Test public void rechazaCantidadesInvalidasSinRedondearlas() {
        for (String texto : new String[]{"", "-1", "NaN", "Infinity", "0.001", "1,2.3", "1000000", "99999999999999999999"}) {
            assertEquals(texto, -1, Cantidad.leer(texto));
        }
    }
    @Test public void conservaCentavosEnConversionDeIdaYVuelta() {
        for (int cantidad : new int[]{0, 1, 10, 50, 1250, 99999, Cantidad.MAXIMO}) {
            assertEquals(cantidad, Cantidad.leer(Cantidad.mostrar(cantidad)));
        }
    }
}
