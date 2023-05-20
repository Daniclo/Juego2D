package Items;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ItemPezVida extends Item{

    public ItemPezVida(){

        nombre = "Pez"; //FALTA ESCALAR LOS SPRITES
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/Pez_completo.png")));
            sprite2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/Pez_vacio.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        colision = false;

    }
}
