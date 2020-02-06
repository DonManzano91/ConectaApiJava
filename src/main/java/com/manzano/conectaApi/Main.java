package com.manzano.conectaApi;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int opcionMenu = -1;
        String[] botones = {
                "1. Ver gatos",
                "2. Salir"
        };

        do {
            String opcion = (String) JOptionPane.showInputDialog(
                    null,
                    "gatitosJava",
                    "Menu Principal",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    botones,
                    botones[0]);
            for (int i=0; i<botones.length; i++){
                if (opcion.equals(botones[i])){
                    opcionMenu = i;
                }
            }

            switch (opcionMenu){
                case 0:
                    System.out.println("entra case 0");
                    GatosService.verGatos();
                    break;
                case 1:
                    System.out.println("entra case 1");
                    break;
            }
        }while (opcionMenu!=1);
    }
}
