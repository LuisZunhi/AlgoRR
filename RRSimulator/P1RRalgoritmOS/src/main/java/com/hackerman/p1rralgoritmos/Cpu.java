package com.hackerman.p1rralgoritmos;



import static java.lang.Thread.sleep;

/*
 * Esta clase se encarga de ejecutar los procesos que le llegan en la cola
 * de procesos listos, los encola de nuevo si necesesita mas tiempo del quantum.
 */
public class Cpu extends Thread {

    //Atributos
    private final int quantum;
    private final AdminProcesosListos ready_proces;
    private Proceso procesoTemp;
    private int time_t;
    private boolean first_run;
    private Cola finished_q;

    //Constructores
    public Cpu(int quantum, AdminProcesosListos ready_proces, Cola finished_q) {
        this.setName("CPU");
        this.quantum = quantum;
        this.ready_proces = ready_proces;
        this.time_t = 0;
        this.first_run = true;
        this.finished_q = finished_q;
    }

    //Metodos
    @Override
    public void run() {
        while (true) {// Ciclo infinito, se corta hasta que la simulacion finaliza
            procesoTemp = ready_proces.desencolarProcesoListo();

            if (ready_proces.all_uploaded && procesoTemp == null) {
                //Acciones cuando se han subido todos los procesos y ya no quedan mas en la lista de procesos listos
                showCpuMess("Terminaron de ejecutarse TODOS los procesos");
                break;
            } else {
                //Acciones cuando aun faltan procesos a ejecutar   
                if (procesoTemp != null) {

                    if (first_run) {
                        //Acciones cuando se itera por primera vez
                    	time_t = procesoTemp.tiempoLlegada;
                        first_run = false;
                    }

                    if (procesoTemp.first_run) {
                        //Acciones cuando un proceso entra a CPU por primera vez
                        procesoTemp.tiempoEntrada = time_t;
                        procesoTemp.first_run = false;
                        ready_proces.memoRAM = ready_proces.memoRAM - procesoTemp.tam;
                        showCpuMess("Se cargó el proceso " + procesoTemp.name + " en memoria RAM. Memoria RAM disponible " + ready_proces.memoRAM + "[k]");
                    }

                    showCpuMess("Proceso " + procesoTemp.name + " subio a CPU en el tiempo " + time_t + " [ms], tiempo faltante de ejecucion "
                            + procesoTemp.tiempoFaltante + " [ms]");

                    if (procesoTemp.tiempoFaltante > quantum) {
                        //Caso en el que el proceso necesita mas tiempo en CPU que el quantum, por lo que tiene que repetir
                        dormir(quantum);

                        procesoTemp.tiempoFaltante -= quantum;// Se le resta el tiempo que ya se ejecutó
                        showCpuMess("Proceso " + procesoTemp.name + " entra de nuevo a la cola de procesos listos en el tiempo " + time_t + " [ms]");
                        ready_proces.encolarProcesoListo(procesoTemp);

                    } else {
                        //Caso en el que el proceso necesita menos tiempo que el quantum, ya no necesita regresar
                        dormir(procesoTemp.tiempoFaltante);

                        procesoTemp.tiempoFaltante = 0;
                        procesoTemp.tiempoTotal = time_t;
                        finished_q.insertar(procesoTemp);

                        showCpuMess("Proceso " + procesoTemp.name + " termino su ejecucion en el tiempo " + time_t + " [ms]");
                        ready_proces.memoRAM = ready_proces.memoRAM + procesoTemp.tam;
                        showCpuMess("Se liberó memoria RAM. Memoria RAM disponible " + ready_proces.memoRAM + "[k]");

                        if (ready_proces.proces_waiting) {
                        	ready_proces.ram_ready = true;
                        	ready_proces.esperarRAM();
                        }
                    }
                }
            }
        }
    }

    private void dormir(int tiempo) {
        try {
            sleep(tiempo);
            time_t += tiempo;
        } catch (InterruptedException ex) {
        }
    }

    private void showCpuMess(String mensaje) {
        System.out.println("\nCPU: " + mensaje);
    }
}
