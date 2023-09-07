package ummisco.gama.chemmisol.architecture;

import msi.gama.precompiler.GamlAnnotations.action;
import msi.gama.precompiler.GamlAnnotations.arg;
import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.example;
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
	@variable(name=ChemicalArchitecture.PHASE, type = IType.STRING, constant=true, doc=@doc(
			value=ChemicalArchitecture.PHASE + " keyword",
			comment="useful to construct chemical_species and chemical_components from a map",
			examples= {
					@example("chemical_component H <- chemical_component([phase::AQUEOUS])"),
					@example("chemical_species SOH <- chemical_species([phase::MINERAL])")
			}
			)),
	@variable(name=ChemicalArchitecture.TOTAL_CONCENTRATION, type = IType.STRING, constant=true, doc=@doc(
			value=ChemicalArchitecture.TOTAL_CONCENTRATION + " keyword",
			comment="useful to construct chemical_species and chemical_components from a dict",
			examples= {
					@example("chemical_component PO4 <- chemical_component([phase::AQUEOUS, total_concentration::0.1#mol/#l])"),
					@example("chemical_component SOH <- chemical_component([phase::MINERAL, total_concentration::1])")
			}
			)),
	@variable(name="AQUEOUS", type = IType.STRING, constant=true, doc=@doc(
			value="Aqueous phase",
			comment="""
					Represents a species living in a liquid with a fixed volume.\
					Its concentration is typically expressed in mol/l.
					""",
			examples= {
					@example("chemical_component PO4 <- chemical_component([phase::AQUEOUS])"),
					@example("chemical_species H3PO4 <- chemical_component([phase::AQUEOUS])"),
			}
			)),
	@variable(name="MINERAL", type = IType.STRING, constant=true, doc=@doc(
			value="Mineral phase",
			comment="""
					Represents a surface complex on a mineral. Its concentration\
					is expressed as a molar fraction.
					""",
			examples= {
					@example("chemical_component SOH <- chemical_component([phase::MINERAL, total_concentration::1])"),
					@example("chemical_species SOH2PO4 <- chemical_species([phase::MINERAL])"),
			}
			)),
	@variable(name="SOLVENT", type = IType.STRING, constant=true, doc=@doc(
			value="Solvent phase",
			comment="A solvent is always in excess and has a fixed activity of 1.",
			examples= {
					@example("chemical_component H2O <- chemical_component([phase::SOLVENT])")
			}
			))
})
@doc(
	"Agent architecture that allows agents to solve chemical systems."
)
public class ChemicalArchitecture extends ReflexArchitecture {
	private static final Phase _AQUEOUS = Phase.AQUEOUS;
	private static final Phase _MINERAL = Phase.MINERAL;
	private static final Phase _SOLVENT = Phase.SOLVENT;

	public static final String PH = "ph";
	public static final String COMPONENT = "component";
	public static final String REACTION = "reaction";
	public static final String PHASE = "phase";
	public static final String TOTAL_CONCENTRATION = "total_concentration";
	public static final String AQUEOUS = "AQUEOUS";
	public static final String MINERAL = "MINERAL";
	public static final String SOLVENT = "SOLVENT";

	/**
	 * Name of the variable used to store ChemicalSystems in each Agent with a ChemicalSystem architecture.
	 */
	static final String CHEMICAL_SYSTEM_VARIABLE = "chemical_system";
	
	/**
	 * Name of the ChemicalSystem architecture, passed to the "control:" facet of a species.
	 */
	static final String CHEMICAL_ARCHITECTURE = "chemical";


	@getter(AQUEOUS)
	public Phase getAqueous() {
		return _AQUEOUS;
	}

	@getter(MINERAL)
	public Phase getMineral() {
		return _MINERAL;
	}

	@getter(SOLVENT)
	public Phase getSolvent() {
		return _SOLVENT;
	}
	
	@getter(PHASE)
	public String getPhase() {
		return ChemicalComponentType.CHEMICAL_PHASE;
	}

	@getter(TOTAL_CONCENTRATION)
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
	@doc("""
			Solves the equilibrium state of the chemical system defined in the \
			agent, using the chemmisol solver. Concentrations of \
			chemical_components and chemical_species defined in the agent scope \
			are automatically updated.
			""")
	public Object solve_equilibrium(final IScope scope) throws GamaRuntimeException {
		try {
			((ChemicalSystem) scope.getAgent().getAttribute(CHEMICAL_SYSTEM_VARIABLE)).solve();
		} catch (ChemicalSystem.ChemmisolCoreException e) {
			throw GamaRuntimeException.create(e, scope);
		}
		return null;
	}

	@action(name = "fix_ph", args = {
			@arg(name = PH, type = IType.FLOAT, optional = false, doc = @doc("pH value.")),
			@arg(name = COMPONENT, type = ChemicalComponentType.CHEMICAL_COMPONENT_TYPE_ID, optional = false,
					doc = @doc("""
							chemical_component representing the H+ species. \
							When the equilibrium is solved, the concentration \
							of the chemical species associated to this \
							component is set to 10^-pH.
		       				"""))
			})
	@doc("""
			Fixes the ph of the chemical system. The equilibrium state must be \
			solved for the new ph value to be effective.
			""")
	public Object fix_ph(final IScope scope) throws GamaRuntimeException {
		((ChemicalSystem) scope.getAgent().getAttribute(CHEMICAL_SYSTEM_VARIABLE)).fixPH(
				scope.getFloatArg(PH),
				(ChemicalComponent) scope.getArg(COMPONENT)
				);
		return null;
	}
	
	@action(name = "set_total_concentration",
			args = {
					@arg(name = COMPONENT, type=ChemicalComponentType.CHEMICAL_COMPONENT_TYPE_ID, optional=false,
							doc=@doc("Chemical component.")),
					@arg(name = TOTAL_CONCENTRATION, type=IType.FLOAT, optional=false,
							doc=@doc("Total concentration of the component."))
			})
	@doc("""
			Sets the total concentration of a component. The equilibrium state\
			must be solved so that the total concentration is actually dispatch\
			between chemical species according to the new equilibrium of the\
			system.
			""")
	public Object set_total_concentration(final IScope scope) throws GamaRuntimeException {
		((ChemicalSystem) scope.getAgent().getAttribute(CHEMICAL_SYSTEM_VARIABLE))
				.setTotalConcentration((ChemicalComponent) scope.getArg(COMPONENT), scope.getFloatArg(TOTAL_CONCENTRATION));
		return null;
	}

	@action(name = "reaction_quotient",
			args = {
		            @arg(name = REACTION, type=IType.STRING, optional = false,
		            		doc=@doc("Name of a reaction defined in the system."))
		            })
	@doc(value = """
			Computes the reaction quotient of a reaction defined in the system. Considering the reaction:
			    m * A + n * B = o * C + p * D
			the reaction quotient is computed as:
			    Q = ([C]^o + [D]^p) / ([A]^m + [B]^n)
			where the bracket [] notation denotes the activity of each species.
			""",
			returns = "Reaction quotient of the reaction."
			)
	public double reaction_quotient(final IScope scope) throws GamaRuntimeException {
		ChemicalSystem system = ((ChemicalSystem) scope.getAgent().getAttribute(CHEMICAL_SYSTEM_VARIABLE));
		return system.reactionQuotient(scope.getStringArg(REACTION));
	}
}
