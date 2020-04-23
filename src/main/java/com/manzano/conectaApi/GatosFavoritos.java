package com.manzano.conectaApi;

public class GatosFavoritos {

    String id;
    String image_id;
    String apikey = "9c53f7b2-233c-4e8a-87d8-57400ff50d57";
    ImagenesFavoritas image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public ImagenesFavoritas getImage() {
        return image;
    }

    public void setImage(ImagenesFavoritas image) {
        this.image = image;
    }
}
