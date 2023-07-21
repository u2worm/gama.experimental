package ummisco.gama.chemmisol.units;

import msi.gama.precompiler.GamlAnnotations.constant;
import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.IConcept;
import msi.gaml.constants.GamlCoreUnits;

public interface ChemicalConstants {
	/** The CHEMISTRY concept. */
	public static final String CHEMISTRY = "chemistry";

	/** The PHYSICAL_UNIT concept. */
	public static final String PHYSICAL_UNIT = "physical_unit";

	/** The PHYSICS concept. */
	public static final String PHYSICS = "physics";

	/** The PHYSICAL constant category. */
	public static final String PHYSICAL = "Physical constants and units";

	/**
	 * Physical and chemical constants.
	 */

	/** The Constant u. */
	@constant (
	value = "u",
	altNames = { "Da", "dalton", "daltons" },
	category = { PHYSICAL },
	concept = { IConcept.DIMENSION, PHYSICAL_UNIT, IConcept.WEIGHT_UNIT },
		doc = @doc ("the unified atomic mass unit (or Dalton), usually used to express atomic masses."))
	double u = 1.66053906660e-27*GamlCoreUnits.kg;

	/** The Avogadro constant */
	@constant (
	value = "NA",
	altNames = { "avogadro_constant", "L" },
	category = { PHYSICAL },
	concept = { IConcept.CONSTANT, CHEMISTRY, PHYSICS },
		doc = @doc ("The Avogadro constant, notably used to define the mole unit."))
	double NA = 6.02214076e23;

}
