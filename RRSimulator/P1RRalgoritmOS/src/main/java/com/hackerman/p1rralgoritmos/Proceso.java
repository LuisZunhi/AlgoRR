package com.hackerman.p1rralgoritmos;


public class Proceso {

    //ATRIBUTOS
    int id; //Id del proceso (numérico)
    String name; //nombre del proceso (alfanumérico)
    int tam; //Tamaño del proceso
    int serv_time; //Tiempo que requiere el proceso para su ejecución
    int prioridad; //Prioridad del proceso
    int tiempoLlegada; //Tiempo de llegada del proceso
    private Proceso siguiente; //Apuntador hacia el siguiente Proceso

    //Variables que ocupa cpu
    int tiempoFaltante;
    int tiempoTotal;
    boolean first_run;
    int tiempoEntrada;

    //CONSTRUCTORES
    Proceso() {
        //NODOS INVOLUCRADOS
        siguiente = null;
    }

    Proceso(int id, String name, int tam, int tiempoLlegada, int serv_time, int prioridad) {
        //NODOS INVOLUCRADOS
        siguiente = null;
        //Inicializacion de atributos
        this.id = id;
        this.name = name;
        this.tam = tam;
        this.serv_time = serv_time;
        this.prioridad = prioridad;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoFaltante = serv_time;
        this.tiempoTotal = tiempoLlegada;
        this.first_run = true;
        this.tiempoEntrada = 0;
    }

    Proceso(int id, String name, int tam, int tiempoLlegada, int serv_time) {
        //NODOS INVOLUCRADOS
        siguiente = null;
        //Inicializacion de atributos
        this.id = id;
        this.name = name;
        this.tam = tam;
        this.serv_time = serv_time;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoFaltante = serv_time;
        this.tiempoTotal = tiempoLlegada;
        this.first_run = true;
        this.tiempoEntrada = 0;
    }
    
    //MÉTODOS
    public void setSiguiente(Proceso siguiente) {
        this.siguiente = siguiente;
    }

    public Proceso getSiguiente() {
        return this.siguiente;
    }
}
