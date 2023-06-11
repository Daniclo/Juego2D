package Main;

import Entidades.Entidad;
import Entidades.Jugador;

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
                    //System.out.println(tileNum1 + " " + tileNum2);
                }

            }
            case "izquierda" -> {
                columnaIzquierda = (bordeIzquierdoX - e.speed) / gamePanel.tamanyoFinalSprites;
                tileNum1 = gamePanel.tileManager.mapTileNum[columnaIzquierda][filaInferior];
                tileNum2 = gamePanel.tileManager.mapTileNum[columnaIzquierda][filaSuperior];
                if (gamePanel.tileManager.tile[tileNum1].colision || gamePanel.tileManager.tile[tileNum2].colision) {
                    e.colisionOn = true;
                    //System.out.println(tileNum1 + " " + tileNum2);
                }

            }
            case "derecha" -> {
                columnaDerecha = (bordeDerechoX + e.speed) / gamePanel.tamanyoFinalSprites;
                tileNum1 = gamePanel.tileManager.mapTileNum[columnaDerecha][filaInferior];
                tileNum2 = gamePanel.tileManager.mapTileNum[columnaDerecha][filaSuperior];
                if (gamePanel.tileManager.tile[tileNum1].colision || gamePanel.tileManager.tile[tileNum2].colision) {
                    e.colisionOn = true;
                    //System.out.println(tileNum1 + " " + tileNum2);
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
                        if (target == gamePanel.enemigos && e == gamePanel.jugador){ //Si la interacción entre entidades es entre jugador y enemigo, el jugador recibe daño
                            establecerDMGColisionEnemigo(i);
                        }
                    }
                    case "abajo" -> {
                        e.areaColision.y += e.speed;
                        establecerColisionEntidad(e,i,target);
                        if (target == gamePanel.enemigos && e == gamePanel.jugador){ //Si la interacción entre entidades es entre jugador y enemigo, el jugador recibe daño
                            establecerDMGColisionEnemigo(i);
                        }
                    }
                    case "izquierda" -> {
                        e.areaColision.x -= e.speed;
                        establecerColisionEntidad(e,i,target);
                        if (target == gamePanel.enemigos && e == gamePanel.jugador){ //Si la interacción entre entidades es entre jugador y enemigo, el jugador recibe daño
                            establecerDMGColisionEnemigo(i);
                        }
                    }
                    case "derecha" -> {
                        e.areaColision.x += e.speed;
                        establecerColisionEntidad(e,i,target);
                        if (target == gamePanel.enemigos && e == gamePanel.jugador){ //Si la interacción entre entidades es entre jugador y enemigo, el jugador recibe daño
                            establecerDMGColisionEnemigo(i);
                        }
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
    public void checkJugador(Entidad e, Jugador j){

        if (j != null){

            //Conseguimos el área de colisión de la entidad que colisiona
            e.areaColision.x = e.xMundo + e.areaColision.x;
            e.areaColision.y = e.yMundo + e.areaColision.y;

            //Conseguimos el área de colisión de la entidad colisionada (j)
            j.areaColision.x = j.xMundo + j.areaColision.x;
            j.areaColision.y = j.yMundo + j.areaColision.y;

            switch (e.apuntandoA) {
                //Establecemos la colisión correspondiente dependiendo de la dirección
                case "arriba" -> {
                    e.areaColision.y -= e.speed;
                    establecerColisionJugador(e,j);
                }
                case "abajo" -> {
                    e.areaColision.y += e.speed;
                    establecerColisionJugador(e,j);
                }
                case "izquierda" -> {
                    e.areaColision.x -= e.speed;
                    establecerColisionJugador(e,j);
                }
                case "derecha" -> {
                    e.areaColision.x += e.speed;
                    establecerColisionJugador(e,j);
                }
            }
            //Reseteamos los valores originales de la x e y de la entidad y objeto (solo queremos cambiarlos para
            //la comprobación, no deben cambiar realmente)
            e.areaColision.x = e.areaColisionDefaultX;
            e.areaColision.y = e.areaColisionDefaultY;
            j.areaColision.x = j.areaColisionDefaultX;
            j.areaColision.y = j.areaColisionDefaultY;
        }

    }

    private void establecerColisionEntidad(Entidad e, int i, Entidad[] target){

        //Se comprueba si se tocan las áreas de colisión con el método intersects una vez se mueva la entidad.
        if (e.areaColision.intersects(target[i].areaColision)){
            if (target[i] != e){
                e.colisionOn = true;
                target[i].colisionOn = true;
            }
        }
    }
    private void establecerColisionJugador(Entidad e,Jugador j){

        //Se comprueba si se tocan las áreas de colisión con el método intersects una vez se mueva la entidad.
        if (e.areaColision.intersects(j.areaColision)){
            if (j != e){
                e.colisionOn = true;
                j.colisionOn = true;
                if (e.tipoEntidad == 2){
                    for (int i = 0;i<gamePanel.enemigos.length;i++){
                        if (gamePanel.enemigos[i] != null){
                            if (e.nombre.equals(gamePanel.enemigos[i].nombre)){
                                establecerDMGColisionEnemigo(i);
                            }
                        }
                    }

                }
            }
        }

    }

    private void establecerColisionItem(Entidad e, int i) {

        //Como en los tiles, se comprueba donde estará la entidad cuando se mueva hacia delante.
        //Pero aquí usamos el método intersects() de la clase Rectangle  quecomprueba si hay una intersección
        // con otro rectángulo (en este caso, si ambas áreas de colisión se tocan)

        if (e.areaColision.intersects(gamePanel.items[i].areaColision)){
            if (gamePanel.items[i].colision){
                e.colisionOn = true;
                if (gamePanel.items[i].nombre.equals("Roca") && gamePanel.jugador.tienePezGlobo && gamePanel.inputs.spacePressed && gamePanel.jugador.puedeRodar){ //Si el objeto es una roca, comprueba si debe romperlo
                    gamePanel.items[i].colision = false;
                    gamePanel.items[i].sprite = gamePanel.items[i].sprite2;
                    gamePanel.reproducirSonido(2);
                }
            }
        }
    }
    private void establecerDMGColisionEnemigo(int i){

        //Este método comprueba si el jugador está colisionando con un enemigo, y en ese caso detecta de que enemigo se trata para saber
        //cuanta vida quitarle al jugador. Además gestiona los frames de invencibilidad junto con el método update del jugador.

        if (gamePanel.jugador.areaColision.intersects(gamePanel.enemigos[i].areaColision)){
            String nombre = gamePanel.enemigos[i].nombre;
            switch (nombre){
                case "Rana" -> {
                    if (gamePanel.jugador.tienePezGlobo && gamePanel.inputs.spacePressed && gamePanel.jugador.puedeRodar){
                        gamePanel.enemigos[i].vida--;
                        gamePanel.reproducirSonido(3);
                        gamePanel.enemigos[i].invencibleOn = true;
                        gamePanel.jugador.invencibleOn = true;
                        gamePanel.jugador.puedeRodar = false;
                        //DEBUG
                        System.out.println(gamePanel.enemigos[i].contadorInvencible);
                    }else {
                        if (!gamePanel.jugador.invencibleOn){
                            gamePanel.jugador.vida--;
                            gamePanel.reproducirSonido(3);
                            gamePanel.jugador.invencibleOn = true;
                        }
                    }
                }
            }

        }

    }
}