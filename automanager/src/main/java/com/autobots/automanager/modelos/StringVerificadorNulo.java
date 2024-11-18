package com.autobots.automanager.modelos;

public class StringVerificadorNulo {
    public boolean verificar(String valor) {
        boolean nulo = true;
        if (!(valor == null)) {
            if (!valor.isBlank()) {
                nulo = false;
            }
        }
        return nulo;
    }
}