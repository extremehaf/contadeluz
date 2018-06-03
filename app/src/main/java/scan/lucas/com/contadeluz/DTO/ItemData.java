package scan.lucas.com.contadeluz.DTO;

/**
 * Created by lucas on 02/06/2018.
 */

public class ItemData {

    String text;
    Integer imageId;

    public ItemData(String text, Integer imageId) {
        this.text = text;
        this.imageId = imageId;
    }

    public String getText() {
        return text;
    }

    public Integer getImageId() {
        return imageId;
    }
}