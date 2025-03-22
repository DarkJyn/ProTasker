package protasker.Model;

import com.google.gson.*;
import javafx.scene.image.ImageView;
import java.lang.reflect.Type;

public class ImageViewJson implements JsonSerializer<ImageView>, JsonDeserializer<ImageView> {
    @Override
    public JsonElement serialize(ImageView src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        // Lưu URL hình ảnh trong ImageView (hoặc bất kỳ thuộc tính nào bạn muốn)
        jsonObject.addProperty("Product/src/main/resources/ImageLoginScreen/logo.png", src.getImage().getUrl());
        return jsonObject;
    }

    @Override
    public ImageView deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String imageUrl = jsonObject.get("Product/src/main/resources/ImageLoginScreen/logo.png").getAsString();
        // Tạo ImageView mới với URL hình ảnh
        ImageView imageView = new ImageView(imageUrl);
        return imageView;
    }
}