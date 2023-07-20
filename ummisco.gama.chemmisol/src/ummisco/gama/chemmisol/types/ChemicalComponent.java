package ummisco.gama.chemmisol.types;

import msi.gama.precompiler.GamlAnnotations.getter;
import msi.gama.precompiler.GamlAnnotations.setter;
import msi.gama.precompiler.GamlAnnotations.variable;
import msi.gama.precompiler.GamlAnnotations.vars;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Component;
import ummisco.gama.chemmisol.Phase;

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
public class ChemicalComponent extends Component {

	public ChemicalComponent(String name, Phase phase, double concentration) {
		super(name, phase, concentration);
	}

	public ChemicalComponent(Phase phase, double concentration) {
		this("", phase, concentration);
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

	@getter("concentration")
	public double getConcentration() {
		return super.getConcentration();
	}
	
	@setter("concentration")
	public void setConcentration(double concentration) {
		super.setConcentration(concentration);
	}
	
	@getter("name")
	public String getName() {
		return super.getName();
	}
	
	public void setName(String name) {
		super.setName(name);
	}
}
