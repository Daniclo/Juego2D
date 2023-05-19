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

    //Esta clase gestiona la carga del mapa y la distribución de tiles.

    GamePanel gamePanel;
    public Tile[] tile; //Este array almacena todos los tiles que puede cargar el juego.
    public int[][] mapTileNum; //Este array de ints nos sirve para leer y cargar los mapas

    public TileManager(GamePanel gamePanel){

        this.gamePanel = gamePanel;

        tile = new Tile[25]; //Este es el número de tiles diferentes que va a contener el juego. El número se irá alterando en función de lo necesario
        mapTileNum = new int[gamePanel.maxMundoColumna][gamePanel.maxMundoFila]; //Cuadrícula que representa el mapa cargado

        getTileSprite();
        cargarMapa("/maps/Mapa1.txt");
    }

    private void getTileSprite() { //Aquí instanciamos el sprite para cada tipo de tile que se va a utilizar

        try{

            tile[0] = new Tile();
            tile[0].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_hierba.png")));

            tile[1] = new Tile();
            tile[1].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_tierra.png")));

            tile[2] = new Tile();
            tile[2].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_agua.png")));
            tile[2].colision = true; //Este valor indica que no se puede atravesar este tile

            tile[3] = new Tile();
            tile[3].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_palmera.png")));
            tile[3].colision = true;

            tile[4] = new Tile();
            tile[4].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_arbusto.png")));
            tile[4].colision = true;

            tile[5] = new Tile();
            tile[5].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_arena.png")));

            tile[6] = new Tile();
            tile[6].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_cactus.png")));
            tile[6].colision = true;

            tile[7] = new Tile();
            tile[7].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_roca_hierba.png")));
            tile[7].colision = true;

            tile[8] = new Tile();
            tile[8].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_roca_tierra.png")));
            tile[8].colision = true;

            tile[9] = new Tile();
            tile[9].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_roca_arena.png")));
            tile[9].colision = true;

            tile[10] = new Tile();
            tile[10].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_roca_destruida_hierba.png")));

            tile[11] = new Tile();
            tile[11].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_roca_destruida_tierra.png")));

            tile[12] = new Tile();
            tile[12].sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Tile_roca_destruida_arena.png")));

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

            while (columnas < gamePanel.maxMundoColumna && filas < gamePanel.maxMundoFila){ //Recorre las filas y columnas y va asignando el número leído a cada posición
                String linea = br.readLine();

                while(columnas < gamePanel.maxMundoColumna){
                    String[] numeros = linea.split(" ");

                    int num = Integer.parseInt(numeros[columnas]);

                    mapTileNum[columnas][filas] = num;
                    columnas++;
                }
                if (columnas == gamePanel.maxMundoColumna){
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


        int columnaMundo = 0;
        int filaMundo = 0;


        while (columnaMundo < gamePanel.maxMundoColumna && filaMundo < gamePanel.maxMundoFila){ //Este bucle va a rellenar el mapa con el tile indicado

            int tileNum = mapTileNum[columnaMundo][filaMundo];

            //Determina la posición del tile en el mundo y en la cámara. Esto significa, la posición en el mapa real total, y la posición con respecto al jugador.
            //Como el jugador tiene una posición fija en el centro de la pantalla, en realidad estamos moviendo el mapa a su alrededor.
            int xMundo = columnaMundo * gamePanel.tamanyoFinalSprites;
            int yMundo = filaMundo * gamePanel.tamanyoFinalSprites;
            int xCamara = xMundo - gamePanel.jugador.xMundo + gamePanel.jugador.xCamara;
            int yCamara = yMundo - gamePanel.jugador.yMundo + gamePanel.jugador.yCamara;


            //Este bucle hace que solo se dibujen los tiles que están en la pantalla.
            //Sirve para optimizar el renderizado del mapa y no cargarlo entero mientras que
            //no se está mostrando.
            if (xMundo + (gamePanel.tamanyoFinalSprites*2) > gamePanel.jugador.xMundo - gamePanel.jugador.xCamara &&
                xMundo - (gamePanel.tamanyoFinalSprites*2)< gamePanel.jugador.xMundo + gamePanel.jugador.xCamara &&
                yMundo + (gamePanel.tamanyoFinalSprites*2)> gamePanel.jugador.yMundo - gamePanel.jugador.yCamara &&
                yMundo - (gamePanel.tamanyoFinalSprites*2)< gamePanel.jugador.yMundo + gamePanel.jugador.yCamara){

                g2.drawImage(tile[tileNum].sprite,xCamara,yCamara,gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites,null);

            }
            columnaMundo++;

            if (columnaMundo == gamePanel.maxMundoColumna){
                columnaMundo = 0;

                filaMundo++;

            }
        }
    }
}