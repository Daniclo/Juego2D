package Entidades;

import java.awt.image.BufferedImage;

public class Entidad {

    //Esta clase será la clase padre de las clases del jugador, npcs y enemigos
    public int x, y; //Posición en pantalla de la entidad
    public int speed; //Velocidad a la que se desplaza la entidad

    public BufferedImage arriba1, arriba2, abajo1, abajo2, izquierda1, izquierda2, derecha1, derecha2; //Almacena las animaciones de movimiento de las entidades
    public String apuntandoA; //Almacena la información de hacia donde está mirando la entidad
    public int contadorSprite = 0;
    public int spriteActual = 1;
}