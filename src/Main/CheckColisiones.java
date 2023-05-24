package Main;

import Entidades.Entidad;

public class CheckColisiones {

    //Esta clase permite determinar que bloques u objetos son sólidos y cuales no lo son y gestiona las colisiones entre
    //entidades y estos bloques u objetos.

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

        switch (e.apuntandoA) {
            case "arriba" -> {
                //Estas líneas permiten saber hacia qué tiles se está moviendo el jugador mientras que pulsa cada botón
                filaSuperior = (bordeSuperiorY - e.speed) / gamePanel.tamanyoFinalSprites;

                tileNum1 = gamePanel.tileManager.mapTileNum[columnaIzquierda][filaSuperior];
                tileNum2 = gamePanel.tileManager.mapTileNum[columnaDerecha][filaSuperior];
                //Comprobamos si alguno de los 2 tiles de arriba es sólido
                if (gamePanel.tileManager.tile[tileNum1].colision || gamePanel.tileManager.tile[tileNum2].colision) {
                    e.colisionOn = true;
                }
            }
            case "abajo" -> {
                filaInferior = (bordeInferiorY + e.speed) / gamePanel.tamanyoFinalSprites;
                tileNum1 = gamePanel.tileManager.mapTileNum[columnaIzquierda][filaInferior];
                tileNum2 = gamePanel.tileManager.mapTileNum[columnaDerecha][filaInferior];
                if (gamePanel.tileManager.tile[tileNum1].colision || gamePanel.tileManager.tile[tileNum2].colision) {
                    e.colisionOn = true;
                }
            }
            case "izquierda" -> {
                columnaIzquierda = (bordeIzquierdoX - e.speed) / gamePanel.tamanyoFinalSprites;
                tileNum1 = gamePanel.tileManager.mapTileNum[columnaIzquierda][filaInferior];
                tileNum2 = gamePanel.tileManager.mapTileNum[columnaIzquierda][filaSuperior];
                if (gamePanel.tileManager.tile[tileNum1].colision || gamePanel.tileManager.tile[tileNum2].colision) {
                    e.colisionOn = true;
                }
            }
            case "derecha" -> {
                columnaDerecha = (bordeDerechoX + e.speed) / gamePanel.tamanyoFinalSprites;
                tileNum1 = gamePanel.tileManager.mapTileNum[columnaDerecha][filaInferior];
                tileNum2 = gamePanel.tileManager.mapTileNum[columnaDerecha][filaSuperior];
                if (gamePanel.tileManager.tile[tileNum1].colision || gamePanel.tileManager.tile[tileNum2].colision) {
                    e.colisionOn = true;
                }
            }
        }
    }

    //Este método va a comprobar si hay un item delante que tiene colisión y en caso de que si, devolver el índice del item
    public void checkItem(Entidad e){ //Recibimos una entidad para comprobar si colisiona


        for (int i=0;i<gamePanel.items.length;i++){

            if (gamePanel.items[i] != null){

                //Conseguimos el área de colisión de la entidad
                e.areaColision.x = e.xMundo + e.areaColision.x;
                e.areaColision.y = e.yMundo + e.areaColision.y;

                //Conseguimos el área de colisión del item
                gamePanel.items[i].areaColision.x = gamePanel.items[i].xMundo + gamePanel.items[i].areaColision.x;
                gamePanel.items[i].areaColision.y = gamePanel.items[i].yMundo + gamePanel.items[i].areaColision.y;

                switch (e.apuntandoA) {

                    case "arriba" -> {
                        e.areaColision.y -= e.speed;
                        establecerColisionItem(e, i);
                    }
                    case "abajo" -> {
                        e.areaColision.y += e.speed;
                        establecerColisionItem(e, i);
                    }
                    case "izquierda" -> {
                        e.areaColision.x -= e.speed;
                        establecerColisionItem(e, i);
                    }
                    case "derecha" -> {
                        e.areaColision.x += e.speed;
                        establecerColisionItem(e, i);
                    }
                }
                //Reseteamos los valores originales de la x e y de la entidad y objeto (solo queremos cambiarlos para
                //la comprobación, no deben cambiar realmente)
                e.areaColision.x = e.areaColisionDefaultX;
                e.areaColision.y = e.areaColisionDefaultY;
                gamePanel.items[i].areaColision.x = gamePanel.items[i].areaColisionDefaultX;
                gamePanel.items[i].areaColision.y = gamePanel.items[i].areaColisionDefaultY;
            }

        }
    }

    //Colisión con NPC o enemigo
    public void checkEntitdad(Entidad e, Entidad[] target){

        for (int i = 0; i<target.length; i++) {

            if (target[i] != null) { //Si la entidad objetivo existe

                //Conseguimos el área de colisión de la entidad que colisiona
                e.areaColision.x = e.xMundo + e.areaColision.x;
                e.areaColision.y = e.yMundo + e.areaColision.y;

                //Conseguimos el área de colisión de la entidad colisionada (target)
                target[i].areaColision.x = target[i].xMundo + target[i].areaColision.x;
                target[i].areaColision.y = target[i].yMundo + target[i].areaColision.y;

                switch (e.apuntandoA) {
                    //Establecemos la colisión correspondiente dependiendo de la dirección
                    case "arriba" -> {
                        e.areaColision.y -= e.speed;
                        establecerColisionEntidad(e,i,target);
                    }
                    case "abajo" -> {
                        e.areaColision.y += e.speed;
                        establecerColisionEntidad(e,i,target);
                    }
                    case "izquierda" -> {
                        e.areaColision.x -= e.speed;
                        establecerColisionEntidad(e,i,target);
                    }
                    case "derecha" -> {
                        e.areaColision.x += e.speed;
                        establecerColisionEntidad(e,i,target);
                    }
                }
                //Reseteamos los valores originales de la x e y de la entidad y objeto (solo queremos cambiarlos para
                //la comprobación, no deben cambiar realmente)
                e.areaColision.x = e.areaColisionDefaultX;
                e.areaColision.y = e.areaColisionDefaultY;
                target[i].areaColision.x = target[i].areaColisionDefaultX;
                target[i].areaColision.y = target[i].areaColisionDefaultY;

            }

        }

    }

    private void establecerColisionEntidad(Entidad e, int i, Entidad[] target){

        //Se comprueba si se tocan las áreas de colisión con el método intersects una vez se mueva la entidad.
        if (e.areaColision.intersects(target[i].areaColision)){
            e.colisionOn = true;
            target[i].colisionOn = true;
        }
    }

    private void establecerColisionItem(Entidad e, int i) {

        //Como en los tiles, se comprueba donde estará la entidad cuando se mueva hacia delante.
        //Pero aquí usamos el método intersects() de la clase Rectangle  quecomprueba si hay una intersección
        // con otro rectángulo (en este caso, si ambas áreas de colisión se tocan)

        if (e.areaColision.intersects(gamePanel.items[i].areaColision)){
            if (gamePanel.items[i].colision){
                e.colisionOn = true;
                if (gamePanel.items[i].nombre.equals("Roca") && gamePanel.jugador.tienePezGlobo && gamePanel.inputs.spacePressed){ //Si el objeto es una roca, comprueba si debe romperlo
                    gamePanel.items[i].colision = false;
                    gamePanel.items[i].sprite = gamePanel.items[i].sprite2;
                    gamePanel.reproducirSonido(2);
                }
            }
        }
    }
}