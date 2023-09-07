package ummisco.gama.chemmisol.types;

import java.util.LinkedList;
import java.util.List;

import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import ummisco.gama.chemmisol.Reagent;

public class ChemicalEquation {
	private List<Reagent> reagents;

	public ChemicalEquation() {
		this.reagents = new LinkedList<Reagent>();
	}

	public List<Reagent> getReagents() {
		return reagents;
	}

	public ChemicalEquation add(Reagent reagent) {
		this.reagents.add(reagent);
		return this;
	}

	public ChemicalEquation addAll(List<Reagent> reagents) {
		this.reagents.addAll(reagents);
		return this;
	}

	public static void validate(ChemicalEquation chemical_equation, IScope scope) {
		int reactives = 0;
		int products = 0;

		for (Reagent reagent : chemical_equation.getReagents()) {
			if (reagent.getCoefficient() < 0)
				products = products + reagent.getCoefficient();
			else
				reactives = reactives + reagent.getCoefficient();
		}
		if (reactives == 0) {
			throw GamaRuntimeException.error(
					"Missing products or reactives. " +
					"The chemical equation can be specified as " +
					"<coef> * <reactive> + ... = <coef> * <product> + ...",
					scope
					);
		}
	}
}
