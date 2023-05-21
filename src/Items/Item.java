package Items;

import Main.GamePanel;
import Main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item {

    //Esta es la clase padre de la que extienden todos los items del juego.

    public BufferedImage sprite; //La textura del objeto
    public BufferedImage sprite2; //Textura secundaria por si es necesaria para algún objeto
    public String nombre; //Nombre del objeto
    public boolean colision; //Si tiene colisión o no
    public int xMundo, yMundo; //Sus coordenadas
    public Rectangle areaColision = new Rectangle(0,0,48,48); //El área de colisión de los items ocupa el cuadrado entero (48x48)
    public int areaColisionDefaultX = 0;
    public int areaColisionDefaultY = 0;
    UtilityTool uTool = new UtilityTool();

    public void dibujar(Graphics2D g2, GamePanel gamePanel){

        //Utilizamos la x y la y en el mundo para calcular la posición en pantalla
        int xCamara = xMundo - gamePanel.jugador.xMundo + gamePanel.jugador.xCamara;
        int yCamara = yMundo - gamePanel.jugador.yMundo + gamePanel.jugador.yCamara;


        //Este bucle hace que solo se dibujen los items que están en la pantalla.
        //Sirve para optimizar el renderizado del mapa y no cargar items que estén fuera.
        if (xMundo + (gamePanel.tamanyoFinalSprites*2) > gamePanel.jugador.xMundo - gamePanel.jugador.xCamara &&
                xMundo - (gamePanel.tamanyoFinalSprites*2)< gamePanel.jugador.xMundo + gamePanel.jugador.xCamara &&
                yMundo + (gamePanel.tamanyoFinalSprites*2)> gamePanel.jugador.yMundo - gamePanel.jugador.yCamara &&
                yMundo - (gamePanel.tamanyoFinalSprites*2)< gamePanel.jugador.yMundo + gamePanel.jugador.yCamara){

            g2.drawImage(sprite,xCamara,yCamara,gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites,null);

        }
    }
}