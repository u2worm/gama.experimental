package ummisco.gama.chemmisol.types;

import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.getter;
import msi.gama.precompiler.GamlAnnotations.variable;
import msi.gama.precompiler.GamlAnnotations.vars;
import msi.gaml.types.IType;

@vars ({ 
    @variable (
        name = "name",
        type = IType.STRING,
        doc = { @doc ("Name of the ChemicalSystem") })
})
public class ChemicalSystem extends ummisco.gama.chemmisol.ChemicalSystem {

	private String name;
	
	public ChemicalSystem(String name) {
		super();
		this.name = name;
	}
	
	public ChemicalSystem(
			String name,
			double solid_concentration,
			double specific_surface_area,
			double site_concentration) {
		super(solid_concentration, specific_surface_area, site_concentration);
		this.name = name;
	}

	@getter("name")
	public String getName() {
		return name;
	}
}
