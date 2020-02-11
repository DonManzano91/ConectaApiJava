package com.manzano.conectaApi;

public class GatosFavoritos {

    String id;
    String imagenId;
    String apikey = null;
    ImagenesFavoritas favoritosGatos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagenId() {
        return imagenId;
    }

    public void setImagenId(String imagenId) {
        this.imagenId = imagenId;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public ImagenesFavoritas getFavoritosGatos() {
        return favoritosGatos;
    }

    public void setFavoritosGatos(ImagenesFavoritas favoritosGatos) {
        this.favoritosGatos = favoritosGatos;
    }
}
