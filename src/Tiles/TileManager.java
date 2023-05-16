package Tiles;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    Tile[] tile;

    public TileManager(GamePanel gamePanel){

        this.gamePanel = gamePanel;
        tile = new Tile[10]; //Este es el número de tiles diferentes que va a contener el juego. El número se irá alterando en función de lo necesario
        getTileSprite();
    }

    private void getTileSprite() { //Aquí instanciamos el sprite para cada tipo de tile que se va a utilizar

        try{

            tile[0] = new Tile();
            tile[0].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_hierba_helada.png")));

            tile[1] = new Tile();
            tile[1].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_tierra.png")));

            tile[2] = new Tile();
            tile[2].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_agua.png")));

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void dibujar(Graphics2D g2){


        int columna = 0;
        int fila = 0;
        int x = 0;
        int y = 0;

        while (columna < gamePanel.numMaxColumnas && fila < gamePanel.numMaxFilas){ //Este bucle va a rellenar el mapa con el tile indicado

            g2.drawImage(tile[0].sprite,x,y,gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites,null);
            columna++;
            x += gamePanel.tamanyoFinalSprites;

            if (columna == gamePanel.numMaxColumnas){
                columna = 0;
                x = 0;
                fila++;
                y += gamePanel.tamanyoFinalSprites;
            }
        }


    }
}
