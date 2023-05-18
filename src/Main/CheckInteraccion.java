package Main;

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

                switch (e.apuntandoA){ //Igual que en las colisiones, se comprueba si está el jugador pegado al objeto.

                    case "arriba":
                        e.areaColision.y -= e.speed;
                        if (e.areaColision.intersects(gamePanel.items[i].areaColision)){
                            int index = i; //Se almacena el indice del objeto y se manda a otro método que lo manejará
                            obtenerObjetos(index, e);
                        }else {
                        }
                        break;
                    case "abajo":
                        e.areaColision.y += e.speed;
                        if (e.areaColision.intersects(gamePanel.items[i].areaColision)){
                            if (gamePanel.items[i].colision){ //Se comprueba si el objeto debe impedir el paso
                                int index = i; //Se almacena el indice del objeto y se manda a otro método que lo manejará
                                obtenerObjetos(index, e);
                            }else {
                            }
                        }
                        break;
                    case "izquierda":
                        e.areaColision.x -= e.speed;
                        if (e.areaColision.intersects(gamePanel.items[i].areaColision)){
                            if (gamePanel.items[i].colision){ //Se comprueba si el objeto debe impedir el paso
                                int index = i; //Se almacena el indice del objeto y se manda a otro método que lo manejará
                                obtenerObjetos(index, e);
                            }else {
                            }
                        }
                        break;
                    case "derecha":
                        e.areaColision.x += e.speed;
                        if (e.areaColision.intersects(gamePanel.items[i].areaColision)){
                            if (gamePanel.items[i].colision){ //Se comprueba si el objeto debe impedir el paso
                                int index = i; //Se almacena el indice del objeto y se manda a otro método que lo manejará
                                obtenerObjetos(index, e);
                            }else {
                            }
                        }
                        break;
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

    public void obtenerObjetos(int index, Jugador e){ //Este método comprueba con qué item has interactuado y resuelve
                                                        //lo que tenga que acontecer según el item
        if (index == 0){
            e.tienePezGlobo = true;
            gamePanel.items[0] = null;
        }

    }
}
