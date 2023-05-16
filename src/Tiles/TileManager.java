package Tiles;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    Tile[] tile; //Este array almacena todos los tiles que puede cargar el juego.
    int[][] mapTileNum; //Este array de ints nos sirve para leer y cargar los mapas

    public TileManager(GamePanel gamePanel){

        this.gamePanel = gamePanel;

        tile = new Tile[10]; //Este es el número de tiles diferentes que va a contener el juego. El número se irá alterando en función de lo necesario
        mapTileNum = new int[gamePanel.numMaxColumnas][gamePanel.numMaxFilas]; //Cuadrícula que representa la pantalla y sirve para cargar mapas

        getTileSprite();
        cargarMapa("/maps/Mapa1.txt");
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

    public void cargarMapa(String mapa){ //Lee el mapa seleccionado

        try {

            InputStream is = getClass().getResourceAsStream(mapa); //Lee el archivo del mapa para interpretarlo
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int columnas = 0;
            int filas = 0;

            while (columnas < gamePanel.numMaxColumnas && filas < gamePanel.numMaxFilas){ //Recorre las filas y columnas y va asignando el número leído a cada posición
                String linea = br.readLine();

                while(columnas < gamePanel.numMaxColumnas){
                    String[] numeros = linea.split(" ");

                    int num = Integer.parseInt(numeros[columnas]);

                    mapTileNum[columnas][filas] = num;
                    columnas++;
                }
                if (columnas == gamePanel.numMaxColumnas){
                    columnas = 0;
                    filas++;
                }
            }
            br.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void dibujar(Graphics2D g2){


        int columna = 0;
        int fila = 0;
        int x = 0;
        int y = 0;

        while (columna < gamePanel.numMaxColumnas && fila < gamePanel.numMaxFilas){ //Este bucle va a rellenar el mapa con el tile indicado

            int tileNum = mapTileNum[columna][fila];

            g2.drawImage(tile[tileNum].sprite,x,y,gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites,null);
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