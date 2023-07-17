package ummisco.gama.chemmisol.architecture;


import msi.gama.common.interfaces.IKeyword;
import msi.gama.precompiler.GamlAnnotations.action;
import msi.gama.precompiler.GamlAnnotations.arg;
import msi.gama.precompiler.GamlAnnotations.skill;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.architecture.reflex.ReflexArchitecture;
import msi.gaml.descriptions.IDescription;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.ChemmisolLoader;

@skill(
		name=ChemicalSystemArchitecture.CHEMICAL_ARCHITECTURE
		)
public class ChemicalSystemArchitecture extends ReflexArchitecture {

	static final String CHEMICAL_ARCHITECTURE = "chemical";
	
	static {
		ChemmisolLoader.loadChemmisol();
	}
	
	public ChemicalSystemArchitecture() {
		super();
	    System.out.println("New chem system skill");
	}
	public ChemicalSystemArchitecture(final IDescription desc) {
		super();
	    System.out.println("New chem system statement: " + getName());
	}
	
	@Override
	public boolean init(IScope scope) throws GamaRuntimeException {
		super.init(scope);
		// TODO Auto-generated method stub
		return false;
	}

	@action(name = "solve_chem", 
	        args = {
	            @arg(name = "chemical_system", type = IType.ID, optional = false)})
	public Object solve_chem(final IScope scope) throws GamaRuntimeException {
		System.out.println("Solve : " + scope.getArg("chemical_system", IType.ID));
		return null;
	}
}
