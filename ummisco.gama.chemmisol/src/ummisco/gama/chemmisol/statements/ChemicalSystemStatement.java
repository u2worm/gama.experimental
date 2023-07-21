package ummisco.gama.chemmisol.statements;

import msi.gama.precompiler.GamlAnnotations.facet;
import msi.gama.precompiler.GamlAnnotations.facets;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.runtime.ExecutionResult;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.descriptions.IDescription;
import msi.gaml.descriptions.VariableDescription;
import msi.gaml.statements.AbstractStatementSequence;
import msi.gaml.statements.Facets.Facet;
import msi.gaml.statements.IStatement;

import java.util.Map;

import msi.gama.common.interfaces.IKeyword;
import msi.gaml.types.IType;
import msi.gaml.variables.IVariable;
import ummisco.gama.chemmisol.Reaction;
import ummisco.gama.chemmisol.types.ChemicalComponent;
import ummisco.gama.chemmisol.types.ChemicalComponentType;
import ummisco.gama.chemmisol.types.ChemicalSystem;

@symbol(name = ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT, kind = ISymbolKind.BEHAVIOR, with_sequence = true)
@inside(kinds = { ISymbolKind.SPECIES })
@facets(value = { @facet(name = ChemicalSystemStatement.PH, type = IType.FLOAT, optional = true) })
public class ChemicalSystemStatement extends AbstractStatementSequence {

	static final String CHEMICAL_SYSTEM_STATEMENT = "chemical_system";
	static final String PH = "ph";

	public ChemicalSystemStatement(final IDescription desc) {
		super(desc);
	}

	@Override
	public ChemicalSystem privateExecuteIn(IScope scope) throws GamaRuntimeException {
		// Executes the "chemical_system" statement itself by initializing a new
		// ChemicalSystem
		ChemicalSystem chemical_system = new ChemicalSystem(
				scope.getCurrentSymbol().getDescription().getLitteral(IKeyword.NAME));

		// ChemicalComponent variables post-treatment
		for (IVariable var : scope.getAgent().getSpecies().getVars()) {
			System.out.println("Var: " + var.getKeyword() + "-" + var.getName());
			for(Facet facet : var.getDescription().getFacets().getFacets()) {
				System.out.println("  Var facets: " + facet);
				
			}
			if (var.getKeyword().equals(ChemicalComponentType.CHEMICAL_COMPONENT_TYPE)) {
				ChemicalComponent component = (ChemicalComponent) scope.getAgent().getAttribute(var.getName());
				// Sets the name of the component according to the name of the variable
				component.setName(var.getName());
				// Adds the component to the system
				chemical_system.addComponent(component);
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
				component.setName(command.getDescription().getName());
				// Adds the local component to the chemical system
				chemical_system.addComponent(component);
			}

			if (command instanceof ReactionStatement) {
				Reaction reaction = (Reaction) result.getValue();
				chemical_system.addReaction(reaction);
			}
		}

		return chemical_system;
	}
}
