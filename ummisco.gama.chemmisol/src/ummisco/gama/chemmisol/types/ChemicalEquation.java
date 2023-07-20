package ummisco.gama.chemmisol.types;

import java.util.LinkedList;
import java.util.List;

import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import ummisco.gama.chemmisol.Phase;

public class ChemicalEquation {
	public static class ChemicalEquationItem {
		private String component_name;
		private Phase phase;
		private int coefficient;

		public ChemicalEquationItem(String component_name, Phase phase, int coefficient) {
			this.component_name = component_name;
			this.phase = phase;
			this.coefficient = coefficient;
		}

		public String getName() {
			return component_name;
		}

		public Phase getPhase() {
			return phase;
		}

		public int getCoefficient() {
			return coefficient;
		}

		public void revertCoefficient() {
			this.coefficient = -this.coefficient;
		}
	}

	private List<ChemicalEquationItem> items;

	public ChemicalEquation() {
		this.items = new LinkedList<ChemicalEquationItem>();
	}

	public List<ChemicalEquationItem> getItems() {
		return items;
	}

	public ChemicalEquation add(ChemicalEquationItem item) {
		this.items.add(item);
		return this;
	}

	public ChemicalEquation addAll(List<ChemicalEquationItem> items) {
		this.items.addAll(items);
		return this;
	}

	public static void validate(ChemicalEquation chemical_equation, IScope scope) {
		int reactives = 0;
		int products = 0;

		for (ChemicalEquationItem item : chemical_equation.getItems()) {
			if (item.getCoefficient() < 0)
				products = products + item.getCoefficient();
			else
				reactives = reactives + item.getCoefficient();
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
