package msjfxuicomponents.uicomponents;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import com.jfoenix.controls.JFXMasonryPane;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import msjfxuicomponents.others.IBootstrapCardIdentifiedEntity;

public abstract class MSBootstrapCardBasket<T extends IBootstrapCardIdentifiedEntity, S extends MSBootstrapCard>
		extends JFXMasonryPane {

	private List<T> elements = new LinkedList<>();
	private Map<Integer, S> displayedCards = new HashMap<>();
	private Map<S, T> displayedCardsToEntities = new HashMap<>();
	private Map<T, S> basket = new HashMap<>();

	private BiConsumer<T, S> onBasketChanged;

	private S selectedCard;
	private Background lastSelectedCardBackground, cardSelectionBackground = new Background(
			new BackgroundFill(Color.rgb(0, 0, 255, 0.2), CornerRadii.EMPTY, Insets.EMPTY));

	// ---------------------------------------------------------------------------------------------------------

	public void addElements(Collection<T> elements) {
		for (T element : elements)
			this.addMSBootstrapCard(element);

		this.elements.addAll(elements);
	}

	public void clearDisplayedElements() {
		for (T element : this.elements)
			this.removeMSBootstrapCard(element);

		this.elements.clear();
	}

	public void clearBasket() {
		this.basket.clear();

		List<T> intermediate = new ArrayList<>(this.elements.size());

		for (T element : this.elements)
			intermediate.add(element);

		this.clearDisplayedElements();
		this.addElements(intermediate);
	}

	// ---------------------------------------------------------------------------------------------------------

	public void addMSBootstrapCard(T element) {
		boolean alreadyAdded = this.displayedCards.containsKey(element.getBoostrapCardId());

		if (!alreadyAdded) {
			S card;

			boolean itemAlreadyInBasket = this.basket.containsKey(element);

			if (!itemAlreadyInBasket) {
				S recoveryCard = this.constructBootstrapCard();
				recoveryCard.setOnCardActionTriggered(() -> {
					if (!recoveryCard.isAvailable())
						this.basket.remove(element);
					else {
						this.basket.put(element, recoveryCard);
					}

					if (this.onBasketChanged != null)
						this.onBasketChanged.accept(element, recoveryCard);
				});

				recoveryCard.setOnMouseClicked(e -> {
					if (this.selectedCard != null)
						this.selectedCard.setBackground(this.lastSelectedCardBackground);

					this.selectedCard = recoveryCard;
					this.lastSelectedCardBackground = this.selectedCard.getBackground();
					this.selectedCard.setBackground(this.cardSelectionBackground);
				});

				this.draw(element, recoveryCard);

				this.basket.put(element, recoveryCard);

				card = recoveryCard;
			} else
				card = this.basket.get(element);

			this.displayedCards.put(element.getBoostrapCardId(), card);
			this.displayedCardsToEntities.put(card, element);
			this.getChildren().add(card);
		}
	}

	public void removeMSBootstrapCard(T element) {
		S msBootstrapCard = this.displayedCards.get(element.getBoostrapCardId());

		if (msBootstrapCard != null) {
			this.displayedCards.remove(element.getBoostrapCardId());
			this.displayedCardsToEntities.remove(msBootstrapCard);
			this.getChildren().remove(msBootstrapCard);
		}
	}

	// ---------------------------------------------------------------------------------------------------------

	public Map<Integer, S> getDisplayedCards() {
		return displayedCards;
	}

	public void setDisplayedCards(Map<Integer, S> displayedCards) {
		this.displayedCards = displayedCards;
	}

	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}

	public Map<T, S> getBasket() {
		return basket;
	}

	public void setBasket(Map<T, S> basket) {
		this.basket = basket;
	}

	public BiConsumer<T, S> getOnBasketChanged() {
		return onBasketChanged;
	}

	public void setOnBasketChanged(BiConsumer<T, S> onBasketChanged) {
		this.onBasketChanged = onBasketChanged;
	}

	public T getSelectedEntity() {
		if (this.getSelectedCard() != null)
			return this.displayedCardsToEntities.get(this.getSelectedCard());

		return null;
	}

	public S getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(S selectedCard) {
		this.selectedCard = selectedCard;
	}

	public Background getCardSelectionBackground() {
		return cardSelectionBackground;
	}

	public void setCardSelectionBackground(Background cardSelectionBackground) {
		this.cardSelectionBackground = cardSelectionBackground;
	}

	public void draw(T entity, S card) {
		card.setHeaderText(entity.getBoostrapCardHeader());
		card.setSubHeaderText(entity.getBoostrapCardSubHeader());

		if (entity.getBoostrapCardImageByteArray() != null) {
			Image image = new Image(new ByteArrayInputStream(entity.getBoostrapCardImageByteArray()));
			card.setImageContent(image);
		}
	}

	public abstract S constructBootstrapCard();
}
