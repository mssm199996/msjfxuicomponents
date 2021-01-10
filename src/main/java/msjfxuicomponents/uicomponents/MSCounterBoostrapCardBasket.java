package msjfxuicomponents.uicomponents;

import msjfxuicomponents.others.IBootstrapCardIdentifiedEntity;

public class MSCounterBoostrapCardBasket<T extends IBootstrapCardIdentifiedEntity>
		extends MSBootstrapCardBasket<T, MSCounterBootstrapCard> {

	@Override
	public MSCounterBootstrapCard constructBootstrapCard() {
		MSCounterBootstrapCard msCounterBootstrapCard = new MSCounterBootstrapCard();
		
		return msCounterBootstrapCard;
	}
}
