package Main;

import Entidades.Entidad;
import Entidades.Jugador;
import Entidades.Mensaje;
import Items.Item;
import Tiles.TileManager;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //Esta clase es el root pane en el que se encuentran todos los elementos del juego. Todos los manejadores se inicializan
    //aquí y también aquí es donde se declara el reloj de juego.

    //Ajustes de la pantalla
    final int tamanyoSprites = 16; //16x16 píxeles.
    final int scale = 3; //16x16 es una resolución muy baja así que lo escalamos al triple de su tamaño para mostrarlo.
    public final int tamanyoFinalSprites = tamanyoSprites *scale; //48x48 píxeles.

    //Estas 2 variables deciden el número de tiles (48x48) que conforman la pantalla.
    public final int numMaxColumnas = 16;
    public final int numMaxFilas = 12;

    //Multiplicando estas filas y columnas por el tamaño de un tile obtenemos el largo y ancho.
    public final int anchoPantalla = tamanyoFinalSprites * numMaxColumnas;
    public final int altoPantalla = tamanyoFinalSprites * numMaxFilas;

    //Ajustes de los mapas:
    public final int maxMundoColumna = 32;
    public final int maxMundoFila = 18;

    int fps = 60; //Límite máximo de FPS que queremos que se reproduzcan

    //Clases que gestionan el sistema de juego
    InputsTeclado inputs = new InputsTeclado(this);
    Thread gameThread;
    Sonido musica = new Sonido();
    Sonido efectos = new Sonido();
    TileManager tileManager = new TileManager(this);
    public CheckColisiones checkColisiones = new CheckColisiones(this);
    AssetSetter assetSetter = new AssetSetter(this);
    public UserInterface ui = new UserInterface(this);
    public CheckInteraccion checkInteraccion = new CheckInteraccion(this);

    //Clases que gestionan las entidades e items
    public Jugador jugador = new Jugador(this,inputs);
    public Item[] items = new Item[10]; //Indica el número de objetos que podemos incluir en en pantalla a la vez. Ya que al pickear un objeto desaparece
    public Entidad[] entidades = new Entidad[10]; //Indica el número de entidades que pueden haber a la vez en el mapa. Igual que los objetos.
    public Mensaje msg = new Mensaje(this);

    //Game state
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    //Activamos esto con una tecla para ver las hitboxes
    public boolean toogleHitboxes = false;

    //Trackear que canción está sonando
    int sonandoAhora;

    //Constructor de ventanas de juego
    public GamePanel(){
        this.setPreferredSize(new Dimension(anchoPantalla,altoPantalla)); //Definir las dimensiones de la pantalla
        this.setBackground(Color.black); //Definir el color del fondo de la pantalla
        this.setDoubleBuffered(true); //Activar el doble buffer (rendimiento)
        this.addKeyListener(inputs); //Asociar nuestra clase inputsTeclado con la pantalla para asignar controles
        this.setFocusable(true); //Es necesario que este componente sea focuseable para poder registrar inputs
    }

    public void setUpGame(){ //Este método sirve para colocar en el juego ciertos elementos

        assetSetter.setItems();
        assetSetter.setNPC();
        sonandoAhora = reproducirMusica(0);
        gameState = playState;

    }

    public void iniciarRelojDeJuego(){ //Al llamar a este método comienza un loop que ejecuta el método run
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() { //Loop/reloj del juego
        while (gameThread != null){

            double intervaloEscritura = (double) 1000000000 /fps; // 1/60 segundos, se actualiza la pantalla 60 veces en un segundo.
            double siguienteEscritura = System.nanoTime() + intervaloEscritura; //Establecemos una espera de 1/60 segundos entre escritura y escritura.

            //Mientras se esté ejecutando este hilo, van a ocurrir 2 acciones de manera constante:
            //1. Actualizar la información del juego
            actualizar();

            //DEBUG
            //Con estas 2 líneas, se puede ver los frames de la canción (para fijar los loops)
            //int x = getFrames();
            //System.out.println(x);

            //2. Actualizar la pantalla (printear los sprites actualizados)
            repaint(); //Llamada al método paintComponent

            try {
                double esperaEscritura = siguienteEscritura - System.nanoTime(); // Cuando se acaba el repaint, determina cuanto tiempo falta hasta el próximo repaint
                esperaEscritura = esperaEscritura/1000000; //Pasamos los nanosegundos a milisegundos

                if (esperaEscritura < 0){ //Evita que el valor de la variable de espera pueda ser negativo
                    esperaEscritura = 0;
                }

                Thread.sleep((long) esperaEscritura); //Bloquea todas las acciones del loop hasta que acabe el tiempo de espera para la siguiente escritura

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void actualizar(){ //En este método vamos a registrar los datos de movimiento de los sprites por la pantalla
                                //para enviarlos al método paintComponent y que los actualice.

        if (gameState == playState){ //Solo se actualizan datos si el juego no está pausado.

            //Jugador
            jugador.actualizar(); //Obtener datos del jugador
            //NPCs
            for (int i = 0; i<entidades.length;i++){
                if (entidades[i] != null){
                    entidades[i].actualizar();
                }
            }

            //Por algún motivo, la música se reproduce siempre desde el punto de inicio y no donde se detuvo.
            //if (!musica.clip.isActive()){
            //    reproducirMusica(sonandoAhora);
            //}
        }
        if (gameState == pauseState){



            //detenerMusica();
        }
        if (gameState == dialogueState){

            ui.dibujarVentanaDialogo();

        }


    }
    public void paintComponent(Graphics g){

        //La clase Graphics2D extiende la clase Graphics y da más control sobre ellas para renderizar 2D.
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //DEBUG
        long comienzoRender = 0;
        comienzoRender = System.nanoTime();

        tileManager.dibujar(g2); //Dibujar el mapa. Siempre se dibujan los tiles por encima de las entidades y objetos.

        //Dibuja todos los items del array de items siempre que no sean nulos.
        for (int i=0;i< items.length;i++){
            if (items[i] != null){
                items[i].dibujar(g2, this);
            }
        }

        jugador.dibujar(g2); //Dibujar al jugador.

        //NPCs
        for (int i=0;i<entidades.length;i++){
            if (entidades[i] != null){
                entidades[i].dibujar(g2);
            }
        }

        ui.dibujar(g2); //Esto tiene que estar debajo de lo demás siempre. La interfaz lo último.

        //DEBUG
        long finRender = System.nanoTime();
        long tiempoRender = finRender - comienzoRender;
        //System.out.println("Tiempo de renderizado: " + tiempoRender);

        g2.dispose();
    }


    //Métodos para reproducir sonidos (de la clase Sonido)
    public int reproducirMusica(int i){

        musica.setFile(i);
        musica.play();
        if (i == 0){
            musica.loopMundo1();
        }

        return i;
    }

    public void detenerMusica(){

        musica.stop();

    }

    public void reproducirSonido(int i){

        efectos.setFile(i);
        efectos.play();

    }

    public int getFrames(){

        return musica.getFrames();
    }
}