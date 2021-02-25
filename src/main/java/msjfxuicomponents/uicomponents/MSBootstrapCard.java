package msjfxuicomponents.uicomponents;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.Random;

public abstract class MSBootstrapCard extends VBox {

    private static Random RANDOM = new Random();

    private ImageView image;
    private Text header, subHeader;

    private VBox textsContainer;
    private HBox centerContainer, imageContainer;

    private int maxImageWidth = 200;
    private int maxImageHeight = 200;

    private Color borderColor = Color.STEELBLUE;
    private Runnable onCardActionTriggered;

    public MSBootstrapCard() {
        super();
        this.initCard();
        this.initEffects();
    }

    public void initEffects() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setColor(Color.GRAY);

        BackgroundFill backgroundFill = new BackgroundFill(this.generateRandomBrightColor(), CornerRadii.EMPTY,
                Insets.EMPTY);
        BorderStroke borderStroke = new BorderStroke(this.borderColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(2.0));

        this.setEffect(dropShadow);
        this.setBackground(new Background(backgroundFill));
        this.setBorder(new Border(borderStroke));
    }

    public void initCard() {
        this.header = new Text("Header");
        this.header.setFill(Color.BLACK);
        this.header.getStyleClass().add("msbootstrapcard-header");

        this.subHeader = new Text("Sub header");
        this.subHeader.getStyleClass().add("msbootstrapcard-subheader");

        this.image = new ImageView();
        this.image.getStyleClass().add("msbootstrapcard-image");
        this.image.setPreserveRatio(true);

        this.imageContainer = new HBox();
        this.imageContainer.setPadding(new Insets(10.0, 0.0, 10.0, 0.0));
        this.imageContainer.getChildren().addAll(this.image);
        this.imageContainer.getStyleClass().add("msbootstrapcard-image-container");
        this.imageContainer.setAlignment(Pos.CENTER);

        this.textsContainer = new VBox();
        this.textsContainer.setSpacing(5.0);
        this.textsContainer.getChildren().addAll(this.header, this.subHeader);

        this.centerContainer = new HBox();
        this.centerContainer.setPadding(new Insets(0.0, 10.0, 10.0, 10.0));
        this.centerContainer.setAlignment(Pos.BOTTOM_CENTER);
        this.centerContainer.setSpacing(15.0);
        this.centerContainer.getChildren().add(this.textsContainer);

        HBox.setHgrow(this.textsContainer, Priority.ALWAYS);
        VBox.setVgrow(this.imageContainer, Priority.ALWAYS);

        Node bottomRightMostComponent = this.getBottomRightMoseComponent();

        if (bottomRightMostComponent != null)
            this.centerContainer.getChildren().add(bottomRightMostComponent);

        this.getChildren().clear();
        this.getChildren().addAll(this.imageContainer, this.centerContainer);
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public Text getHeader() {
        return header;
    }

    public void setHeader(Text header) {
        this.header = header;
    }

    public Text getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(Text subHeader) {
        this.subHeader = subHeader;
    }

    public VBox getTextsContainer() {
        return textsContainer;
    }

    public void setTextsContainer(VBox textsContainer) {
        this.textsContainer = textsContainer;
    }

    public Image getImageContent() {
        return this.image.getImage();
    }

    public void setImageContent(Image imageContent) {
        this.image.setImage(imageContent);
        this.image.setFitWidth(Math.min(this.maxImageWidth, imageContent.getWidth()));
        this.image.setFitHeight(Math.min(this.maxImageHeight, imageContent.getHeight()));

        this.imageContainer.setMinSize(this.image.getFitWidth(), this.image.getFitHeight());
    }

    public String getHeaderText() {
        return this.header.getText();
    }

    public void setHeaderText(String headerText) {
        this.header.setText(headerText);
    }

    public String getSubHeaderText() {
        return this.subHeader.getText();
    }

    public void setSubHeaderText(String subHeaderText) {
        this.subHeader.setText(subHeaderText);
    }

    public int getMaxImageWidth() {
        return maxImageWidth;
    }

    public void setMaxImageWidth(int maxImageWidth) {
        this.maxImageWidth = maxImageWidth;
    }

    public int getMaxImageHeight() {
        return maxImageHeight;
    }

    public void setMaxImageHeight(int maxImageHeight) {
        this.maxImageHeight = maxImageHeight;
    }

    public Runnable getOnCardActionTriggered() {
        return onCardActionTriggered;
    }

    public void setOnCardActionTriggered(Runnable onCardActionTriggered) {
        this.onCardActionTriggered = onCardActionTriggered;
    }

    private Paint generateRandomBrightColor() {
        final int offset = 215;
        final int bound = 256 - offset;

        int r = offset + MSBootstrapCard.RANDOM.nextInt(bound);
        int g = offset + MSBootstrapCard.RANDOM.nextInt(bound);
        int b = offset + MSBootstrapCard.RANDOM.nextInt(bound);

        return Color.rgb(r, g, b);
    }

    protected Paint generateRandomDarkColor() {
        return Color.hsb(MSBootstrapCard.RANDOM.nextInt(360), 1.0, 1.0).darker();
    }

    protected abstract Node getBottomRightMoseComponent();

    protected abstract boolean isAvailable();
}
