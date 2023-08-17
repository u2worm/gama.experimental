package ummisco.gama.chemmisol.architecture;

import msi.gama.precompiler.GamlAnnotations.action;
import msi.gama.precompiler.GamlAnnotations.arg;
import msi.gama.precompiler.GamlAnnotations.getter;
import msi.gama.precompiler.GamlAnnotations.skill;
import msi.gama.precompiler.GamlAnnotations.variable;
import msi.gama.precompiler.GamlAnnotations.vars;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.architecture.reflex.ReflexArchitecture;
import msi.gaml.statements.IStatement;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.types.ChemicalSystem;
import ummisco.gama.chemmisol.ChemmisolLoader;
import ummisco.gama.chemmisol.Phase;
import ummisco.gama.chemmisol.statements.ChemicalSystemStatement;
import ummisco.gama.chemmisol.types.ChemicalComponent;
import ummisco.gama.chemmisol.types.ChemicalComponentType;

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

	public static final String PH = "ph";
	public static final String COMPONENT = "component";
	public static final String REACTION = "reaction";
	public static final String CONCENTRATION = "concentration";

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
			chemical_system_statement = (ChemicalSystemStatement) c;
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
		try {
			((ChemicalSystem) scope.getAgent().getAttribute(CHEMICAL_SYSTEM_VARIABLE)).solve();
		} catch (ChemicalSystem.ChemmisolCoreException e) {
			throw GamaRuntimeException.create(e, scope);
		}
		return null;
	}

	@action(name = "fix_ph",
			args = {
		            @arg(name = PH, type = IType.FLOAT, optional = false),
		            @arg(name = COMPONENT, type=ChemicalComponentType.CHEMICAL_COMPONENT_TYPE_ID, optional = false)
		            })
	public Object fix_ph(final IScope scope) throws GamaRuntimeException {
		((ChemicalSystem) scope.getAgent().getAttribute(CHEMICAL_SYSTEM_VARIABLE)).fixPH(
				scope.getFloatArg(PH),
				(ChemicalComponent) scope.getArg(COMPONENT)
				);
		return null;
	}
	
	@action(name = "set_total_concentration",
			args = {
					@arg(name = COMPONENT, type=ChemicalComponentType.CHEMICAL_COMPONENT_TYPE_ID, optional=false),
					@arg(name = CONCENTRATION, type=IType.FLOAT, optional=false)
			})
	public void set_total_concentration(final IScope scope) throws GamaRuntimeException {
		((ChemicalSystem) scope.getAgent().getAttribute(CHEMICAL_SYSTEM_VARIABLE)).setTotalConcentration(
				(ChemicalComponent) scope.getArg(COMPONENT),
				scope.getFloatArg(CONCENTRATION)
				);
	}

	@action(name = "reaction_quotient",
			args = {
		            @arg(name = REACTION, type=IType.STRING, optional = false)
		            })
	public double reaction_quotient(final IScope scope) throws GamaRuntimeException {
		ChemicalSystem system = ((ChemicalSystem) scope.getAgent().getAttribute(CHEMICAL_SYSTEM_VARIABLE));
		return system.reactionQuotient(scope.getStringArg(REACTION));
	}
}
