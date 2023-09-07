package ummisco.gama.chemmisol.types;

import msi.gama.precompiler.GamlAnnotations.getter;
import msi.gama.precompiler.GamlAnnotations.variable;
import msi.gama.precompiler.GamlAnnotations.vars;
import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Phase;
import ummisco.gama.chemmisol.architecture.ChemicalArchitecture;

@vars ({ 
    @variable (
        name = ChemicalComponent.TOTAL_CONCENTRATION,
        type = IType.FLOAT,
        doc = @doc("Total concentration of the chemical component.")
    ),
    @variable (
        name = ChemicalComponent.CONCENTRATION,
        type = IType.FLOAT,
        doc = @doc("""
        		Shortcut to get the concentration of the species associated to \
        		this component.
        		
        		Notice that this is different from the total_concentration of \
        		the component.
        		""")
    ),
    @variable (
    		name = ChemicalComponent.NAME,
    		type = IType.STRING,
    		doc = @doc("""
    				Name of the component.
    				
    				The name of the component is the same as the name of its \
    				associated species.
    				""")
    		),
    @variable (
    		name = ChemicalComponent.SPECIES,
    		type = ChemicalSpeciesType.CHEMICAL_SPECIES_TYPE_ID,
    		doc = @doc("""
    				Species associated to this component.
    				""")
    		)
})

public class ChemicalComponent extends ummisco.gama.chemmisol.ChemicalComponent {
	private static final String TOTAL_CONCENTRATION = ChemicalArchitecture.TOTAL_CONCENTRATION;
	private static final String CONCENTRATION = "concentration";
	private static final String NAME = "name";
	private static final String SPECIES = "species";

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

	public ChemicalComponent(double total_concentration) {
		this(Phase.AQUEOUS, total_concentration);
	}
	
	public ChemicalComponent() {
		this (Phase.AQUEOUS, 0.0);
	}

	@getter(TOTAL_CONCENTRATION)
	public double getTotalConcentration() {
		return super.getTotalConcentration();
	}

	@getter(CONCENTRATION)
	public double getConcentration() {
		return super.getSpecies().getConcentration();
	}

	@getter(NAME)
	public String getName() {
		return super.getName();
	}
	
	@getter(SPECIES)
	public ChemicalSpecies getSpecies() {
		// Safe cast from ummisco.gama.chemmisol.ChemicalSpecies to ummisco.gama.chemmisol.types.ChemicalSpecies
		return (ChemicalSpecies) super.getSpecies();
	}
}
