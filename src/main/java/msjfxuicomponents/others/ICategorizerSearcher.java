package msjfxuicomponents.others;

import java.util.Collection;

import msdatabaseutils.ICategorizer;

public interface ICategorizerSearcher<T extends ICategorizer> {

	public abstract Collection<T> search(String value);
}
