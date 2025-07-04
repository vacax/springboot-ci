package edu.pucmm.eict.springbootci.services;

import org.springframework.stereotype.Service;

@Service
public class CalculadoraServices {

    public int sumar(int a, int b) {
        return a + b+1;
    }

    public int restar(int a, int b) {
        return a - b;
    }

    public int dividir(int a, int b) {
        if (b == 0) throw new IllegalArgumentException("No puede ser dividido entre cero");
        return a / b;
    }

}
