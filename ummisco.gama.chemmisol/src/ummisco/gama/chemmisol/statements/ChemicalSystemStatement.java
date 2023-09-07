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

@symbol(name = ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT, kind = ISymbolKind.BEHAVIOR, with_sequence = true)
@inside(kinds = { ISymbolKind.SPECIES })
@facets(value = { @facet(
		name = ChemicalArchitecture.PH, type = IType.LIST, optional = true,
		doc=@doc("Initial fixed pH value.")) })
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

	public static class ReactionValidator implements IDescriptionValidator<IDescription> {

		@Override
		public void validate(IDescription description) {
			Iterator<IDescription> it = description.getSpeciesContext().getChildrenWithKeyword(ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT).iterator();
			int n = 0;
			while(it.hasNext() && n < 2) {
				n = n + 1;
				it.next();
			}
			if(n > 1) {
				description.error("Only one chemical_system can be declared in each chemical agent species.");
			}
		}
	}

	final IExpression ph_expression;
	
	public ChemicalSystemStatement(final IDescription desc) {
		super(desc);
		if(desc.hasFacet(ChemicalArchitecture.PH)) {
			ph_expression = desc.getFacet(ChemicalArchitecture.PH).getExpression();
		} else {
			ph_expression = null;
		}
	}

	@Override
	public ChemicalSystem privateExecuteIn(IScope scope) throws GamaRuntimeException {
		// Executes the "chemical_system" statement itself by initializing a new
		// ChemicalSystem
		ChemicalSystem chemical_system = new ChemicalSystem(
				scope.getCurrentSymbol().getDescription().getLitteral(IKeyword.NAME));

		// ChemicalComponent variables post-treatment
		for (IVariable var : scope.getAgent().getSpecies().getVars()) {
			if (var.getKeyword().equals(ChemicalComponentType.CHEMICAL_COMPONENT_TYPE)) {
				ChemicalComponent component = (ChemicalComponent) scope.getAgent().getAttribute(var.getName());
				// Sets the name of the component according to the name of the variable
				component.getSpecies().setName(var.getName());
				// Adds the component to the system
				chemical_system.addComponent(component);
			} else if(var.getKeyword().equals(ChemicalSpeciesType.CHEMICAL_SPECIES_TYPE)) {
				ChemicalSpecies species = (ChemicalSpecies) scope.getAgent().getAttribute(var.getName());
				// Sets the name of the component according to the name of the variable
				species.setName(var.getName());
				// Adds the component to the system
				chemical_system.addSpecies(species);
			}
		}

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
				chemical_system.addComponent(component);
			} else if(command.getFacet(IKeyword.NAME).getGamlType()
					.id() == ChemicalSpeciesType.CHEMICAL_SPECIES_TYPE_ID) {
				ChemicalSpecies species = (ChemicalSpecies) result.getValue();
				// Sets the name of the local component
				species.setName(command.getDescription().getName());
				// No call to system.addSpecies(), since local chemical species do not need to be tracked.
			}

			if (command instanceof ReactionStatement) {
				Reaction reaction = (Reaction) result.getValue();
				chemical_system.addReaction(reaction);
			}
		}

		if(ph_expression != null) {
			List<?> ph_arg = ((List<?>) scope.evaluate(ph_expression, scope.getAgent()).getValue());
			if(ph_arg.get(1) instanceof ChemicalComponent) {
				chemical_system.fixPH(
						((Number) ph_arg.get(0)).doubleValue(),
						(ChemicalComponent) ph_arg.get(1)
						);
			}
		}

		try {
			chemical_system.setUp();
		} catch (ChemicalSystem.ChemmisolCoreException e) {
			throw GamaRuntimeException.create(e, scope);
		}
		return chemical_system;
	}
}
