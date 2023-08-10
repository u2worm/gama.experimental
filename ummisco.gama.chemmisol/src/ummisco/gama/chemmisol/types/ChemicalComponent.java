package ummisco.gama.chemmisol.types;

import msi.gama.precompiler.GamlAnnotations.getter;
import msi.gama.precompiler.GamlAnnotations.variable;
import msi.gama.precompiler.GamlAnnotations.vars;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Phase;

@vars ({ 
    @variable (
        name = "total_concentration",
        type = IType.FLOAT
    ),
    @variable (
        name = "concentration",
        type = IType.FLOAT
    ),
    @variable (
    		name = "name",
    		type = IType.STRING
    		),
    @variable (
    		name = "species",
    		type = ChemicalSpeciesType.CHEMICAL_SPECIES_TYPE_ID
    		)
})
public class ChemicalComponent extends ummisco.gama.chemmisol.ChemicalComponent {

	public ChemicalComponent(String name, Phase phase, double total_concentration) {
		super(new ChemicalSpecies(name, phase), total_concentration);
		// Considers the total quantity of the component is represented only by this species,
		// since the concentration of all compound species is currently initialize to 0.0.
		getSpecies().setConcentration(total_concentration);
	}

	public ChemicalComponent(Phase phase, double total_concentration) {
		this("", phase, total_concentration);
	}
	
	public ChemicalComponent(Phase phase) {
		this(phase, 0.0);
	}

	public ChemicalComponent(double concentration) {
		this(Phase.AQUEOUS, concentration);
	}
	
	public ChemicalComponent() {
		this (Phase.AQUEOUS, 0.0);
	}

	@getter("total_concentration")
	public double getTotalConcentration() {
		return super.getTotalConcentration();
	}

	@getter("concentration")
	public double getConcentration() {
		return super.getConcentration();
	}

	@getter("name")
	public String getName() {
		return super.getName();
	}
	
	@getter("species")
	public ChemicalSpecies getSpecies() {
		return (ChemicalSpecies) super.getSpecies();
	}
}
