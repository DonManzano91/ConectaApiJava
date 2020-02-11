package com.manzano.conectaApi;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int opcionMenu = -1;
        String[] botones = {
                "1. Ver gatos",
                "2. Ver Favoritos",
                "3. Salir"
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
                    Gatos gatos = new Gatos();
                    System.out.println("entra case 1");
                    GatosService.verFavoritos(gatos.getApikey());
                    break;
                case 2 :
                    System.out.println("entra case 2");
                    break;
                default:
                    break;
            }
        }while (opcionMenu!=1);
    }
}
