package fes.aragon.codigo;
%%
%public
%class AnalizadorLexico
%line
%column
%char
%full
%type Token
%{
    private boolean hayToken=false;
    public boolean getHayToken(){
        return this.hayToken;
    }
%}
%init{
    /* código que se ejecuta en el constructor de la clase*/
%init}
%eof{
    /* código que se ejecuta al terminar el archivo*/
    this.hayToken=false;
%eof}
Espacio=" "
PuntoComa=";"
saltoLinea=\n|\r
num_Entero = [0-9]+
num_Real = [-|+]?[0-9]+(.[0-9]+)?
identificador = [a-zA-Z]([a-zA-Z0-9]*)

%%
"else"      {
            Token token=new Token(yytext(),Sym.ELSE,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"end"      {
            Token token=new Token(yytext(),Sym.END,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"if"      {
            Token token=new Token(yytext(),Sym.IF,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"("      {
            Token token=new Token(yytext(),Sym.PARA,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
")"      {
            Token token=new Token(yytext(),Sym.PARC,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"=="      {
            Token token=new Token(yytext(),Sym.E,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"<"      {
            Token token=new Token(yytext(),Sym.F,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
">"      {
            Token token=new Token(yytext(),Sym.G,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"<="      {
            Token token=new Token(yytext(),Sym.H,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
">="      {
            Token token=new Token(yytext(),Sym.J,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"!="      {
            Token token=new Token(yytext(),Sym.K,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
":="      {
            Token token=new Token(yytext(),Sym.P,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{num_Entero}      {
            Token token=new Token(yytext(),Sym.ENTERO,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{num_Real}      {
            Token token=new Token(yytext(),Sym.REAL,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{identificador}      {
            Token token=new Token(yytext(),Sym.REAL,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{PuntoComa} {
            Token token=new Token(yytext(),Sym.PUNTOCOMA,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{saltoLinea} {
}
{Espacio} {
}
. {
    String error="Error Léxico: " + yytext() + " linea "+
    (yyline+1)+ " columna "  + (yycolumn+1);
    System.out.println(error);
}