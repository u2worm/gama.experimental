package ummisco.gama.chemmisol.types;

import msi.gama.precompiler.GamlAnnotations.getter;
import msi.gama.precompiler.GamlAnnotations.variable;
import msi.gama.precompiler.GamlAnnotations.vars;
import msi.gama.precompiler.GamlAnnotations.doc;
import ummisco.gama.chemmisol.Phase;
import msi.gaml.types.IType;

@vars ({ 
    @variable (
        name = ChemicalSpecies.CONCENTRATION,
        type = IType.FLOAT,
        doc=@doc(value="""
        		Concentration of the chemical species within a chemical system.

        		The interpretation of the concentration depends on the phase of \
        		the component. For example, it can be a concentration in mol/l \
        		for aqueous species, or a molar fraction for mineral species. \
        		The concentration of solvents is fixed to a unitary value that \
        		corresponds to an activity of 1.
        		""",
        		comment="""
        				The concentration of a species should **not** be \
        				modified by the user, as it is determined by the \
        				equilibrium state of the system. Users should set the pH \
        				or the total_concentration of chemical **components** \
        				instead to observe concentration changes of species in \
        				the system.
        				""")
    ),
    @variable (
    		name = ChemicalSpecies.NAME,
    		type = IType.STRING,
    		doc=@doc(value="""
    				Name of the species.
    				""",
    				comment="""
    						The name of each species should be unique within a \
    						chemical system. The name of the species is \
    						currently set to the name of the variable that \
    						declares it.
    						""")
    		)
})
public class ChemicalSpecies extends ummisco.gama.chemmisol.ChemicalSpecies {
	private static final String CONCENTRATION = "concentration";
	private static final String NAME = "name";

	public ChemicalSpecies(String name, Phase phase) {
		super(name, phase);
	}
	
	public ChemicalSpecies(Phase phase) {
		super("", phase);
	}
	
	public ChemicalSpecies() {
		super("", Phase.AQUEOUS);
	}
	
	@getter(ChemicalSpecies.CONCENTRATION)
	public double getConcentration() {
		return super.getConcentration();
	}
	
	@getter(ChemicalSpecies.NAME)
	public String getName() {
		return super.getName();
	}
}
