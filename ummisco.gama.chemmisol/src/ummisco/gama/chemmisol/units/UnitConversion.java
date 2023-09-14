package ummisco.gama.chemmisol.units;

import java.util.HashMap;
import java.util.Map;

import msi.gaml.constants.GamlCoreUnits;
import ummisco.gama.chemmisol.Phase;

public class UnitConversion {
	
	// Base units used by the chemmisol library
	private static final Map<Phase, Double> base_concentration_units;
	
	static {
		base_concentration_units =  new HashMap<Phase, Double>();
		base_concentration_units.put(Phase.MINERAL, 1.0);
		base_concentration_units.put(Phase.SOLVENT, 1.0);
		// Aqueous case: (0.1mol/l specified in GAML must correspond to concentration=0.1 in chemmisol)
		base_concentration_units.put(Phase.AQUEOUS, ChemicalUnits.mol / GamlCoreUnits.l);
	}

	public static double convertConcentration(Phase phase, double concentration) {
		return concentration / base_concentration_units.get(phase);
	}
	
	public static double convertSolidConcentration(double solid_concentration) {
		return solid_concentration / (GamlCoreUnits.gram / GamlCoreUnits.l);
	}
	
	public static double convertSpecificSurfaceArea(double specific_surface_area) {
		return specific_surface_area / (GamlCoreUnits.m2 / GamlCoreUnits.gram);
	}
	
	public static double convertSiteConcentration(double site_concentration) {
		return site_concentration / (ChemicalUnits.mol / GamlCoreUnits.m2);
	}
}
