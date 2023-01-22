package edu.ebac.projeto.util;

public class Validador {


    public static String validaDadoNumerico(String dado) {
        return (dado == null || !dado.matches("[0-9]*")) ? "0" : dado;
    }
}
