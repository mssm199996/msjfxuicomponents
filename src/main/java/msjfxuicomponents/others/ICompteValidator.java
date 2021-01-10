package msjfxuicomponents.others;

public interface ICompteValidator {

	public abstract ICompte isAccountValid(String username, String password);
	public abstract void onAttemptingToConnect(String username, String password);
}
