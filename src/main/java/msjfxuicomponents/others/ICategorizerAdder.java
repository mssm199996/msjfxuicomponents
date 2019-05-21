package msjfxuicomponents.others;

public interface ICategorizerAdder<T> {

	public T createEntity();

	public void insertEntity(T entity);
}
