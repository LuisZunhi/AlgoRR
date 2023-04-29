package com.hackerman.p1rralgoritmos;


/*
 * Esta clase se encarga de agregar los procesos a la cola de procesos listos
 * conforme a su tiempo de llegada.
 */
public class AdminProcesosListos extends Thread {

    //Atributos
    private final Cola colaProcesosCargados;
    private final Cola ready_q;
    private Proceso procesoTemp;
    private Proceso procesoTempCerrojo;
    private int tiempoTranscurrido;
    private boolean disponible;
    boolean subieronTodos;
    int memoriaRAM;
    boolean hayProcesoEsperandoRAM;
    boolean ramLista;

    //Constructores
    public AdminProcesosListos(Cola procesosCargados, Cola ready_proces, int RAM) {
        this.setName("HiloAdminProcesosListos");
        this.colaProcesosCargados = procesosCargados;
        this.ready_q = ready_proces;
        this.tiempoTranscurrido = 0;
        this.disponible = true;
        this.subieronTodos = false;
        this.memoriaRAM = RAM;
        this.hayProcesoEsperandoRAM = false;
        this.ramLista = false;
    }

    //Metodos
    @Override
    public void run() {
        int start_cont = colaProcesosCargados.getCantidad(); //cantidad fija
        for (int i = 0; i < start_cont; i++) {
            procesoTemp = colaProcesosCargados.desencolar();

            dormir(procesoTemp.tiempoLlegada - tiempoTranscurrido); // duerme lo necesario para que el proceso se insete en su tiempo de llegada           
            tiempoTranscurrido = tiempoTranscurrido + (procesoTemp.tiempoLlegada - tiempoTranscurrido); // Actualiza el tiempo que ha transcurrido en total

            if (memoriaRAM - procesoTemp.tam >= 0) {
                encolarProcesoListo(procesoTemp);
                System.out.println("Llega el proceso " + procesoTemp.name + " en el tiempo " + procesoTemp.tiempoLlegada + " [ms], tamanio "
                        + procesoTemp.tam + " [k]");
            } else {
                hayProcesoEsperandoRAM = true;
                esperarRAM();
                encolarProcesoListo(procesoTemp);
                System.out.println("Llega el proceso " + procesoTemp.name + " con retraso al esperar espacio en RAM, tamanio "
                        + procesoTemp.tam + " [k]");
            }
        }
        subieronTodos = true;
    }

    private void dormir(int tiempo) {
        try {
            sleep(tiempo);
        } catch (InterruptedException ex) {
            System.out.println("Error al dormir");
        }
    }

    public synchronized void encolarProcesoListo(Proceso procesoListo) {
        while (disponible == false) {
            //Se mantiene en este while cuando otro hilo está ocupando este metodo.
            try {
                wait(); //se pone a dormir y cede el monitor
            } catch (InterruptedException e) {
            }
        }
        //Entra aqui cuando otro hilo ha dejado de ocupar este metodo
        disponible = false;//Cierra el cerrojo para que otro hilo no ocupe el metodo
        ready_q.insertar(procesoListo);
        ready_q.imprimirColaCompleta();
        disponible = true;//Abre el cerrojo cuando termina
        notifyAll();
    }

    public synchronized Proceso desencolarProcesoListo() {
        while (disponible == false) {
            //Se mantiene en este while cuando otro hilo está ocupando este metodo.
            try {
                wait(); //se pone a dormir y cede el monitor
            } catch (InterruptedException e) {
            }
        }
        //Entra aqui cuando otro hilo ha dejado de ocupar este metodo
        disponible = false;//Cierra el cerrojo para que otro hilo no ocupe el metodo
        procesoTempCerrojo = ready_q.desencolar();

        disponible = true;//Abre el cerrojo cuando termina
        notifyAll();
        return procesoTempCerrojo;
    }
    
    public synchronized void esperarRAM() { // Esperara hasta que CPU baje un proceso
        while (ramLista == false) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        notifyAll();
    }


}

