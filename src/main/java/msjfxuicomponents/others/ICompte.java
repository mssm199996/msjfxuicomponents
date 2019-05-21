package msjfxuicomponents.others;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import DomainModel.Droit;

public interface ICompte {

	public abstract String getUsername();

	public abstract String getPassword();

	public abstract void setDateDerniereConnexion(LocalDate date);

	public abstract void setHeureDerniereConnexion(LocalTime heure);

	public abstract Boolean isAdmin();

	public abstract List<Droit> getDroits();
}
