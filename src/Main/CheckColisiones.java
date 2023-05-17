package Main;

import Entidades.Entidad;

public class CheckColisiones {

    GamePanel gamePanel;
    public CheckColisiones(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entidad e){

        //Se definen los límites del area de colisión:
        int bordeIzquierdoX = e.xMundo + e.areaColision.x;
        int bordeDerechoX = e.xMundo + e.areaColision.x + e.areaColision.width;
        int bordeSuperiorY = e.yMundo + e.areaColision.y;
        int bordeInferiorY = e.yMundo + e.areaColision.y + e.areaColision.height;

        //Se definen las filas y columnas para compararlas con los tiles
        int columnaIzquierda = bordeIzquierdoX/gamePanel.tamanyoFinalSprites;
        int columnaDerecha = bordeDerechoX/gamePanel.tamanyoFinalSprites;
        int filaSuperior = bordeSuperiorY/gamePanel.tamanyoFinalSprites;
        int filaInferior = bordeInferiorY/gamePanel.tamanyoFinalSprites;

        int tileNum1, tileNum2;

        switch (e.apuntandoA){
            case "arriba":

                filaSuperior = (bordeSuperiorY - e.speed)/gamePanel.tamanyoFinalSprites; //Estas líneas permiten saber hacia qué tiles se está moviendo el jugador mientras que pulsa
                                                                                            //cada botón
                tileNum1 = gamePanel.tileManager.mapTileNum[columnaIzquierda][filaSuperior];
                tileNum2 = gamePanel.tileManager.mapTileNum[columnaDerecha][filaSuperior];

                if (gamePanel.tileManager.tile[tileNum1].colision || gamePanel.tileManager.tile[tileNum2].colision){ //Comprobamos si alguno de los 2 tiles de arriba es sólido
                    e.colisionOn = true;
                }

                break;
            case "abajo":

                filaInferior = (bordeInferiorY + e.speed)/gamePanel.tamanyoFinalSprites;
                tileNum1 = gamePanel.tileManager.mapTileNum[columnaIzquierda][filaInferior];
                tileNum2 = gamePanel.tileManager.mapTileNum[columnaDerecha][filaInferior];

                if (gamePanel.tileManager.tile[tileNum1].colision || gamePanel.tileManager.tile[tileNum2].colision){
                    e.colisionOn = true;
                }

                break;
            case "izquierda":

                columnaIzquierda = (bordeIzquierdoX - e.speed)/gamePanel.tamanyoFinalSprites;
                tileNum1 = gamePanel.tileManager.mapTileNum[columnaIzquierda][filaInferior];
                tileNum2 = gamePanel.tileManager.mapTileNum[columnaIzquierda][filaSuperior];

                if (gamePanel.tileManager.tile[tileNum1].colision || gamePanel.tileManager.tile[tileNum2].colision){
                    e.colisionOn = true;
                }

                break;
            case "derecha":

                columnaDerecha = (bordeDerechoX + e.speed)/gamePanel.tamanyoFinalSprites;
                tileNum1 = gamePanel.tileManager.mapTileNum[columnaDerecha][filaInferior];
                tileNum2 = gamePanel.tileManager.mapTileNum[columnaDerecha][filaSuperior];

                if (gamePanel.tileManager.tile[tileNum1].colision || gamePanel.tileManager.tile[tileNum2].colision){
                    e.colisionOn = true;
                }

                break;
        }

    }

}
