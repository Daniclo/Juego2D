package Entidades;

import Main.GamePanel;
import Main.InputsTeclado;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Jugador extends Entidad{

    //Esta clase controla lo relacionado con el personaje protagonista del juego, manejado por el jugador, y sus
    //interacciones con el resto del juego.

    public InputsTeclado inputs;
    public final int xCamara;
    public final int yCamara;

    //ANIMACIONES RODAR

    BufferedImage rodarAbajo1,rodarAbajo2,rodarAbajo3,rodarArriba1,rodarArriba2,rodarArriba3,rodarIzquierda1,rodarIzquierda2,rodarIzquierda3,rodarDerecha1,rodarDerecha2,rodarDerecha3;

    //OBJETOS QUE PUEDE CONSEGUIR EL JUGADOR
    public boolean tienePezGlobo = false;
    public boolean puedeRodar = true;
    public int contadorRodar = 0;

    //COOLDOWN BOLA DE NIEVE
    public int contadorProyectil = 80;

    public Jugador(GamePanel gamePanel, InputsTeclado inputs){ //Instanciamos los inputs y el panel de juego para poder
                                                                //utilizarlos en esta clase

        super(gamePanel); //Constructor de entidad
        this.inputs = inputs;

        xCamara = gamePanel.anchoPantalla/2 - (gamePanel.tamanyoFinalSprites/2);
        yCamara = gamePanel.altoPantalla/2 - (gamePanel.tamanyoFinalSprites/2);

        areaColision = new Rectangle(8,+16,32,32); //Estos valores definen el area de colisión del jugador
        //Los valores del área de colisión van a alterar, así que los almacenamos en otras variables para poder acceder de nuevo a ellos
        areaColisionDefaultX = areaColision.x;
        areaColisionDefaultY = areaColision.y;

        valoresPorDefecto(); //Cuando se construye un objeto jugador, se establecen estos valores
        getSpritesJugador(); //Se llama al método cada vez que se construye un jugador para asignarle sprites

        tipoEntidad = 0; //Es un jugador
    }

    public void valoresPorDefecto(){ //Definimos una serie de atributos iniciales para el jugador
        xMundo = 150;
        yMundo = 150;
        speed = 4;
        apuntandoA = "abajo";
        vidaMaxima = 5;
        vida = vidaMaxima;
        ataque = 1;
        proyectil = new AtaqueHielo(gamePanel);
    }

    public void getSpritesJugador(){ //Inicializamos los sprites del jugador

        //Sprites para andar y correr sin pez globo
        abajo1 = setUpSprite("/jugador/andar/PJ_Abajo_1");
        abajo2 = setUpSprite("/jugador/andar/PJ_Abajo_2");
        arriba1 = setUpSprite("/jugador/andar/PJ_Arriba_1");
        arriba2 = setUpSprite("/jugador/andar/PJ_Arriba_2");
        izquierda1 = setUpSprite("/jugador/andar/PJ_Izquierda_1");
        izquierda2 = setUpSprite("/jugador/andar/PJ_Izquierda_2");
        derecha1 = setUpSprite("/jugador/andar/PJ_Derecha_1");
        derecha2 = setUpSprite("/jugador/andar/PJ_Derecha_2");

        //Sprites para rodar una vez tienes el pez globo
        rodarAbajo1 = setUpSprite("/jugador/rodar/PJ_rodar_abajo_1");
        rodarAbajo2 = setUpSprite("/jugador/rodar/PJ_rodar_abajo_2");
        rodarAbajo3 = setUpSprite("/jugador/rodar/PJ_rodar_abajo_3");
        rodarArriba1 = setUpSprite("/jugador/rodar/PJ_rodar_arriba_1");
        rodarArriba2 = setUpSprite("/jugador/rodar/PJ_rodar_arriba_2");
        rodarArriba3 = setUpSprite("/jugador/rodar/PJ_rodar_arriba_3");
        rodarIzquierda1 = setUpSprite("/jugador/rodar/PJ_rodar_izquierda_1");
        rodarIzquierda2 = setUpSprite("/jugador/rodar/PJ_rodar_izquierda_2");
        rodarIzquierda3 = setUpSprite("/jugador/rodar/PJ_rodar_izquierda_3");
        rodarDerecha1 = setUpSprite("/jugador/rodar/PJ_rodar_derecha_1");
        rodarDerecha2 = setUpSprite("/jugador/rodar/PJ_rodar_derecha_2");
        rodarDerecha3 = setUpSprite("/jugador/rodar/PJ_rodar_derecha_3");
    }

    @Override
    public void actualizar(){

        //MOVIMIENTO
        if (inputs.upPressed || inputs.downPressed || inputs.rightPressed || inputs.leftPressed){ //Este if hace que las animaciones de movimiento no se activen mientras
                                                                                                    //el personaje está quieto.

            if (inputs.upPressed){ //Apuntar arriba. Comprueba que no hay colisiones antes de avanzar
                apuntandoA = "arriba";
            }
            if (inputs.downPressed){ //Apuntar abajo. Comprueba que no hay colisiones antes de avanzar
                apuntandoA = "abajo";
            }
            if (inputs.leftPressed){ //Apuntar izquierda. Comprueba que no hay colisiones antes de avanzar
                apuntandoA = "izquierda";
            }
            if (inputs.rightPressed){ //Apuntar derecha. Comprueba que no hay colisiones antes de avanzar
                apuntandoA = "derecha";
            }
            if (inputs.spacePressed){ //Correr más rápido al pulsar espacio.
                speed = 7;
            }else {
                speed = 4;
            }

            //DEBUG
            //System.out.println("x: " + xMundo);
            //System.out.println("y: " + yMundo);

            //Colisiones
            colisionOn = false;

            gamePanel.checkColisiones.checkTile( this); //Le pasamos esta entidad (en este caso el jugaddor) al check de colisiones de tiles en cada actualización

            //Le pasamos esta entidad al check de colisiones de objetos.
            gamePanel.checkColisiones.checkItem(this);

            //Comprobamos colisiones con npcs.
            gamePanel.checkColisiones.checkEntitdad(this, gamePanel.npcs);

            //Comprobamos colisiones con enemigos.
            gamePanel.checkColisiones.checkEntitdad(this, gamePanel.enemigos);

            if (!colisionOn){ //Solo habilitamos que se mueva el jugador mientras que el valor de colisión sea falso
                if (inputs.upPressed){
                    yMundo -= speed;
                }
                if (inputs.downPressed){
                    yMundo += speed;
                }
                if (inputs.leftPressed){
                    xMundo -= speed;
                }
                if (inputs.rightPressed){
                    xMundo += speed;
                }
            }

            if (!tienePezGlobo){ //Este if cicla las animaciones más rápido cuando no tienes el pez globo. Es para arreglar un bug
                contadorSprite++;
            }
            contadorSprite++; //Esto nos permite cambiar al siguiente sprite después de cada movimiento para simular la animación de caminar
            if (contadorSprite > 12){ //Este número determina la velocidad a la que cambian los sprites.
                if (spriteActual == 1){
                    spriteActual = 2;
                }else if (spriteActual == 2){
                    spriteActual = 3;
                }else if (spriteActual == 3){
                    spriteActual = 1;
                }
                contadorSprite = 0;
            }
        }

        //Gestión de bolas de hielo
        if (inputs.xPressed && contadorProyectil >= 80){
            proyectil.set(xMundo, yMundo, apuntandoA, false, this);
            gamePanel.proyectiles.add(proyectil);
            gamePanel.reproducirSonido(4);
            contadorProyectil = 0;
        }


        //Interacciones
        if (inputs.zPressed){

            int item = gamePanel.checkInteraccion.checkItem(this);
            //DEBUG
            //System.out.println(item);
            if (item != 999){

                switch (item) {
                    case 0 -> gamePanel.msg.mostrarMensaje(0);
                    case 1,2,3,4 -> {
                        if (!tienePezGlobo){
                            gamePanel.msg.mostrarMensaje(1);
                        }
                    }
                }

            }
            int npc = gamePanel.checkInteraccion.checkNPC(this, gamePanel.npcs);
            //DEBUG
            //System.out.println(npc);

            if (npc != 999){
                gamePanel.npcs[npc].hablar();
            }
        }

        //Invencibilidad
        if (invencibleOn){
            contadorInvencible++;
            if (contadorInvencible>60){
                invencibleOn = false;
                contadorInvencible = 0;
            }
        }

        //Cooldown ataque
        if (!puedeRodar){
            contadorRodar++;
            if (contadorRodar>250){
                puedeRodar = true;
                contadorRodar = 0;
            }
        }

    }
    public void dibujar(Graphics2D g2){ //Asignamos los sprites adecuados según la dirección en cada actualización

        BufferedImage image = null;
        switch (apuntandoA) {
            case "arriba" -> {//Comprueba cual de los 2 sprites tiene que usar ahora y lo asigna en cada actualización para cada dirección.
                                //Además, si tienes pez globo y estás corriendo cambia los sprites.

                image = getImage(image, rodarArriba1, rodarArriba2, rodarArriba3, arriba1, arriba2);
            }
            case "abajo" -> {
                image = getImage(image, rodarAbajo1, rodarAbajo2, rodarAbajo3, abajo1, abajo2);
            }
            case "izquierda" -> {
                image = getImage(image, rodarIzquierda1, rodarIzquierda2, rodarIzquierda3, izquierda1, izquierda2);
            }
            case "derecha" -> {
                image = getImage(image, rodarDerecha1, rodarDerecha2, rodarDerecha3, derecha1, derecha2);
            }
        }
        if (invencibleOn){ //Efecto visual de invencibilidad
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, xCamara, yCamara, null);
        //Resetear efecto visual de invencibilidad
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG
        if (gamePanel.toogleHitboxes){
            g2.setColor(Color.red);
            g2.drawRect(xCamara+8,yCamara+16,32,32);
        }

    }

    private BufferedImage getImage(BufferedImage image, BufferedImage rodarArriba1, BufferedImage rodarArriba2, BufferedImage rodarArriba3, BufferedImage arriba1, BufferedImage arriba2) {
        if (tienePezGlobo && inputs.spacePressed && puedeRodar){
            if (spriteActual == 1){
                image = rodarArriba1;
            }
            if (spriteActual == 2){
                image = rodarArriba2;
            }
            if (spriteActual == 3){
                image = rodarArriba3;
            }
        }else {
            if (spriteActual == 1) {
                image = arriba1;
            }
            if (spriteActual == 2) {
                image = arriba2;
            }
            if (spriteActual == 3){
                image = arriba1;
            }
        }
        return image;
    }

}