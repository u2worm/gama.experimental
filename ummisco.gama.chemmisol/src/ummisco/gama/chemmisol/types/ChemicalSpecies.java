package ummisco.gama.chemmisol.types;

import msi.gama.precompiler.GamlAnnotations.getter;
import msi.gama.precompiler.GamlAnnotations.variable;
import ummisco.gama.chemmisol.Phase;
import msi.gama.precompiler.GamlAnnotations.vars;
import msi.gaml.types.IType;

@vars ({ 
    @variable (
        name = "concentration",
        type = IType.FLOAT
    ),
    @variable (
    		name = "name",
    		type = IType.STRING
    		)
})
public class ChemicalSpecies extends ummisco.gama.chemmisol.ChemicalSpecies {

	public ChemicalSpecies(String name, Phase phase) {
		super(name, phase);
	}
	
	public ChemicalSpecies(Phase phase) {
		super("", phase);
	}
	
	public ChemicalSpecies() {
		super("", Phase.AQUEOUS);
	}
	
	@getter("concentration")
	public double getConcentration() {
		return super.getConcentration();
	}
	
	@getter("name")
	public String getName() {
		return super.getName();
	}
}
