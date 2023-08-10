package ummisco.gama.chemmisol.statements;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.precompiler.GamlAnnotations.facet;
import msi.gama.precompiler.GamlAnnotations.facets;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.operator;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.compilation.IDescriptionValidator;
import msi.gaml.compilation.annotations.validator;
import msi.gaml.descriptions.IDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.statements.AbstractStatement;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Reaction;
import ummisco.gama.chemmisol.types.ChemicalComponent;
import ummisco.gama.chemmisol.types.ChemicalEquation;
import ummisco.gama.chemmisol.types.ChemicalEquationType;
import ummisco.gama.chemmisol.types.ChemicalSpecies;
import ummisco.gama.chemmisol.types.ChemicalEquation.ChemicalEquationItem;

@symbol(name = ReactionStatement.REACTION_STATEMENT, kind=ISymbolKind.SINGLE_STATEMENT, with_sequence=false)
@inside(kinds = { ISymbolKind.BEHAVIOR })
@facets(value = {
		@facet(name = IKeyword.NAME, type=IType.STRING, optional=false),
		@facet(name = ReactionStatement.EQUATION, type=ChemicalEquationType.CHEMICAL_EQUATION_TYPE_ID, optional=false),
		@facet(name = ReactionStatement.LOG_K, type=IType.FLOAT, optional=false)
})
@validator(value = ReactionStatement.ReactionValidator.class)
public class ReactionStatement extends AbstractStatement {
	static final String REACTION_STATEMENT = "reaction";
	static final String EQUATION = "chemical_equation";
	static final String LOG_K = "log_k";
	final IExpression name_exp;
	final IExpression log_k_exp;
	final IExpression equation_exp;
	
	public static class ReactionValidator implements IDescriptionValidator<IDescription> {

		@Override
		public void validate(IDescription description) {
			if (!description.isIn(ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT)) {
				description.error("A reaction can only be specified within a chemical_system.");
			}
		}

	}

	public ReactionStatement(final IDescription desc) {
		super(desc);
		this.name_exp = desc.getFacet(IKeyword.NAME).getExpression();
		this.log_k_exp = desc.getFacet(LOG_K).getExpression();
		this.equation_exp = desc.getFacet(EQUATION).getExpression();
	}

	@Override
	protected Reaction privateExecuteIn(IScope scope) throws GamaRuntimeException {
		Reaction reaction = new Reaction(
				(String) scope.evaluate(name_exp, scope.getAgent()).getValue(),
				((Number) scope.evaluate(log_k_exp, scope.getAgent()).getValue()).doubleValue()
				);
		ChemicalEquation equation = (ChemicalEquation) scope.evaluate(equation_exp, scope.getAgent()).getValue();

		// Ensures at runtime reactives and products are properly specified
		ChemicalEquation.validate(equation, scope);

		for(ChemicalEquationItem item : equation.getItems()) {
			reaction.addReagent(item.getName(), item.getCoefficient(), item.getPhase());
		}
		return reaction;
	}

	@operator("*")
	public static ChemicalEquation stoichiometry(final IScope scope, int coef, ChemicalSpecies species) {
		return new ChemicalEquation().add(
				new ChemicalEquationItem(species.getName(), species.getPhase(), coef));
	}

	@operator("*")
	public static ChemicalEquation stoichiometry(final IScope scope, int coef, ChemicalComponent component) {
		return stoichiometry(scope, coef, component.getSpecies());
	}
	
	@operator("+")
	public static ChemicalEquation addReagents(final IScope scope, ChemicalEquation r1, ChemicalEquation r2) {
		return new ChemicalEquation()
				.addAll(r1.getItems())
				.addAll(r2.getItems());
	}

	@operator("+")
	public static ChemicalEquation addReagents(final IScope scope, ChemicalEquation r, ChemicalSpecies s) {
		return addReagents(scope, r, stoichiometry(scope, 1, s));
	}

	@operator("+")
	public static ChemicalEquation addReagents(final IScope scope, ChemicalEquation r, ChemicalComponent c) {
		return addReagents(scope, r, c.getSpecies());
	}

	@operator("+")
	public static ChemicalEquation addReagents(final IScope scope, ChemicalSpecies s, ChemicalEquation r) {
		return addReagents(scope, stoichiometry(scope, 1, s), r);
	}

	@operator("+")
	public static ChemicalEquation addReagents(final IScope scope, ChemicalComponent c, ChemicalEquation r) {
		return addReagents(scope, c.getSpecies(), r);
	}

	@operator("+")
	public static ChemicalEquation addReagents(final IScope scope, ChemicalSpecies s1, ChemicalSpecies s2) {
		return addReagents(scope, stoichiometry(scope, 1, s1), stoichiometry(scope, 1, s2));
	}

	@operator("+")
	public static ChemicalEquation addReagents(final IScope scope, ChemicalComponent c1, ChemicalSpecies s2) {
		return addReagents(scope, c1.getSpecies(), s2);
	}

	@operator("+")
	public static ChemicalEquation addReagents(final IScope scope, ChemicalSpecies s1, ChemicalComponent c2) {
		return addReagents(scope, s1, c2.getSpecies());
	}

	@operator("+")
	public static ChemicalEquation addReagents(final IScope scope, ChemicalComponent c1, ChemicalComponent c2) {
		return addReagents(scope, c1.getSpecies(), c2.getSpecies());
	}

	@operator("=")
	public static ChemicalEquation equals(final IScope scope, ChemicalEquation r1, ChemicalEquation r2) {
		for(ChemicalEquationItem item : r2.getItems()) {
			item.revertCoefficient();
		}
		return addReagents(scope, r1, r2);
	}

	@operator("=")
	public static ChemicalEquation equals(final IScope scope, ChemicalEquation r, ChemicalSpecies s) {
		return equals(scope, r, stoichiometry(scope, 1, s));
	}

	@operator("=")
	public static ChemicalEquation equals(final IScope scope, ChemicalEquation r, ChemicalComponent c) {
		return equals(scope, r, stoichiometry(scope, 1, c));
	}

	@operator("=")
	public static ChemicalEquation equals(final IScope scope, ChemicalSpecies s, ChemicalEquation r) {
		return equals(scope, stoichiometry(scope, 1, s), r);
	}

	@operator("=")
	public static ChemicalEquation equals(final IScope scope, ChemicalComponent c, ChemicalEquation r) {
		return equals(scope, stoichiometry(scope, 1, c), r);
	}

	@operator("=")
	public static ChemicalEquation equals(final IScope scope, ChemicalSpecies s1, ChemicalSpecies s2) {
		return equals(scope, stoichiometry(scope, 1, s1), stoichiometry(scope, 1, s2));
	}

	@operator("=")
	public static ChemicalEquation equals(final IScope scope, ChemicalComponent c1, ChemicalSpecies s2) {
		return equals(scope, stoichiometry(scope, 1, c1), stoichiometry(scope, 1, s2));
	}

	@operator("=")
	public static ChemicalEquation equals(final IScope scope, ChemicalSpecies s1, ChemicalComponent c2) {
		return equals(scope, stoichiometry(scope, 1, s1), stoichiometry(scope, 1, c2));
	}

	@operator("=")
	public static ChemicalEquation equals(final IScope scope, ChemicalComponent c1, ChemicalComponent c2) {
		return equals(scope, stoichiometry(scope, 1, c1), stoichiometry(scope, 1, c2));
	}
}
