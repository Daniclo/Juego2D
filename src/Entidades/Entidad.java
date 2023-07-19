package Entidades;

import Main.GamePanel;
import Main.UtilityTool;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Entidad {

    //Esta clase será la clase padre de las clases del jugador, npcs y enemigos

    GamePanel gamePanel;
    public String nombre;
    public int xMundo, yMundo; //Posición de la entidad en el mundo
    public int speed; //Velocidad a la que se desplaza la entidad
    public BufferedImage arriba1, arriba2, abajo1, abajo2, izquierda1, izquierda2, derecha1, derecha2, arriba3, abajo3, izquierda3, derecha3; //Almacena las animaciones de movimiento de las entidades
    public String apuntandoA; //Almacena la información de hacia donde está mirando la entidad
    public int contadorSprite = 0; //Este contador permite cambiar de sprites cada cierto tiempo
    public int spriteActual = 1; //Indica que sprite está usando la entidad actualmente
    public Rectangle areaColision = new Rectangle(8,+16,32,32); //Define el area de colisiones de la entidad para determinar cuando choca contra otros elementos
    public int areaColisionDefaultX, areaColisionDefaultY; //Area de colisión de la entidad
    public boolean colisionOn = false; //Determina si colisiona o no
    public int contadorAcciones = 0; //Esta variable permite definir el tiempo que tardará un npc en hacer su próxima acción.
    public boolean invencibleOn = false; //Indica si los frames de invencibilidad están activos.
    public int contadorInvencible = 0; //Indica cuantos ticks quedan para que acaben los frames de invencibilidad.
    public int tipoEntidad; //0 = jugador, 1 = npc, 2 = enemigo.
    public boolean muerte = false; //Determina si la entidad debe desaparecer.
    public int contadorMuerte = 0; //Contador para la animación de muerte de los enemigos.
    public boolean finAnimacionMuerte = false; //Cuando esta variable es true, elimina la entidad porque ya ha finalizado la animación de muerte.
    public Proyectil proyectil;

    //DIÁLOGOS
    String[] dialogos = new String[20]; //Este array define el número de diálogos que puede tener un npc.
    int indexDialogo = 0;
    boolean dialogoAcabado;

    //Status de la entidad
    public int vidaMaxima;
    public int vida;
    public int ataque;

    public Entidad(GamePanel gamePanel){

        this.gamePanel = gamePanel;

    }


    public BufferedImage setUpSprite(String ruta){ //Devuelve la imagen escalada por medio del método de uTool.

        UtilityTool uTool = new UtilityTool();
        BufferedImage imagen = null;

        try {

            imagen = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(ruta+".png")));
            imagen = uTool.escalarImagen(imagen,gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return imagen;

    }

    public void setAccion(){ //Este método define los movimientos de la entidad.

        Random random = new Random();
        int i = random.nextInt(100)+1;

        if (apuntandoA.equals("arriba")){
            if (i <= 1){
                yMundo -= speed;
            }
        }
        if (apuntandoA.equals("abajo")){
            if (i > 1 && i <= 3){
                yMundo += speed;
            }
        }
        if (apuntandoA.equals("izquierda")){
            if (i > 3 && i <= 5){
                xMundo -= speed;
            }
        }
        if (apuntandoA.equals("derecha")){
            if (i > 5 && i <= 7){
                xMundo += speed;
            }
        }

    }

    public void interaccion(String apuntandoA){ //Este método define las acciones de la entidad al interactuar con ella

        this.apuntandoA = apuntandoA;
        //DEBUG
        //System.out.println("Interacción");
        gamePanel.gameState = gamePanel.dialogueState;
    }

    public void hablar(){ //Este método muestra uno por uno los diálogos del NPC y se asegura de que el NPC siempre muestre
                            //en bucle el último diálogo definido una vez que los has leído todos.

        //Esta línea arregla el bug de pulsar demasiado fuerte la E.
        gamePanel.jugador.inputs.zPressed = false;
        if (dialogos[indexDialogo] != null){
            gamePanel.ui.dialogoActual = dialogos[indexDialogo];
            indexDialogo++;
            if (indexDialogo>=this.dialogos.length-1){
                indexDialogo = this.dialogos.length-1;
                gamePanel.ui.dialogoActual = dialogos[indexDialogo];
            }
        }

    }

    public void actualizar(){ //Actualiza la información de la entidad

        setAccion();

        colisionOn = false;
        gamePanel.checkColisiones.checkTile(this);
        gamePanel.checkColisiones.checkItem(this);
        gamePanel.checkColisiones.checkEntitdad(this, gamePanel.npcs);
        gamePanel.checkColisiones.checkEntitdad(this, gamePanel.enemigos);
        gamePanel.checkColisiones.checkJugador(this,gamePanel.jugador);

        if (!colisionOn){
            switch (apuntandoA){
                case "arriba" -> yMundo -= speed;
                case "abajo" -> yMundo += speed;
                case "izquierda" -> xMundo -= speed;
                case "derecha" -> xMundo += speed;
            }
        }

        contadorSprite++; //Esto nos permite cambiar al siguiente sprite después de cada movimiento para simular la animación de caminar
        if (contadorSprite > 12){ //Este número determina la velocidad a la que cambian los sprites.
            if (spriteActual == 1){
                spriteActual = 2;
            }else if (spriteActual == 2){
                spriteActual = 1;
            }
            contadorSprite = 0;
        }

        //Muerte:
        if (this != gamePanel.jugador){
            if (vida == 0){
                muerte = true;
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

    }

    public void dibujar(Graphics2D g2) { //Dibuja a la entidad

        //Utilizamos la x y la y en el mundo para calcular la posición en pantalla
        int xCamara = xMundo - gamePanel.jugador.xMundo + gamePanel.jugador.xCamara;
        int yCamara = yMundo - gamePanel.jugador.yMundo + gamePanel.jugador.yCamara;

        //Este bucle hace que solo se dibujen los NPCs que están en la pantalla.
        //Sirve para optimizar el renderizado del mapa y no cargar NPCs que estén fuera.
        if (xMundo + (gamePanel.tamanyoFinalSprites*2) > gamePanel.jugador.xMundo - gamePanel.jugador.xCamara &&
                xMundo - (gamePanel.tamanyoFinalSprites*2)< gamePanel.jugador.xMundo + gamePanel.jugador.xCamara &&
                yMundo + (gamePanel.tamanyoFinalSprites*2)> gamePanel.jugador.yMundo - gamePanel.jugador.yCamara &&
                yMundo - (gamePanel.tamanyoFinalSprites*2)< gamePanel.jugador.yMundo + gamePanel.jugador.yCamara){

            BufferedImage image = null;
            switch (apuntandoA) {
                case "arriba" -> {  //Comprueba cual de los 2 sprites tiene que usar ahora y lo asigna en cada actualización para cada dirección.

                    if (spriteActual == 1) {
                        image = arriba1;
                    }
                    if (spriteActual == 2) {
                        image = arriba2;
                    }
                    if (spriteActual == 3) {
                        image = arriba1;
                    }
                }
                case "abajo" -> {

                    if (spriteActual == 1) {
                        image = abajo1;
                    }
                    if (spriteActual == 2) {
                        image = abajo2;
                    }
                    if (spriteActual == 3) {
                        image = abajo1;
                    }
                }
                case "izquierda" -> {

                    if (spriteActual == 1) {
                        image = izquierda1;
                    }
                    if (spriteActual == 2) {
                        image = izquierda2;
                    }

                    if (spriteActual == 3) {
                        image = izquierda1;
                    }
                }
                case "derecha" -> {

                    if (spriteActual == 1) {
                        image = derecha1;
                    }
                    if (spriteActual == 2) {
                        image = derecha2;
                    }
                    if (spriteActual == 3) {
                        image = derecha1;
                    }
                }
            }
            //DEBUG
            //if (!invencibleOn){
            //    System.out.println("No transparente");
            //}else {
            //    System.out.println("transparente");
            //}
            if (invencibleOn){ //Efecto visual de invencibilidad
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
            if (muerte){
                morir(g2);
            }
            g2.drawImage(image, xCamara, yCamara, gamePanel.tamanyoFinalSprites, gamePanel.tamanyoFinalSprites, null);
            //Resetear efecto visual de invencibilidad
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            //DEBUG
            if (gamePanel.toogleHitboxes){
                g2.setColor(Color.red);
                g2.drawRect(xCamara+8,yCamara+16,32,32);
            }
        }
    }

    public void morir(Graphics2D g2){

        contadorMuerte++;

        for (int i = 0; i < contadorMuerte ; i++){
            if (contadorMuerte <= 5){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            }
            if (contadorMuerte > 5 && contadorMuerte <= 10){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }
            if (contadorMuerte > 10 && contadorMuerte <= 15){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            }
            if (contadorMuerte > 15 && contadorMuerte <= 20){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }
            if (contadorMuerte > 20 && contadorMuerte <= 25){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            }
            if (contadorMuerte > 25 && contadorMuerte <= 30){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }
            if (contadorMuerte > 30 && contadorMuerte <= 35){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            }
            if (contadorMuerte > 35 && contadorMuerte <= 40){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }
            if (contadorMuerte > 40){
                finAnimacionMuerte = true;
            }

        }
        //DEBUG
        //System.out.println(contadorMuerte);

    }

}