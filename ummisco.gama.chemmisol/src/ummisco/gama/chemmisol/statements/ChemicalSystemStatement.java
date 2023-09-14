package ummisco.gama.chemmisol.statements;

import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.facet;
import msi.gama.precompiler.GamlAnnotations.facets;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.runtime.ExecutionResult;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.compilation.IDescriptionValidator;
import msi.gaml.compilation.annotations.validator;
import msi.gaml.descriptions.IDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.statements.AbstractStatementSequence;
import msi.gaml.statements.IStatement;

import java.util.Iterator;
import java.util.List;

import msi.gama.common.interfaces.IKeyword;
import msi.gaml.types.IType;
import msi.gaml.variables.IVariable;
import ummisco.gama.chemmisol.Reaction;
import ummisco.gama.chemmisol.architecture.ChemicalArchitecture;
import ummisco.gama.chemmisol.types.ChemicalComponent;
import ummisco.gama.chemmisol.types.ChemicalComponentType;
import ummisco.gama.chemmisol.types.ChemicalSpecies;
import ummisco.gama.chemmisol.types.ChemicalSpeciesType;
import ummisco.gama.chemmisol.types.ChemicalSystem;
import ummisco.gama.chemmisol.units.UnitConversion;

@symbol(name = ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT, kind = ISymbolKind.BEHAVIOR, with_sequence = true)
@inside(kinds = { ISymbolKind.SPECIES })
@facets(value = {
		@facet(
				name = ChemicalArchitecture.PH,
				type = IType.LIST, optional = true,
				doc = @doc("Initial fixed pH value.")),
		@facet(
				name = ChemicalSystemStatement.SOLID_CONCENTRATION,
				type = IType.FLOAT, optional = true,
				doc = @doc("""
						Mass concentration of mineral in suspension in the \
						solution (example unit: #mass/#l).
						"""
						)
		),
		@facet(
				name = ChemicalSystemStatement.SPECIFIC_SURFACE_AREA,
				type = IType.FLOAT, optional = true,
				doc = @doc("""
						Surface of the solid in contact with the solution per \
						unit of mass (example unit: #m2/#g)
						""")

			),
		@facet(
				name = ChemicalSystemStatement.SITE_CONCENTRATION,
				type = IType.FLOAT, optional = true,
				doc = @doc("""
						Quantity of sites per unit of surface in contact with \
						the solution (example unit: #entities/#nm2)
						""")

			)
		})
@validator(value = ChemicalSystemStatement.ReactionValidator.class)
@doc("""
	Sequence statement used to declare a chemical system at the agent scope of \
	an agent with the chemical skill.
	The sequence of statements might contain any GAML declaration, local \
	declarations of chemical_components and chemical_species, and the definition \
	of reactions.
	"""
)
public class ChemicalSystemStatement extends AbstractStatementSequence {

	static final String CHEMICAL_SYSTEM_STATEMENT = "chemical_system";
	static final String SOLID_CONCENTRATION = "solid_concentration";
	static final String SPECIFIC_SURFACE_AREA = "specific_surface_area";
	static final String SITE_CONCENTRATION = "site_concentration";

	public static class ReactionValidator implements IDescriptionValidator<IDescription> {

		@Override
		public void validate(IDescription description) {
			Iterator<IDescription> it = description.getSpeciesContext()
					.getChildrenWithKeyword(ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT)
					.iterator();
			int n = 0;
			while(it.hasNext() && n < 2) {
				n = n + 1;
				it.next();
			}
			if(n > 1) {
				description.error("""
						Only one chemical_system can be declared in each \
						chemical agent species.
						""");
			} else if(n == 1) {
				IDescription system = description.getSpeciesContext()
						.getChildWithKeyword(ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT);
				if(system.hasFacet(SOLID_CONCENTRATION) ||
						system.hasFacet(SPECIFIC_SURFACE_AREA) ||
						system.hasFacet(SITE_CONCENTRATION)) {
					if(!(system.hasFacet(SOLID_CONCENTRATION) &&
						system.hasFacet(SPECIFIC_SURFACE_AREA) &&
						system.hasFacet(SITE_CONCENTRATION))) {
						description.error("""
								If one of the solid_concentration or \
								specific_surface_area or site_concentration \
								facet is specified, the three parameters must be \
								specified so the site count can be properly \
								computed.
								""");
					}
				}
			}
		}
	}

	final IExpression ph_expression;
	final IExpression solid_concentration_expression;
	final IExpression specific_surface_area_expression;
	final IExpression site_concentration_expression;
	
	public ChemicalSystemStatement(final IDescription desc) {
		super(desc);
		ph_expression = desc.hasFacet(ChemicalArchitecture.PH) ?
				desc.getFacet(ChemicalArchitecture.PH).getExpression()
				: null;
		solid_concentration_expression = desc.hasFacet(SOLID_CONCENTRATION) ?
				desc.getFacet(SOLID_CONCENTRATION).getExpression()
				: null;
		specific_surface_area_expression = desc.hasFacet(SPECIFIC_SURFACE_AREA) ?
				desc.getFacet(SPECIFIC_SURFACE_AREA).getExpression()
				: null;
		site_concentration_expression = desc.hasFacet(SITE_CONCENTRATION) ?
				desc.getFacet(SITE_CONCENTRATION).getExpression()
				: null;
	}

