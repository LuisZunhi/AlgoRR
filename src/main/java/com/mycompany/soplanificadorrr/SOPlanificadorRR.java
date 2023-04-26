

package com.mycompany.soplanificadorrr;

import java.util.Scanner;

public class SOPlanificadorRR {

    public static void main(String[] args) { 
        presentacion();
        definicionParametros();
        System.out.println("Hasta la proxima");

        //Datos datos = new Datos(); //Solamente recolecta los datos necesarios para el simulador
        //Simulador simulador = new Simulador(datos);

        //simulador.iniciar(); //Inicia la ejecución
    }

    public static void presentacion(){
        System.out.println("\n\n");
        System.out.println("******************************************");
        System.out.println("          Facultad de Ingenieria          ");
        System.out.println("Materia: Sistemas Operativos              ");
        System.out.println("Profesora: Ing. Patricia Del Valle Morales");
        System.out.println("Alumnos:");
        System.out.println("Mejia Alba Israel Hipolito");
    }

    public static void definicionParametros(){
        int ramSize , maxProcesSize;
        scan = new Scanner(System.in);
        System.out.println("\nIniciemos definiendo ciertos parametros del programa");
        System.out.println("Cuanta memoria RAM (kb) tendra nuestro simualdor  :");
        ramSize = maxProcesSize = scan.nextInt();

    }
}

