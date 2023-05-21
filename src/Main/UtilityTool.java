package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    //Esta clase contiene métodos variados que permiten optimizar diferentes partes del juego.


    //Este método va a permitir escalar imagenes fuera del loop de juego para mejorar el rendimiento.
    public BufferedImage escalarImagen (BufferedImage original, int ancho, int alto){

        BufferedImage imagenEscalada = new BufferedImage(ancho,alto,original.getType());
        Graphics2D g2 = imagenEscalada.createGraphics();
        g2.drawImage(original,0,0,ancho,alto,null);
        g2.dispose();

        return imagenEscalada;
    }

}
