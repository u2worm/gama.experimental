package ummisco.gama.chemmisol.architecture;

import java.util.LinkedList;
import java.util.List;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.precompiler.GamlAnnotations.action;
import msi.gama.precompiler.GamlAnnotations.arg;
import msi.gama.precompiler.GamlAnnotations.skill;
import msi.gama.precompiler.GamlAnnotations.variable;
import msi.gama.precompiler.GamlAnnotations.vars;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.util.GamaMapFactory;
import msi.gama.util.IMap;
import msi.gaml.architecture.reflex.ReflexArchitecture;
import msi.gaml.statements.IStatement;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.types.ChemicalSystem;
import ummisco.gama.chemmisol.ChemmisolLoader;
import ummisco.gama.chemmisol.statements.ChemicalSystemStatement;
import ummisco.gama.chemmisol.types.ChemicalSystemType;

@skill(
		name=ChemicalSystemArchitecture.CHEMICAL_ARCHITECTURE
		)
@vars({
	@variable(name=ChemicalSystemArchitecture.CHEMICAL_SYSTEM_VARIABLE, type = IType.MAP, internal=true)
})
public class ChemicalSystemArchitecture extends ReflexArchitecture {

	/**
	 * Name of the variable used to store ChemicalSystems in each Agent with a ChemicalSystem architecture.
	 */
	static final String CHEMICAL_SYSTEM_VARIABLE = "chemical_systems";
	
	/**
	 * Name of the ChemicalSystem architecture, passed to the "control:" facet of a species.
	 */
	static final String CHEMICAL_ARCHITECTURE = "chemical";
	
	List<ChemicalSystemStatement> chemical_system_statements;
	
	static {
		ChemmisolLoader.loadChemmisol();
	}
	
	public ChemicalSystemArchitecture() {
		super();
		chemical_system_statements = new LinkedList<ChemicalSystemStatement>();
	}
	
	@Override
	public boolean init(IScope scope) throws GamaRuntimeException {
		super.init(scope);

		@SuppressWarnings("unchecked")
		IMap<String, ChemicalSystem> chemical_systems = GamaMapFactory.create(
				scope.getType(IKeyword.STRING), scope.getType(ChemicalSystemType.CHEMICAL_SYSTEM_TYPE), false);
		
		for(ChemicalSystemStatement chemical_system_statement : chemical_system_statements) {
			ChemicalSystem chemical_system = 
					(ChemicalSystem) scope.execute(chemical_system_statement).getValue();
			
			chemical_systems.put(chemical_system.getName(), chemical_system);
		}
		scope.getAgent().setDirectVarValue(scope,
				CHEMICAL_SYSTEM_VARIABLE,
				chemical_systems
				);

		return false;
	}

	@Override
	public void addBehavior(final IStatement c) {
		if (c instanceof ChemicalSystemStatement) {
			chemical_system_statements.add((ChemicalSystemStatement) c);
		} else {
			super.addBehavior(c);
		}
	}
	
	@Override
	protected void clearBehaviors() {
		super.clearBehaviors();
		chemical_system_statements.clear();
	}
	
	@action(name = "solve_equilibrium", 
	        args = {
	            @arg(name = "chemical_system", type = IType.ID, optional = false)}
	)
	public Object solve_equilibrium(final IScope scope) throws GamaRuntimeException {
		System.out.println("Solve : " + scope.getArg("chemical_system", IType.ID));
		return null;
	}
}
