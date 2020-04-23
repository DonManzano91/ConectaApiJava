package com.manzano.conectaApi;

import com.google.gson.Gson;
import com.squareup.okhttp.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

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
            //RequestBody body = RequestBody.create(mediaType, "{\r\n  \"image_id\": \"88j\"\r\n}");
            RequestBody body = RequestBody.create(mediaType, "{\r\n  \"image_id\": \" "+gatos.getId()+"\"\r\n}");
            System.out.println("Valor guardar: " + gatos.getId());
            System.out.println("Apikey: " + gatos.getApikey());
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gatos.getApikey())
                    .build();
            System.out.println("Peticion: " + request);
            Response response = client.newCall(request).execute();
            System.out.println("Guardo en favorito: " + response.toString());
        }catch (IOException e){
            System.out.println("No pudo guardar favoritos: " + e);
        }
    }

    public static void verFavoritos(String apikey) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", apikey)
                .build();
        System.out.println("Peticion ver gatos" + request.toString());
        Response response = client.newCall(request).execute();
        System.out.println("Respuesta verGatos: " + response.toString());
        String elJson = response.body().string();
        System.out.println("Respuesta: " + elJson);
        Gson gson = new Gson();

        GatosFavoritos[] arrayGatos = gson.fromJson(elJson, GatosFavoritos[].class);

        if (arrayGatos.length>0){
            int min = 1;
            int max = arrayGatos.length;
            int aleatorio = (int) (Math.random()*((max-min)+1)) + min;
            int indice = aleatorio - 1 ;

            GatosFavoritos gatosFavoritos = arrayGatos[indice];
            System.out.println("instansiando: " + gatosFavoritos.id);
            System.out.println("instansiando: " + gatosFavoritos.image_id);
            System.out.println("instansiando: " + gatosFavoritos.apikey);
            System.out.println("instansiando: " + gatosFavoritos.image);
            /*Redimensionar la imagen en caso de necesitar*/
            Image imagen = null;
            try{
                ImagenesFavoritas favoritas = null;
                //URL url = new URL("https://cdn2.thecatapi.com/images/9ccXTANkb.jpg");
                //System.out.println( "Contenido URL prueba" + url);
                URL url = new URL(gatosFavoritos.image.getUrl());
                System.out.println("URL el favorito: " + url);
                System.out.println(url);
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
                        + "2 Eliminar Favorito \n"
                        + "3 Volver \n";

                String[] botones = {"ver otra imagin", "eliminar favorito", "volver"};
                String idGato = String.valueOf(gatosFavoritos.getId());
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
                        verFavoritos(apikey);
                        break;
                    case 1:
                        borrarFavorito(gatosFavoritos);
                        break;
                    default:
                        break;
                }

            }catch (IOException e){
                System.out.println("No se pudo traer la imagen del gato " + e);
            }
            
        }
    }

    private static void borrarFavorito(GatosFavoritos gatosFavoritos) throws IOException {

        try{
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/2013232")
                    .url("https://api.thecatapi.com/v1/favourites/"+gatosFavoritos.getId()+"")
                    .method("DELETE", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", "9c53f7b2-233c-4e8a-87d8-57400ff50d57")
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println("Se borro el favorito");
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
