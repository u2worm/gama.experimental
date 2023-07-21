package ummisco.gama.chemmisol.units;

import msi.gama.precompiler.GamlAnnotations.constant;
import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.IConcept;

public interface ChemicalUnits {
	/*
	 *
	 * Physical and chemical units.
	 */
	@constant (
	value = "mol",
	altNames = { "mole", "moles" },
	category = { ChemicalConstants.PHYSICAL },
	concept = { IConcept.DIMENSION, ChemicalConstants.PHYSICAL_UNIT },
	doc = { @doc ("mole: base unit for quantities of elementary entities. 1 mole contains 6.02214076e23 elementary entities.") })
	double mol = 1;

	@constant (
	value = "entities",
	altNames = { "entity" },
	category = { ChemicalConstants.PHYSICAL },
	concept = { IConcept.DIMENSION, ChemicalConstants.PHYSICAL_UNIT },
	doc = { @doc ("amount of substance unit, or quantity of elementar y entities") })
	double entities = 1/ChemicalConstants.NA;

}
