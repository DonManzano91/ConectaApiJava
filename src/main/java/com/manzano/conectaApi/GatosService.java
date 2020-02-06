package com.manzano.conectaApi;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

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
        }catch (IOException e){
            System.out.println("No se pudo traer la imagen del gato " + e); 
        }
    }
}
