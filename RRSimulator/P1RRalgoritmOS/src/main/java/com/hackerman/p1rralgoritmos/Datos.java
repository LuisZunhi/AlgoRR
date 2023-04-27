
package com.hackerman.p1rralgoritmos;


import java.util.Scanner;
 
public class Datos {
        //ATRIBUTOS
        int numProcesos; //Total de procesos
        int maxTamProceso; //Tamaño máximo de memoria que puede tener un proceso: Entero en kBytes
        int memoriaRAM; //Tamaño de la memoria RAM: Entero en kBytes
        int quantum;
        Cola colaProcesos;
        Proceso nodo;
        private final Scanner sc;
        private final Proceso procesosDesordenados[]; 
        
        //CONSTRUCTOR
        /*
            En este constructor se define el número de procesos a crear (numProcesos),
            el tamaño de memoria máximo para un proceso (maxTamProceso) y tamaño de memoria RAM (memoria).
            Se crea un objeto Cola (cola) y se inicializa creando un objeto Proceso (nodo).
            La cola está vacía hasta que se ejecuta la instrucción procesos.hacerProcesos().
            Al terminar, la cola ya tiene a los procesos listos.
        */
        public Datos() {
        maxTamProceso = 50; //Se define un tamaño de memoria máximo por default para un proceso
        memoriaRAM = 1024; //Se define un tamaño de memoria RAM por default
        quantum = 4000; //Se define un tiempo de quantum por default
        sc = new Scanner(System.in);
            
        System.out.println("Valor por default del tamanio maximo de memoria que tendra un proceso: " + maxTamProceso + "[k]");
        System.out.println("Valor por default del tamanio de memoria RAM: " + memoriaRAM + "[k]");
        System.out.println("Valor por default del quantum: " + quantum + "[mseg]");
        System.out.println("Redefinir valores s/n: ");
        String respuesta = sc.next();

        if (respuesta.contains("s")) {
            System.out.println("Ingresar el nuevo valor para tamanio maximo de memoria que tendra un proceso: ");
            maxTamProceso = sc.nextInt();
            System.out.println("Ingresar el nuevo valor para tamanio de memoria RAM: ");
            memoriaRAM = sc.nextInt();
            System.out.println("Ingresar el nuevo valor para quantum: ");
            quantum = sc.nextInt();

            System.out.println("Valor nuevo del tamanio maximo de memoria que tendra un proceso: " + maxTamProceso + "[k]");
            System.out.println("Valor nuevo del tamanio de memoria RAM: " + memoriaRAM + "[k]");
            System.out.println("Valor nuevo del quantum: " + quantum + "[mseg]\n");
        }

        //Aqui creará la cola de procesos ingresados
        System.out.println("Ingresar la cantidad de procesos: ");
        numProcesos = sc.nextInt();

        colaProcesos = new Cola("Cola de procesos cargados"); //Instancia e inicializa la colaProcesos
        int horaLlegada = 0;
        int tiempoEjecucion = 0;
        int prioridad = 0;
        int indiceNormal = 0;
        procesosDesordenados = new Proceso[numProcesos];//Arreglo temporal para ordenar los procesos por hora de llegada

        for (int i = 0; i < numProcesos; i++) { //ciclo que solicita datos y guarda los procesos en procesosDesordenados
            indiceNormal = i + 1;

            System.out.println("Ingresa el tiempo de llegada del proceso " + indiceNormal + " en [ms]: ");
            horaLlegada = sc.nextInt();
            System.out.println("Ingresa el tiempo de servicio del proceso " + indiceNormal + " en [ms]: ");
            tiempoEjecucion = sc.nextInt();
            System.out.println("Ingresa la prioridad del proceso " + indiceNormal + ": ");
            prioridad = sc.nextInt();

            procesosDesordenados[i] = new Proceso(indiceNormal, "P" + indiceNormal, (int) (Math.random() * maxTamProceso) + 1, horaLlegada, tiempoEjecucion, prioridad);
        }

        ordenarProcesos();

        for (int i = 0; i < numProcesos; i++) { //ciclo que llena la colaProcesos con los procesos ordenados
            colaProcesos.insertar(procesosDesordenados[i]);
        }

        System.out.println("Procesos que se cargaron en el simulador:");
        colaProcesos.imprimirColaCompleta();
    }

    //METODOS
    private void ordenarProcesos() {
        //Algoritmo de ordenamiento bubble sort
        for (int x = 0; x < procesosDesordenados.length; x++) {
            for (int i = 0; i < procesosDesordenados.length - x - 1; i++) {
                if (procesosDesordenados[i].tiempoLlegada > procesosDesordenados[i + 1].tiempoLlegada) {
                    Proceso tmp = procesosDesordenados[i + 1];
                    procesosDesordenados[i + 1] = procesosDesordenados[i];
                    procesosDesordenados[i] = tmp;
                }
            }
        }
    }
}
