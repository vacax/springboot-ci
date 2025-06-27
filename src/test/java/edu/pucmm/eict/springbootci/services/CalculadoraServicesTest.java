package edu.pucmm.eict.springbootci.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculadoraServicesTest {

    CalculadoraServices calculator = new CalculadoraServices();

    @Test
    void testAdd() {
        assertEquals(5, calculator.sumar(2, 3));
    }

    @Test
    void testSubtract() {
        assertEquals(1, calculator.restar(4, 3));
    }

    @Test
    void testDivideSuccess() {
        assertEquals(2, calculator.dividir(10, 5));
    }

    @Test
    void testDivideByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.dividir(10, 0);
        });
        assertEquals("No puede ser dividido entre cero", exception.getMessage());
    }
}
