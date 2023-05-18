package Items;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ItemPezGlobo extends Item{

    //Este item

    public ItemPezGlobo(){

        nombre = "Pez globo";
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/Pez_globo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        colision = true;

    }
}