	@Override
	public ChemicalSystem privateExecuteIn(IScope scope) throws GamaRuntimeException {
		// Executes the "chemical_system" statement itself by initializing a new
		// ChemicalSystem

		/* Sets the names of defined components */

		// This is done before the initialization of the chemical system, so
		// that chemical system facets can safely use chemical_component and
		// chemical_species variables

		for (IVariable var : scope.getAgent().getSpecies().getVars()) {
			if (var.getKeyword().equals(ChemicalComponentType.CHEMICAL_COMPONENT_TYPE)) {
				ChemicalComponent component = (ChemicalComponent) scope.getAgent().getAttribute(var.getName());
				// Sets the name of the component according to the name of the variable
				component.getSpecies().setName(var.getName());
				// Adds the component to the system
			} else if(var.getKeyword().equals(ChemicalSpeciesType.CHEMICAL_SPECIES_TYPE)) {
				ChemicalSpecies species = (ChemicalSpecies) scope.getAgent().getAttribute(var.getName());
				// Sets the name of the species according to the name of the variable
				species.setName(var.getName());
			}
		}


		/* Initializes the chemical system */

		ChemicalSystem chemical_system;
		String chemical_system_name = scope.getCurrentSymbol().getDescription().getLitteral(IKeyword.NAME);
		if (solid_concentration_expression != null &&
				specific_surface_area_expression != null &&
				site_concentration_expression != null) {
			Double solid_concentration =
					(Double) scope.evaluate(solid_concentration_expression, scope.getAgent()).getValue();
			Double specific_surface_area =
					(Double) scope.evaluate(specific_surface_area_expression, scope.getAgent()).getValue();
			Double site_concentration =
					(Double) scope.evaluate(site_concentration_expression, scope.getAgent()).getValue();
			chemical_system = new ChemicalSystem(
					chemical_system_name,
					UnitConversion.convertSolidConcentration(solid_concentration),
					UnitConversion.convertSpecificSurfaceArea(specific_surface_area),
					UnitConversion.convertSiteConcentration(site_concentration));
		} else {
			chemical_system = new ChemicalSystem(chemical_system_name);
		}

		/* Adds chemical_species and chemical_components to the chemical system */

		for (IVariable var : scope.getAgent().getSpecies().getVars()) {
			if (var.getKeyword().equals(ChemicalComponentType.CHEMICAL_COMPONENT_TYPE)) {
				ChemicalComponent component = (ChemicalComponent) scope.getAgent().getAttribute(var.getName());
				// Adds the component to the system
				try {
					chemical_system.addComponent(component);
				} catch (ChemicalSystem.ChemmisolCoreException e) {
					throw GamaRuntimeException.create(e, scope);
				}
			} else if(var.getKeyword().equals(ChemicalSpeciesType.CHEMICAL_SPECIES_TYPE)) {
				ChemicalSpecies species = (ChemicalSpecies) scope.getAgent().getAttribute(var.getName());
				// Adds the component to the system
				chemical_system.addSpecies(species);
			}
		}

		/* chemical system statements execution */
		
		// Execute the sequence statements (in the "chemical_system" block) using the
		// predefined AbstractStatementSequence, to execute "reaction" statements for
		// example. See ReactionStatement for how reactions are handled.
		for (final IStatement command : super.commands) {
			final ExecutionResult result = scope.execute(command);

			// Handles components defined locally in the chemical_system scope.
			// Notice that such local variables are handled slightly differently than
			// chemical_components defined as agent attributes (see ChemicalArchitecture).
			// Note: in this case, command is of type LetStatement and
			// command.getFacet(NAME) is an expression of type IVarExpression that declares
			// the variable type and name.
			if (command.getFacet(IKeyword.NAME).getGamlType()
					.id() == ChemicalComponentType.CHEMICAL_COMPONENT_TYPE_ID) {
				// This command defines a chemical component
				ChemicalComponent component = (ChemicalComponent) result.getValue();
				// Sets the name of the local component
				component.getSpecies().setName(command.getDescription().getName());
				// Adds the local component to the chemical system
				try {
					chemical_system.addComponent(component);
				} catch (ChemicalSystem.ChemmisolCoreException e) {
					throw GamaRuntimeException.create(e, scope);
				}
			} else if (command.getFacet(IKeyword.NAME).getGamlType()
					.id() == ChemicalSpeciesType.CHEMICAL_SPECIES_TYPE_ID) {
				ChemicalSpecies species = (ChemicalSpecies) result.getValue();
				// Sets the name of the local component
				species.setName(command.getDescription().getName());
				// No call to system.addSpecies(), since local chemical species do not need to
				// be tracked.
			}

			if (command instanceof ReactionStatement) {
				Reaction reaction = (Reaction) result.getValue();
				chemical_system.addReaction(reaction);
			}
		}

		/* Sets ph from the ph facet */
		
		if(ph_expression != null) {
			List<?> ph_arg = ((List<?>) scope.evaluate(ph_expression, scope.getAgent()).getValue());
			if(ph_arg.get(1) instanceof ChemicalComponent) {
				chemical_system.fixPH(
						((Number) ph_arg.get(0)).doubleValue(),
						(ChemicalComponent) ph_arg.get(1)
						);
			}
		}

		/* Sets up the chemical system */
		
		try {
			chemical_system.setUp();
		} catch (ChemicalSystem.ChemmisolCoreException e) {
			throw GamaRuntimeException.create(e, scope);
		}
		return chemical_system;
	}
}
