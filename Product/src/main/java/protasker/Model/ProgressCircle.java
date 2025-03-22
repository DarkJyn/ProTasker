package protasker.Model;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class ProgressCircle extends Canvas {
    private double progress = 0.0; // Giá trị phần trăm (0.0 - 1.0)
    private final GraphicsContext gc;

    public ProgressCircle(double width, double height) {
        super(width + 50, height + 50);
        gc = getGraphicsContext2D();
        draw();
    }

    private void draw() {
        double size = Math.min(getWidth() - 50, getHeight()-50);
        double offsetX = 7;
        double offsetY = 7;
        double centerX = (getWidth() - 50) / 2 + offsetX;
        double centerY = (getHeight() - 50) / 2 + offsetY;
        double radius = size / 2 - 5;

        gc.clearRect(0, 0, getWidth(), getHeight());

        // Vẽ đường viền nhạt
        gc.setStroke(Color.web("#093959"));
        gc.setLineWidth(10);
        gc.strokeArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 90, 360, javafx.scene.shape.ArcType.OPEN);

        // Vẽ vòng tiến trình
        gc.setStroke(Color.web("#5484ff"));
        gc.setLineWidth(10);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.strokeArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 90, -progress * 360, javafx.scene.shape.ArcType.OPEN);

        // Hiển thị số %
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Plus Jakarta Sans Medium",16));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText((int) (progress * 100) + "%", centerX, centerY + 5);
    }

    public void setProgress(double newProgress) {
        newProgress = Math.max(0, Math.min(1, newProgress));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(20), new KeyValue(this.progressProperty(), newProgress))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private DoubleProperty progressProperty() {
        return new SimpleDoubleProperty(progress) {
            @Override
            protected void invalidated() {
                progress = get();
                draw();
            }
        };
    }
}
