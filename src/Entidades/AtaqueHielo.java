package Entidades;

import Main.GamePanel;

public class AtaqueHielo extends Proyectil{

    GamePanel gamePanel;
    public AtaqueHielo(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        nombre = "Hielo";
        speed = 7;
        vidaMaxima = 80;
        vida = vidaMaxima;
        ataque = 2;
        getSprites();
    }

    public void getSprites(){

        arriba1 = setUpSprite("/proyectiles/Bola1");
        arriba2 = setUpSprite("/proyectiles/Bola2");
        arriba3 = setUpSprite("/proyectiles/Bola3");
        abajo1 = setUpSprite("/proyectiles/Bola1");
        abajo2 = setUpSprite("/proyectiles/Bola2");
        abajo3 = setUpSprite("/proyectiles/Bola3");
        izquierda1 = setUpSprite("/proyectiles/Bola1");
        izquierda2 = setUpSprite("/proyectiles/Bola2");
        izquierda3 = setUpSprite("/proyectiles/Bola3");
        derecha1 = setUpSprite("/proyectiles/Bola1");
        derecha2 = setUpSprite("/proyectiles/Bola2");
        derecha3 = setUpSprite("/proyectiles/Bola3");

    }
}
