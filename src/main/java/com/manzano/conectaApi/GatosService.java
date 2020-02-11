package com.manzano.conectaApi;

import com.google.gson.Gson;
import com.squareup.okhttp.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class GatosService {
    public static void verGatos() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        String elJason = response.body().string();
        /*De lo recibido en postman, hay que quitar la primer y ultima llave*/

        elJason = elJason.substring(1, elJason.length());
        elJason = elJason.substring(0,elJason.length()-1);

        /*Crear Objeto Clae GSON*/
        Gson gson = new Gson();
        /*Hay que convertir lo obtenido de a API, a un objeto del tipo Gato*/

        Gatos gatos = gson.fromJson(elJason, Gatos.class);

        /*Redimensionar la imagen en caso de necesitar*/

        Image imagen = null;
        try{
            URL url = new URL(gatos.getUrl());
            imagen = ImageIO.read(url);
            ImageIcon fondoGato = new ImageIcon(imagen);

            if (fondoGato.getIconWidth() > 800){
                /*Redimensión*/
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800,
                        600,
                        Image.SCALE_SMOOTH);
                fondoGato= new ImageIcon(modificada);
            }

            String menu = "Opciones: \n"
                    + "1 Ver otra imagen \n"
                    + "2 Marca el gato como favorito \n"
                    + "3 Volver \n";

            String[] botones = {"ver otra imagin", "favorito", "volver"};
            String idGato = String.valueOf(gatos.getId());
            String opcion = (String) JOptionPane.showInputDialog(
              null,
              menu,
              idGato,
              JOptionPane.INFORMATION_MESSAGE,
                    fondoGato,
                    botones,
                    botones[0]
            );

            int seleccion = -1;
            /*Validamos la opcion que eligio el usuario*/
            for (int i = 0; i<botones.length; i++){
                if (opcion.equals(botones[i])){
                    seleccion = i;
                }
            }

            switch (seleccion){
                case 0:
                    verGatos();
                    break;
                case 1:
                    gatosFavoritos(gatos);
                    break;
                default:
                    break;
            }

        }catch (IOException e){
            System.out.println("No se pudo traer la imagen del gato " + e);
        }
    }

    public static void gatosFavoritos(Gatos gatos) throws IOException {
        try{
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n  \"image_id\": \""+gatos.getId()+"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gatos.getApikey())
                    .build();
            Response response = client.newCall(request).execute();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static void verFavoritos(String apikey) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .method("GET", null)
                .addHeader("x-api-key", apikey)
                .build();
        Response response = client.newCall(request).execute();
        String elJson = response.body().string();
        Gson gson = new Gson();

        GatosFavoritos[] arrayGatos = gson.fromJson(elJson, GatosFavoritos[].class);

        if (arrayGatos.length>0){
            int min = 1;
            int max = arrayGatos.length;
            int aleatorio = (int) (Math.random()*((max-min)-1)) + min;
            int indice = aleatorio - 1 ;

            GatosFavoritos gatosFavoritos = arrayGatos[indice];

            
        }
    }
}
