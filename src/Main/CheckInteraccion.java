package Main;

import Entidades.Entidad;
import Entidades.Jugador;

public class CheckInteraccion {

    //Esta clase comprueba si, al pulsar el botón de interacción, el jugador está en contacto con un elemento
    //interactuable y gestiona la interacción con dicho elemento

    GamePanel gamePanel;

    public CheckInteraccion(GamePanel gamePanel){

        this.gamePanel = gamePanel;
    }

    public void checkItem(Jugador e){

        for (int i=0;i<gamePanel.items.length;i++){

            if (gamePanel.items[i] != null){

                //Conseguimos el área de colisión de la entidad
                e.areaColision.x = e.xMundo + e.areaColision.x;
                e.areaColision.y = e.yMundo + e.areaColision.y;

                //Conseguimos el área de colisión del item
                gamePanel.items[i].areaColision.x = gamePanel.items[i].xMundo + gamePanel.items[i].areaColision.x;
                gamePanel.items[i].areaColision.y = gamePanel.items[i].yMundo + gamePanel.items[i].areaColision.y;

                switch (e.apuntandoA) { //Igual que en las colisiones, se comprueba si está el jugador pegado al objeto.

                    case "arriba" -> {
                        e.areaColision.y -= e.speed;
                        if (e.areaColision.intersects(gamePanel.items[i].areaColision)) {
                            String nombre = gamePanel.items[i].nombre; //Se almacena el nombre del objeto y se envía a otro método para manejarlo
                            obtenerObjetos(nombre, e);
                        }
                    }
                    case "abajo" -> {
                        e.areaColision.y += e.speed;
                        if (e.areaColision.intersects(gamePanel.items[i].areaColision)) {
                            String nombre = gamePanel.items[i].nombre; //Se almacena el nombre del objeto y se envía a otro método para manejarlo
                            obtenerObjetos(nombre, e);
                        }
                    }
                    case "izquierda" -> {
                        e.areaColision.x -= e.speed;
                        if (e.areaColision.intersects(gamePanel.items[i].areaColision)) {
                            String nombre = gamePanel.items[i].nombre; //Se almacena el nombre del objeto y se envía a otro método para manejarlo
                            obtenerObjetos(nombre, e);
                        }
                    }
                    case "derecha" -> {
                        e.areaColision.x += e.speed;
                        if (e.areaColision.intersects(gamePanel.items[i].areaColision)) {
                            String nombre = gamePanel.items[i].nombre; //Se almacena el nombre del objeto y se envía a otro método para manejarlo
                            obtenerObjetos(nombre, e);
                        }
                    }
                }
                //Reseteamos los valores originales de la x e y de la entidad y objeto (solo queremos cambiarlos para
                //la comprobación, no deben cambiar realmente)
                e.areaColision.x = e.areaColisionDefaultX;
                e.areaColision.y = e.areaColisionDefaultY;
                if (gamePanel.items[i] != null){
                    gamePanel.items[i].areaColision.x = gamePanel.items[i].areaColisionDefaultX;
                    gamePanel.items[i].areaColision.y = gamePanel.items[i].areaColisionDefaultY;
                }
            }
        }
    }

    public void obtenerObjetos(String nombre, Jugador e){ //Este método comprueba con qué item has interactuado y resuelve
                                                        //lo que tenga que acontecer según el item

        if (nombre.equals("Pez Globo")){
            e.tienePezGlobo = true;
            gamePanel.items[0] = null;
            gamePanel.reproducirSonido(1);
        }
    }

    public void checkNPC(Jugador e, Entidad[] target) {

        for (int i=0;i< target.length;i++){

            if (target[i] != null){

                //Conseguimos el área de colisión del jugador
                e.areaColision.x = e.xMundo + e.areaColision.x;
                e.areaColision.y = e.yMundo + e.areaColision.y;

                //Conseguimos el área de colisión de la entidad objetivo
                target[i].areaColision.x = target[i].xMundo + target[i].areaColision.x;
                target[i].areaColision.y = target[i].yMundo + target[i].areaColision.y;

                switch (e.apuntandoA){

                    case "arriba" -> {
                        if (e.areaColision.intersects(target[i].areaColision)){
                            target[i].interaccion("arriba");
                        }
                    }
                    case "abajo" -> {
                        if (e.areaColision.intersects(target[i].areaColision)){
                            target[i].interaccion("abajo");
                        }
                    }
                    case "izquierda" -> {
                        if (e.areaColision.intersects(target[i].areaColision)){
                            target[i].interaccion("izquierda");
                        }
                    }
                    case "derecha" -> {
                        if (e.areaColision.intersects(target[i].areaColision)){
                            target[i].interaccion("derecha");
                        }
                    }

                }
            }

        }

    }
}