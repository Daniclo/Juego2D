package Entidades;

import Main.GamePanel;

public class Proyectil extends Entidad{

    public Proyectil(GamePanel gamePanel) {
        super(gamePanel);
    }

    public void set(int xMundo, int yMundo, String apuntandoA, boolean muerte){

        this.xMundo = xMundo;
        this.yMundo = yMundo;
        this.apuntandoA = apuntandoA;
        this.muerte = muerte;
        //Determina que entidad lanza el proyectil.
        this.vida = this.vidaMaxima;

    }

    public void actualizar(){
        if (apuntandoA != null){
            switch (apuntandoA){
                case "arriba" -> yMundo -= speed;
                case "abajo" -> yMundo += speed;
                case "izquierda" -> xMundo -= speed;
                case "derecha" -> xMundo += speed;
            }
        }

        //Este valor de vida define lo lejos que se desplazará el proyectil antes de desaparecer.
        vida--;
        if (vida <= 0){
            muerte = true;
        }

        gamePanel.jugador.contadorProyectil++;

        contadorSprite++;
        if (contadorSprite > 12){
            if (spriteActual == 1){
                spriteActual = 2;
            }
            if (spriteActual == 2){
                spriteActual = 3;
            }
            if (spriteActual == 3){
                spriteActual = 1;
            }
            contadorSprite = 0;
        }

        colisionOn = false;
        gamePanel.checkColisiones.checkEntitdad(this, gamePanel.enemigos);
        if (colisionOn){
            vida = 0;
            gamePanel.jugador.contadorProyectil = 75;
        }
    }

}
