package Entidades.enemigos;

import Entidades.Entidad;
import Main.GamePanel;

import java.util.Random;

public class Rana extends Entidad {
    public Rana(GamePanel gamePanel) {
        super(gamePanel);

        nombre = "Rana";
        speed = 3;
        vidaMaxima = 3;
        vida = vidaMaxima;
        ataque = 1;
        getSprites();
        apuntandoA = "abajo";
        tipoEntidad = 2; //Es un enemigo.
    }
    public void getSprites(){

        abajo1 = setUpSprite("/enemigos/Rana_andar/Rana_abajo_1");
        abajo2 = setUpSprite("/enemigos/Rana_andar/Rana_abajo_2");
        arriba1 = setUpSprite("/enemigos/Rana_andar/Rana_arriba_1");
        arriba2 = setUpSprite("/enemigos/Rana_andar/Rana_arriba_2");
        izquierda1 = setUpSprite("/enemigos/Rana_andar/Rana_izquierda_1");
        izquierda2 = setUpSprite("/enemigos/Rana_andar/Rana_izquierda_2");
        derecha1 = setUpSprite("/enemigos/Rana_andar/Rana_derecha_1");
        derecha2 = setUpSprite("/enemigos/Rana_andar/Rana_derecha_2");

    }
    @Override
    public void setAccion(){

        contadorAcciones++;

        if (contadorAcciones == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if (i <= 25){
                apuntandoA = "arriba";
            }
            if (i > 25 && i <= 50){
                apuntandoA = "abajo";
            }
            if (i > 50 && i <= 75){
                apuntandoA = "izquierda";
            }
            if (i > 75){
                apuntandoA = "derecha";
            }
            contadorAcciones=0;
        }

    }
}
