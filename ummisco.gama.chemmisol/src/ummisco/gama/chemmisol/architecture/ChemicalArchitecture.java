package ummisco.gama.chemmisol.architecture;

import java.util.LinkedList;
import java.util.List;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.precompiler.GamlAnnotations.action;
import msi.gama.precompiler.GamlAnnotations.arg;
import msi.gama.precompiler.GamlAnnotations.getter;
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
import msi.gaml.variables.IVariable;
import ummisco.gama.chemmisol.types.ChemicalSystem;
import ummisco.gama.chemmisol.ChemmisolLoader;
import ummisco.gama.chemmisol.Component;
import ummisco.gama.chemmisol.Phase;
import ummisco.gama.chemmisol.statements.ChemicalSystemStatement;
import ummisco.gama.chemmisol.types.ChemicalSystemType;
import ummisco.gama.chemmisol.types.ChemicalComponentType;
import ummisco.gama.chemmisol.types.ChemicalPhaseType;

@skill(
		name=ChemicalArchitecture.CHEMICAL_ARCHITECTURE
		)
@vars({
	@variable(name=ChemicalArchitecture.CHEMICAL_SYSTEM_VARIABLE, type = IType.NONE, internal=true),
	@variable(name="AQUEOUS", type = IType.STRING),
	@variable(name="MINERAL", type = IType.STRING),
	@variable(name="SOLVENT", type = IType.STRING),
	@variable(name="phase", type = IType.STRING),
	@variable(name="concentration", type = IType.STRING)
})
public class ChemicalArchitecture extends ReflexArchitecture {
	private static final Phase AQUEOUS = Phase.AQUEOUS;
	private static final Phase MINERAL = Phase.MINERAL;
	private static final Phase SOLVENT = Phase.SOLVENT;

	/**
	 * Name of the variable used to store ChemicalSystems in each Agent with a ChemicalSystem architecture.
	 */
	static final String CHEMICAL_SYSTEM_VARIABLE = "chemical_system";
	
	/**
	 * Name of the ChemicalSystem architecture, passed to the "control:" facet of a species.
	 */
	static final String CHEMICAL_ARCHITECTURE = "chemical";


	@getter("AQUEOUS")
	public Phase getAqueous() {
		return AQUEOUS;
	}

	@getter("MINERAL")
	public Phase getMineral() {
		return MINERAL;
	}

	@getter("SOLVENT")
	public Phase getSolvent() {
		return SOLVENT;
	}
	
	@getter("phase")
	public String getPhase() {
		return ChemicalComponentType.CHEMICAL_PHASE;
	}

	@getter("concentration")
	public String getConcentration() {
		return ChemicalComponentType.CHEMICAL_CONCENTRATION;
	}
	
	ChemicalSystemStatement chemical_system_statement;
	
	static {
		ChemmisolLoader.loadChemmisol();
	}
	
	public ChemicalArchitecture() {
		super();
	}
	
	@Override
	public boolean init(IScope scope) throws GamaRuntimeException {
		super.init(scope);

		ChemicalSystem chemical_system =
			(ChemicalSystem) scope.execute(chemical_system_statement).getValue();

		scope.getAgent().setAttribute(
			CHEMICAL_SYSTEM_VARIABLE,
			chemical_system
		);

		return false;
	}

	@Override
	public void addBehavior(final IStatement c) {
		if (c instanceof ChemicalSystemStatement) {
			if(chemical_system_statement == null) {
				chemical_system_statement = (ChemicalSystemStatement) c;
			} else {
				this.getDescription().error("Only one chemical_system can be declared in each species.");
			}
		} else {
			super.addBehavior(c);
		}
	}
	
	@Override
	protected void clearBehaviors() {
		super.clearBehaviors();
		chemical_system_statement = null;
	}
	
	@action(name = "solve_equilibrium")
	public Object solve_equilibrium(final IScope scope) throws GamaRuntimeException {
		System.out.println("Solve equilibrium");

		((ChemicalSystem) scope.getAgent().getAttribute(
			CHEMICAL_SYSTEM_VARIABLE
		)).solve();
		return null;
	}
}
