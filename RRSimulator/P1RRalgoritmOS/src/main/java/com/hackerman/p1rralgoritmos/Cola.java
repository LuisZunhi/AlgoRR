package com.hackerman.p1rralgoritmos;


public class Cola {

    //ATRIBUTOS
    private int cantidad; //Cantidad de nodos en la cola
    static int idSegundo = 0;
    private Proceso nodoTemporal; //nodoTemporal apuntará al ultimo nodo agregado
    private Proceso nodoInicial; //Apunta siempre al primer nodo de la cola, se actualiza siempre que se desencole algo
    private Proceso nodoImpresion; //Este nodo solo se usa en las impresiones, para evitar corrupciones
    private String miNombre;

    //CONSTRUCTORES
    public Cola(String nombre) {
        this.miNombre = nombre;
        this.nodoTemporal = new Proceso(); //Se inicializa con nodo siguiente igual a null
        this.nodoInicial = this.nodoTemporal;
        this.nodoImpresion = new Proceso();
        this.cantidad = 0;
    }

    //MÉTODOS
    //Metodo para insertar un nodo a la cola
 
    public void insertar(Proceso nodo) {

        if (cantidad == 0) {
            //Cola vacía
            nodoInicial = nodo;
        } else {
            nodoTemporal.setSiguiente(nodo);
        }
        nodoTemporal = nodo;
        cantidad++;
    }

    //Retorna el primer valor de la cola y lo quita
    public Proceso desencolar() {

        if (cantidad == 0) {
            return null;
        } else {

            Proceso headAntiguo = nodoInicial;
            try {
                nodoInicial = nodoInicial.getSiguiente();
            } catch (NullPointerException e) {
            }

            cantidad--;
            return headAntiguo;
        }
    }

    
    public void imprimirColaCompleta() {
        try {
            nodoImpresion = nodoInicial;

            System.out.println("\n\t\tMostrando cambios en la cola '" + miNombre);
            System.out.println("\t ___________________________________________________________________________");
            System.out.println("\t| ID | Nombre |  Memoria [kb] | Tiempo llegada [ms] | Tiempo ejecucion [ms] |");

            for (int i = 0; i < cantidad; i++) { 
                String showID = String.format("%03d", nodoImpresion.id ); 
                String showName = nodoImpresion.nombre.substring(0, Math.min(nodoImpresion.nombre.length(), 6));
                String showMemoSize    = String.format("%03d", nodoImpresion.tam); 
                String showArriveTime  = String.format("%03d", nodoImpresion.tiempoLlegada);
                String showTimeService = String.format("%03d", nodoImpresion.tiempoServicio); 
                
                if (i == 0) {  //head
                    System.out.println("\t| " + showID + 
                                         "| " + showName +  
                                        " |   " + showMemoSize + 
                                "         |   " + showArriveTime +
                          "               |   "  + showTimeService + 
                        "                 | \t <-------------------- Head");

                } else if (i == cantidad - 1) { //tail
                    System.out.println("\t| " + showID + 
                                         "| " + showName +  
                                        " |   " + showMemoSize + 
                                "         |   " + showArriveTime +
                          "               |   "  + showTimeService + 
                        "                 | \t <-------------------- Tail");

                } else { //Entre head y tail
                    System.out.println("\t| " + showID + 
                                         "| " + showName +  
                                        " |   " + showMemoSize + 
                                "         |   " + showArriveTime +
                          "               |   "  + showTimeService + 
                        "                 | \t  ");
                }

                nodoImpresion = nodoImpresion.getSiguiente();
            }

            System.out.println("\t ___________________________________________________________________________\n\n");

        } catch (NullPointerException e) {
        }
    }

    
    public void imprimirDatosFinales() {
        try {
            nodoImpresion = nodoInicial;

            int resp, esp, ejec;
            int respProm = 0;
            int espProm = 0;
            int ejecProm = 0;

            System.out.println("\n\n                      Resumen de la ejecucion de los procesos                  ");
            System.out.println("\t_______________________________________________________________________________");
            System.out.println("\t| Nombre | T. Ejecucion | T. Espera | T. Respuesta | T. Llegada | T. Servicio |");

            for (int i = 0; i < cantidad; i++) {
                String showName = nodoImpresion.nombre.substring(0, Math.min(nodoImpresion.nombre.length(), 8));
                ejec = nodoImpresion.tiempoTotal - nodoImpresion.tiempoLlegada;
                String showTejec = String.format("%03d", ejec); 

                esp = ejec - nodoImpresion.tiempoServicio;
                String showTesp = String.format("%03d", esp); 

                resp = nodoImpresion.tiempoEntrada - nodoImpresion.tiempoLlegada;
                String showTresp = String.format("%03d", resp); 

                String showArriveTime = String.format("%03d", nodoImpresion.tiempoLlegada); 
                String showTimeService = String.format("%03d", nodoImpresion.tiempoLlegada);

                System.out.println("\t|   " + showName + 
                                  "   |   " + showTejec + 
                                  "   |   " + showTesp + 
                                  "   |   " + showTresp + 
                                  "   |   " + showArriveTime + 
                                  "   |   " + showTimeService + "   |");

                ejecProm += ejec;
                espProm += esp;
                respProm += resp;

                nodoImpresion = nodoImpresion.getSiguiente();
            }
            System.out.println("\t_______________________________________________________________________________"); 

            System.out.println("\nEjecucion promedio: " + ejecProm / cantidad + "\nEspera promedio: "
                    + espProm / cantidad + "\nRespuesta promedio: " + respProm / cantidad);

        } catch (NullPointerException e) {
        }
    }

    public int getCantidad() {
        return this.cantidad;
    }


}
