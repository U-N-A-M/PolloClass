/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.codigo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mash
 */
public class Inicio {

    private AnalizadorLexico analizador = null;
    private Token token = null;
    private boolean errorEnLinea = false; // false -> Hay error en la linea, true -> No hay error en la linea
    private String cadena = "";

    private void siguienteToken() {
        try {
            token = analizador.yylex();
            if (token == null) {
                token = new Token("EOF", Sym.EOF, 0, 0);
                throw new IOException("Fin");
            }
        } catch (IOException ex) {
            System.out.println("Fin de Archivo");
        }
    }

    public static void main(String[] args) {
        try {
            Inicio app = new Inicio();
            BufferedReader buf;
            buf = new BufferedReader(new FileReader(System.getProperty("user.dir")
                    + "/fuente.txt"));
            app.analizador = new AnalizadorLexico(buf);
            app.siguienteToken();
            while (app.token.getLexema() != Sym.EOF) {
                try {
                    app.S();
                    if (!app.errorEnLinea) {
                        System.out.println("Correcto linea: " + app.token.getLinea() + ", columna: " + app.token.getColumna());
                    } else {
                        System.out.println("Incorrecto linea: " + app.token.getLinea() + ", columna: " + app.token.getColumna());
                    }
                    app.errorEnLinea = false;
                } catch (IOException ex) {
                    System.out.print(ex.getMessage());
                }
                System.out.println(app.token.getToken());
                app.siguienteToken();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    //Inicio del codigo del analizador sintactico
    private void S() throws IOException {
        if(token.getLexema() == Sym.IF){
            siguienteToken();
            if (token.getLexema() == Sym.PARA) {
                A();
                siguienteToken();
                if (token.getLexema() == Sym.PARC){
                    F();
                    B();
                } else{
                    System.err.println("Se esperaba un ')'");
                    errorEnLinea = true;
                }
            }else{
                System.err.println("Se esperaba un '('");
                errorEnLinea= true;
            }
        }
    }

    private void A() throws IOException {
        C();
        D();
        C();
    }

    private void B() throws IOException {
        siguienteToken();
        if (token.getLexema() == Sym.END){
            siguienteToken();
        } else if(token.getLexema() == Sym.ELSE){
            F();
            siguienteToken();
            if(token.getLexema() == Sym.END){
                siguienteToken();
            }else {
                System.err.println("Se esperaba un 'END'");
                errorEnLinea = true;
            }
        }
    }

    
    private void C() throws IOException {
        siguienteToken();
        if(token.getLexema() == Sym.ID){
            siguienteToken();
        } else if(token.getLexema() == Sym.ENTERO || token.getLexema() == Sym.REAL){
            E();
        }else{
            System.err.println("Se esperaba un 'ID' o 'Numero'");
        }
    }

    private void D() throws IOException {
        siguienteToken();
        if (token.getLexema() == Sym.E || token.getLexema() == Sym.F ||
                token.getLexema() == Sym.G || token.getLexema() == Sym.H ||
                token.getLexema() == Sym.J || token.getLexema() == Sym.K) {
            siguienteToken();
        } else {
            System.err.println("Se esperaba un operador lógico");
        }
    }
    private void E() throws IOException {
       siguienteToken();
    }
    private void F() throws IOException {
        G();
        siguienteToken();
        if(token.getLexema() == Sym.PUNTOCOMA){
            F2();
        }else {
            System.err.println("Se esperaba un ;");
        }
    }
    private void F2() throws IOException {
        F();
    }

    private void G() throws IOException {
        siguienteToken();
        if(token.getLexema() == Sym.IF){
            S();
        } else if(token.getLexema() == Sym.ID){
            H();
        }else {
            System.err.println("Se esperaba la sentencia IF");
        }
    }

    private void H() throws IOException {
        siguienteToken();
        if (token.getLexema() == Sym.P) {
            siguienteToken();
            E();
        } else {
            System.err.println("Se esperaba una asignación");
        }
    }
}