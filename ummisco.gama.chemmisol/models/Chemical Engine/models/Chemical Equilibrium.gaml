/**
* Name: chemmisoltest
* Based on the internal skeleton template. 
* Author: pbreugno
* Tags: 
*/
model chemmisoltest

global {

	init {
	// Creates a single agent that embeds a chemical system
		create ChemicalAgent number: 1;
	}
	
	/**
	 * Returns 0.0 when v=0.0, useful for display purpose.
	 */
	float log0 (float v) {
		if (v > 0.0) {
			return log(v);
		}

		return 0.0;
	}
}

/**
 * An agent that embeds a chemical system.
 */
species ChemicalAgent control: chemical {
// Specifies chemical components that should be tracked as agent attributes
	chemical_component H;
	chemical_component Na <- chemical_component(0.1 #mol / #l);
	chemical_component Cl <- chemical_component(0.1 #mol / #l);
	chemical_component PO4 <- chemical_component(6.7e-6 #mol / #l);
	// Species produced by reactions, that should also be tracked
	chemical_species NaCl;
	chemical_species NaOH;
	chemical_species H2PO4;
	// Init pH
	float ph <- 7.0;

	// Declares the chemical system. The pH facet takes two arguments: the pH value and the chemical component that represents the H+ quantity.
	chemical_system ph: [ph, H] {
		// Components and species can also be declared locally
		chemical_component H2O <- chemical_component([phase::SOLVENT]);
		chemical_species OH;
		// Chemical reactions that produced the corresponding species.
		// Notice that each species (OH, NaCl, NaOH, H2PO4) must be expressed from declared chemical components (H, Na, Cl, PO4, H2O).
		reaction "OH" chemical_equation: H2O = H + OH log_k: -13.997;
		reaction "NaCl" chemical_equation: Cl + Na = NaCl log_k: -0.3;
		reaction "NaOH" chemical_equation: Na + H2O = NaOH + H log_k: -13.897;
		reaction "H2PO4" chemical_equation: 2 * H + PO4 = H2PO4 log_k: 16;
	}
}

/**
 * An experiment that makes the pH vary from 4 to 10 as a sinusoid.
 */
experiment var_ph type: gui {
/**
	 * Returns 0.0 when v=0.0, useful for display purpose.
	 */
	float log0 (float v) {
		if (v > 0.0) {
			return log(v);
		}

		return 0.0;
	}

	reflex {
		ask ChemicalAgent {
		// The period of the pH variation
			int period <- 10;
			ph <- 7 + sin(90 + (cycle mod period) * 360 / period) * 3; // PH variations between 4 and 10
			write "Ph: " + ph;
			write "H : " + H.concentration;
			write "Na: " + Na.concentration;
			write "Cl: " + Cl.concentration;
			write "PO4: " + PO4.concentration;
			write "NaCl: " + NaCl.concentration;
			write "NaOH: " + NaOH.concentration;
			write "H2PO4: " + H2PO4.concentration;
			write "";

			// Fixes the pH to the new value
			do fix_ph(ph, H);
			// Computes the new equilibrium that corresponds to the fixed pH
			do solve_equilibrium;
		}

	}

	output {
		display "Equilibrium" type: 2d {
			chart "Equilibrium" type: series {
				data "OH" value: sum(ChemicalAgent collect (log0(each.reaction_quotient("OH")))) style: spline;
				data "NaCl" value: sum(ChemicalAgent collect (log0(each.reaction_quotient("NaCl")))) style: spline;
				data "NaOH" value: sum(ChemicalAgent collect (log0(each.reaction_quotient("NaOH")))) style: spline;
				data "H2PO4" value: sum(ChemicalAgent collect (log0(each.reaction_quotient("H2PO4")))) style: spline;
			}

		}

		display "NaCl" type: 2d {
			chart "NaCl" type: series y_label: "Log(concentration) (mol/l)" {
				data "NaCl" value: log0(sum(ChemicalAgent collect (each.NaCl.concentration))) style: spline;
			}

		}

		display "NaOH" type: 2d {
			chart "NaOH" type: series y_label: "Log(concentration) (mol/l)" {
				data "NaOH" value: log0(sum(ChemicalAgent collect (each.NaOH.concentration))) style: spline;
			}

		}

		display "H2PO4" type: 2d {
			chart "H2PO4" type: series y_label: "Log(concentration) (mol/l)" {
				data "PO4" value: log0(sum(ChemicalAgent collect (each.PO4.concentration))) style: spline;
				data "H2PO4" value: log0(sum(ChemicalAgent collect (each.H2PO4.concentration))) style: spline;
			}

		}

		display "ph" type: 2d {
			chart "pH" type: series {
				data "pH" value: -log0(sum(ChemicalAgent collect (each.H.concentration))) style: spline;
			}

		}

	}
}

/**
 * An experiment that makes the total P vary as a sinusoid.
 */
experiment var_P type: gui {

	reflex {
		ask ChemicalAgent {
			// The period of the P variation
			int period <- 10;
			float P <- 6.7e-6#mol/#l * (1.1 + sin(90 + (cycle mod period) * 360 / period)) ; // PH variations between 0.1*6.7e-6 mol/l and 2.1*6.7e-6 mol/l
			write "Ph: " + ph;
			write "H : " + H.concentration;
			write "Na: " + Na.concentration;
			write "Cl: " + Cl.concentration;
			write "PO4: " + PO4.concentration;
			write "NaCl: " + NaCl.concentration;
			write "NaOH: " + NaOH.concentration;
			write "H2PO4: " + H2PO4.concentration;
			write "";

			// Fixes the total P to the new value
			do set_total_concentration(PO4, P);
			
			// Computes the new equilibrium that corresponds to the new total P
			do solve_equilibrium;
		}

	}

	output {
		display "Equilibrium" type: 2d {
			chart "Equilibrium" type: series {
				data "OH" value: sum(ChemicalAgent collect (log0(each.reaction_quotient("OH")))) style: spline;
				data "NaCl" value: sum(ChemicalAgent collect (log0(each.reaction_quotient("NaCl")))) style: spline;
				data "NaOH" value: sum(ChemicalAgent collect (log0(each.reaction_quotient("NaOH")))) style: spline;
				data "H2PO4" value: sum(ChemicalAgent collect (log0(each.reaction_quotient("H2PO4")))) style: spline;
			}

		}

		display "NaCl" type: 2d {
			chart "NaCl" type: series y_label: "Log(concentration) (mol/l)" {
				data "NaCl" value: log0(sum(ChemicalAgent collect (each.NaCl.concentration))) style: spline;
			}

		}

		display "NaOH" type: 2d {
			chart "NaOH" type: series y_label: "Log(concentration) (mol/l)" {
				data "NaOH" value: log0(sum(ChemicalAgent collect (each.NaOH.concentration))) style: spline;
			}

		}

		display "H2PO4" type: 2d {
			chart "H2PO4" type: series y_label: "Log(concentration) (mol/l)" {
				data "PO4" value: log0(sum(ChemicalAgent collect (each.PO4.concentration))) style: spline;
				data "H2PO4" value: log0(sum(ChemicalAgent collect (each.H2PO4.concentration))) style: spline;
			}

		}

		display "Total P" type: 2d {
			chart "Total P" type: series {
				data "P" value: -log0(sum(ChemicalAgent collect (each.PO4.total_concentration))) style: spline;
			}

		}

	}

}