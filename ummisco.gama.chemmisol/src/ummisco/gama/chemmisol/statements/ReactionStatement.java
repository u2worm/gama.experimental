package ummisco.gama.chemmisol.statements;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.facet;
import msi.gama.precompiler.GamlAnnotations.facets;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.no_test;
import msi.gama.precompiler.GamlAnnotations.operator;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.precompiler.GamlAnnotations.usage;
import msi.gama.precompiler.GamlAnnotations.example;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.compilation.IDescriptionValidator;
import msi.gaml.compilation.annotations.validator;
import msi.gaml.descriptions.IDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.statements.AbstractStatement;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Reaction;
import ummisco.gama.chemmisol.Reagent;
import ummisco.gama.chemmisol.types.ChemicalComponent;
import ummisco.gama.chemmisol.types.ChemicalEquation;
import ummisco.gama.chemmisol.types.ChemicalEquationType;
import ummisco.gama.chemmisol.types.ChemicalSpecies;

@symbol(name = ReactionStatement.REACTION_STATEMENT, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = {ISymbolKind.BEHAVIOR})
@facets(value = {
		@facet(name = IKeyword.NAME, type = IType.STRING, optional = false, doc = @doc("Name of the reaction. Should be unique within each chemical_system.")),
		@facet(name = ReactionStatement.EQUATION, type = ChemicalEquationType.CHEMICAL_EQUATION_TYPE_ID, optional = false, doc = @doc("""
				Chemical equation of the reaction. Reagents must \
				correspond to chemical_components and chemical_species declared \
				in the agent scope, or locally within the chemical_system \
				statement if the concentrations of some species do not need to \
				be tracked.
				""")),
		@facet(name = ReactionStatement.LOG_K, type = IType.FLOAT, optional = false, doc = @doc("""
				Base 10 logarithm of the equilibrium constant of the reaction.
				The equilibrium constant of the reaction is defined as the value \
				of the reaction quotient at equilibrium.
				"""))})
@validator(value = ReactionStatement.ReactionValidator.class)
@doc("""
		A reaction statement can be defined in a chemical_system sequence \
		statement to define how chemical components interact to produce other \
		chemical species. The chemical_equation must contain only \
		chemical_components as reagents, except one, that is defined as the \
		produced chemical_species of the reaction.
		Reactions are used to solve the equilibrium of the system, so that at \
		equilibrium the reaction_quotient of all reactions should be equal to K.
		""")
public class ReactionStatement extends AbstractStatement {
	static final String CHEMICAL_OPERATORS_CATEGORY = "Chemical operators";
	static final String REACTION_STATEMENT = "reaction";
	static final String EQUATION = "chemical_equation";
	static final String LOG_K = "log_k";
	final IExpression name_exp;
	final IExpression log_k_exp;
	final IExpression equation_exp;

	public static class ReactionValidator
			implements
				IDescriptionValidator<IDescription> {

		@Override
		public void validate(IDescription description) {
			if (!description
					.isIn(ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT)) {
				description.error(
						"A reaction can only be specified within a chemical_system.");
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
	protected Reaction privateExecuteIn(IScope scope)
			throws GamaRuntimeException {
		Reaction reaction = new Reaction(
				(String) scope.evaluate(name_exp, scope.getAgent()).getValue(),
				((Number) scope.evaluate(log_k_exp, scope.getAgent())
						.getValue()).doubleValue());
		ChemicalEquation equation = (ChemicalEquation) scope
				.evaluate(equation_exp, scope.getAgent()).getValue();

		// Ensures at runtime reactives and products are properly specified
		ChemicalEquation.validate(equation, scope);

		for (Reagent reagent : equation.getReagents()) {
			reaction.addReagent(reagent);
		}
		return reaction;
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Assigns the stoichiometric coefficient to the species in the reaction.
	 * 
	 * @param scope GAMA scope
	 * @param coef stoichiometric coefficient
	 * @param species chemical species
	 * @return Built chemical equation.
	 */
	@operator(value = IKeyword.MULTIPLY, category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value = """
					if the left operand is an `int` and the right operand is a \
					`chemical_species`, assigns the stoichiometric coefficient \
					to the component in the reaction and returns the built \
					chemical equation.
					""",
					examples= {@example("""
							chemical_species HPO4 <- chemical_species([AQUEOUS]);
							chemical_equation eq <- 3 * HPO4;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation stoichiometry(final IScope scope, int coef,
			ChemicalSpecies species) {
		return new ChemicalEquation().add(new Reagent(
				species.getName(), coef, species.getPhase()));
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Assigns the stoichiometric coefficient to the component in the reaction.
	 * 
	 * @param scope GAMA scope
	 * @param coef stoichiometric coefficient
	 * @param component chemical component
	 * @return Built chemical equation.
	 */
	@operator(value = IKeyword.MULTIPLY, category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages = {
			@usage(value="""
					if the left operand is an `int` and the right operand is a \
					`chemical_component`, assigns the stoichiometric coefficient \
					to the component in the reaction and returns the built \
					chemical equation.
					""",
					examples= {@example("""
							chemical_component PO4 <- chemical_component([AQUEOUS, 4.5e-6#mol/#l]);
							chemical_equation eq <- 2 * PO4;
							""")}
					)
	})
	@no_test
	public static ChemicalEquation stoichiometry(final IScope scope, int coef,
			ChemicalComponent component) {
		return stoichiometry(scope, coef, component.getSpecies());
	}

	/**
	 * 
	 * Chemical equation builder operator.
	 * 
	 * Merges the two chemical equation, concatenating the lists of reagents.
	 * 
	 * @param scope GAMA scope
	 * @param r1 left chemical equation
	 * @param r2 right chemical equation
	 * @return Built chemical equation.
	 */
	@operator(value = IKeyword.PLUS, category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if both operands are `chemical_equations`, concatenates the \
					reagents of the two equations and returns the built \
					equation.
					""",
					examples= {@example("""
							chemical_species OH <- chemical_species([AQUEOUS]);
							chemical_component PO4 <- chemical_component([AQUEOUS, 4.5e-6#mol/#l]);
							chemical_equation eq <- 4*OH + 3*PO4;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation addReagents(final IScope scope,
			ChemicalEquation r1, ChemicalEquation r2) {
		return new ChemicalEquation().addAll(r1.getReagents())
				.addAll(r2.getReagents());
	}

	/**
	 * 
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to the right species, and
	 * merges the obtained chemical equation with the left equation.
	 * 
	 * @param scope GAMA scope
	 * @param r    chemical equation
	 * @param s    chemical species
	 * @return Built chemical equation.
	 */
	@operator(value = IKeyword.PLUS, category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if the left operand is a `chemical_equation` and the right \
					operand is a `chemical_species`, assigns a stoichiometric \
					coefficient of +1 to the species and returns the merged \
					equations.
					""",
					examples= {@example("""
							chemical_species OH <- chemical_species([AQUEOUS]);
							chemical_component PO4 <- chemical_component([AQUEOUS, 4.5e-6#mol/#l]);
							chemical_equation eq <-3*PO4 + OH;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation addReagents(final IScope scope,
			ChemicalEquation r, ChemicalSpecies s) {
		return addReagents(scope, r, stoichiometry(scope, 1, s));
	}

	/**
	 * 
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to the right component, and
	 * merges the obtained chemical equation with the left equation.
	 * 
	 * @param scope GAMA scope
	 * @param r    chemical equation
	 * @param c    chemical component
	 * @return Built chemical equation.
	 */
	@operator(value = IKeyword.PLUS, category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if the left operand is a `chemical_equation` and the right \
					operand is a `chemical_component`, assigns a stoichiometric \
					coefficient of +1 to the component and returns the merged \
					equations.
					""",
					examples= {@example("""
							chemical_species OH <- chemical_species([AQUEOUS]);
							chemical_component PO4 <- chemical_component([AQUEOUS, 4.5e-6#mol/#l]);
							chemical_equation eq <-3*OH + PO4;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation addReagents(final IScope scope,
			ChemicalEquation r, ChemicalComponent c) {
		return addReagents(scope, r, c.getSpecies());
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to the left species, and
	 * merges the obtained chemical equation with the right equation.
	 * 
	 * @param scope GAMA scope
	 * @param s chemical species
	 * @param r chemical equation
	 * @return Built chemical equation.
	 */
	@operator(value = IKeyword.PLUS, category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if the left operand is a `chemical_species` and the right \
					operand is a `chemical_equation`, assigns a stoichiometric \
					coefficient of +1 to the species and returns the merged \
					equations.
					""",
					examples= {@example("""
							chemical_species OH <- chemical_species([AQUEOUS]);
							chemical_component PO4 <- chemical_component([AQUEOUS, 4.5e-6#mol/#l]);
							chemical_equation eq <-OH + 3*PO4;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation addReagents(final IScope scope,
			ChemicalSpecies s, ChemicalEquation r) {
		return addReagents(scope, stoichiometry(scope, 1, s), r);
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to the left component, and
	 * merges the obtained chemical equation with the right equation.
	 * 
	 * @param scope GAMA scope
	 * @param c chemical component
	 * @param r chemical equation
	 * @return Built chemical equation.
	 */
	@operator(value = IKeyword.PLUS, category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if the left operand is a `chemical_component` and the right \
					operand is a `chemical_equation`, assigns a stoichiometric \
					coefficient of +1 to the component and returns the merged \
					equations.
					""",
					examples= {@example("""
							chemical_species OH <- chemical_species([AQUEOUS]);
							chemical_component PO4 <- chemical_component([AQUEOUS, 4.5e-6#mol/#l]);
							chemical_equation eq <- PO4 + 3*OH;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation addReagents(final IScope scope,
			ChemicalComponent c, ChemicalEquation r) {
		return addReagents(scope, c.getSpecies(), r);
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to both species and merges the
	 * obtained chemical equations.
	 * 
	 * @param scope GAMA scope
	 * @param s1 left chemical species
	 * @param s2 right chemical species
	 * @return Built chemical equation.
	 */
	@operator(value = IKeyword.PLUS, category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if both operands are `chemical_species`, assigns a \
					stoichiometric coefficient of +1 to both species and returns \
					the merged equations.
					""",
					examples= {@example("""
							chemical_species OH <- chemical_species([AQUEOUS]);
							chemical_species HPO4 <- chemical_species([AQUEOUS]);
							chemical_equation eq <- HPO4 + OH;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation addReagents(final IScope scope,
			ChemicalSpecies s1, ChemicalSpecies s2) {
		return addReagents(scope, stoichiometry(scope, 1, s1),
				stoichiometry(scope, 1, s2));
	}

	/**
	 * 
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to the left component and to
	 * the right species and merges the obtained chemical equations.
	 * 
	 * @param scope GAMA scope
	 * @param c left chemical component
	 * @param s right chemical species
	 * @return Built chemical equation.
	 */
	@operator(value = IKeyword.PLUS, category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if the left operand is a `chemical_component` and the right \
					operand is a `chemical_species`, assigns a stoichiometric \
					coefficient of +1 to both operands and returns the merged \
					equations.
					""",
					examples= {@example("""
							chemical_component H <- chemical_component([AQUEOUS, 10^-7#mol/#l]);
							chemical_species HPO4 <- chemical_species([AQUEOUS]);
							chemical_equation eq <- H + HPO4;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation addReagents(final IScope scope,
			ChemicalComponent c, ChemicalSpecies s) {
		return addReagents(scope, c.getSpecies(), s);
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to the left species and to the
	 * right component and merges the obtained chemical equations.
	 * 
	 * @param scope GAMA scope
	 * @param s chemical species
	 * @param c chemical component
	 * @return Built chemical equation.
	 */
	@operator(value = IKeyword.PLUS, category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if the left operand is a `chemical_species` and the right \
					operand is a `chemical_component`, assigns a stoichiometric \
					coefficient of +1 to both operands and returns the merged \
					equations.
					""",
					examples= {@example("""
							chemical_component H <- chemical_component([AQUEOUS, 10^-7#mol/#l]);
							chemical_species HPO4 <- chemical_species([AQUEOUS]);
							chemical_equation eq <- HPO4 + H;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation addReagents(final IScope scope,
			ChemicalSpecies s, ChemicalComponent c) {
		return addReagents(scope, s, c.getSpecies());
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to both components and merges
	 * the obtained chemical equations.
	 * 
	 * @param scope GAMA scope
	 * @param c1 left chemical component
	 * @param c2 right chemical component
	 * @return Built chemical equation.
	 */
	@operator(value = IKeyword.PLUS, category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if both operands are `chemical_component`s, assigns a \
					stoichiometric coefficient of +1 to both components and returns \
					the merged equations.
					""",
					examples= {@example("""
							chemical_component H <- chemical_component([AQUEOUS, 10^-7#mol/#l]);
							chemical_component HPO4 <- chemical_component([AQUEOUS, 1.4e-6#mol/#l]);
							chemical_equation eq <- H + HPO4;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation addReagents(final IScope scope,
			ChemicalComponent c1, ChemicalComponent c2) {
		return addReagents(scope, c1.getSpecies(), c2.getSpecies());
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Reverts the stoichiometric coefficients of the reagents of the right \
	 * equation so that they become the products of the reaction, then merges the \
	 * obtained equation with the left equation.
	 * 
	 * @param scope GAMA scope
	 * @param r1 left chemical equation
	 * @param r2 right chemical equation
	 * @return Built chemical equation.
	 */
	@operator(value = "=", category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if both operands are `chemical_equations`s, reverts the \
					stoichiometric coefficients of the reagents of the right \
					equation so they become the products of the reaction and \
					returns the merged equations.
					""",
					examples= {@example("""
							chemical_component H <- chemical_component([AQUEOUS, 10^-7#mol/#l]);
							chemical_component H2O <- chemical_component([SOLVENT]);
							chemical_species H2 <- chemical_species([AQUEOUS]);
							chemical_equation eq <-  H2 + 2 * OH = 2 * H2O;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation equals(final IScope scope,
			ChemicalEquation r1, ChemicalEquation r2) {
		for (Reagent reagent : r2.getReagents()) {
			reagent.setCoefficient(-reagent.getCoefficient());
		}
		return addReagents(scope, r1, r2);
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to the right species and merges \
	 * the obtained equation with the left equation using the = operator.
	 * 
	 * @param scope GAMA scope
	 * @param r chemical equation
	 * @param s chemical species
	 * @return Built chemical equation.
	 */
	@operator(value = "=", category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if the left operand is a `chemical_equation` and the right \
					operand is a `chemical_species`, assigns a stoichiometric \
					coefficient of +1 to the species and returns the equations \
					merged with the = operator.
					""",
					examples= {@example("""
							chemical_component H <- chemical_component([AQUEOUS, 10^-7#mol/#l]);
							chemical_component PO4 <- chemical_component([AQUEOUS, 0.1e-6#mol/#l]);
							chemical_species H3PO4 <- chemical_species([AQUEOUS]);
							chemical_equation eq <-  3*H + PO4 = H3PO4;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation equals(final IScope scope,
			ChemicalEquation r, ChemicalSpecies s) {
		return equals(scope, r, stoichiometry(scope, 1, s));
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to the right component and
	 * merges the obtained equation with the left equation using the = operator.
	 * 
	 * @param scope
	 * @param r
	 * @param c
	 * @return
	 */
	@operator(value = "=", category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if the left operand is a `chemical_equation` and the right \
					operand is a `chemical_component`, assigns a stoichiometric \
					coefficient of +1 to the component and returns the equations \
					merged with the = operator.
					""",
					examples= {@example("""
							chemical_species OH <- chemical_species([AQUEOUS]);
							chemical_component H <- chemical_component([AQUEOUS, 10^-7#mol/#l]);
							chemical_component H2O <- chemical_component([SOLVENT]);
							chemical_equation eq <-  H + OH = H2O;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation equals(final IScope scope,
			ChemicalEquation r, ChemicalComponent c) {
		return equals(scope, r, stoichiometry(scope, 1, c));
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to the left species and merges
	 * the obtained equation with the right equation using the = operator.
	 * 
	 * @param scope GAMA scope
	 * @param s chemical species
	 * @param r chemical reaction
	 * @return Built chemical equation.
	 */
	@operator(value = "=", category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if the left operand is a `chemical_species` and the right \
					operand is a `chemical_equation`, assigns a stoichiometric \
					coefficient of +1 to the species and returns the equations \
					merged with the = operator.
					""",
					examples= {@example("""
							chemical_component H <- chemical_component([AQUEOUS, 10^-7#mol/#l]);
							chemical_component PO4 <- chemical_component([AQUEOUS, 0.1e-6#mol/#l]);
							chemical_species H3PO4 <- chemical_species([AQUEOUS]);
							chemical_equation eq <-  H3PO4 = 3*H + PO4;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation equals(final IScope scope, ChemicalSpecies s,
			ChemicalEquation r) {
		return equals(scope, stoichiometry(scope, 1, s), r);
	}

	/**
	 * Chemical equation builder operator.
	 * 
	 * Assigns a stoichiometric coefficient of +1 to the left component and
	 * merges the obtained equation with the right equation using the =
	 * operator.
	 * 
	 * @param scope GAMA scope
	 * @param c chemical component
	 * @param r chemical equation
	 * @return Built chemical equation.
	 */
	@operator(value = "=", category = CHEMICAL_OPERATORS_CATEGORY)
	@doc(usages= {
			@usage(value="""
					if the left operand is a `chemical_component` and the right \
					operand is a `chemical_equation`, assigns a stoichiometric \
					coefficient of +1 to the component and returns the equations \
					merged with the = operator.
					""",
					examples= {@example("""
							chemical_species OH <- chemical_species([AQUEOUS]);
							chemical_component H <- chemical_component([AQUEOUS, 10^-7#mol/#l]);
							chemical_component H2O <- chemical_component([SOLVENT]);
							chemical_equation eq <-  H2O = H + OH;
							""")}
			)
	})
	@no_test
	public static ChemicalEquation equals(final IScope scope,
			ChemicalComponent c, ChemicalEquation r) {
		return equals(scope, stoichiometry(scope, 1, c), r);
	}

//	@operator(value = "=", category = CHEMICAL_OPERATORS_CATEGORY)
//	@doc(value = """
//			Chemical equation builder operator.
//			
//			Assigns a stoichiometric coefficient of +1 to both species and
//			merges the obtained equations using the = operator.
//			""", returns = "Built chemical equation.")
//	@no_test
//	public static ChemicalEquation equals(final IScope scope,
//			ChemicalSpecies s1, ChemicalSpecies s2) {
//		return equals(scope, stoichiometry(scope, 1, s1),
//				stoichiometry(scope, 1, s2));
//	}
//
//	@operator(value = "=", category = CHEMICAL_OPERATORS_CATEGORY)
//	@doc(value = """
//			Chemical equation builder operator.
//			
//			Assigns a stoichiometric coefficient of +1 to the left component and
//			to the right species and merges the obtained equations usint the =
//			operator.
//			""", returns = "Built chemical equation.")
//	@no_test
//	public static ChemicalEquation equals(final IScope scope,
//			ChemicalComponent c1, ChemicalSpecies s2) {
//		return equals(scope, stoichiometry(scope, 1, c1),
//				stoichiometry(scope, 1, s2));
//	}
//
//	@operator(value = "=", category = CHEMICAL_OPERATORS_CATEGORY)
//	@doc(value = """
//			Chemical equation builder operator.
//			
//			Assigns a stoichiometric coefficient of +1 to the left species and
//			to the right component and merges the obtained equations usint the =
//			operator.
//			""", returns = "Built chemical equation.")
//	@no_test
//	public static ChemicalEquation equals(final IScope scope,
//			ChemicalSpecies s1, ChemicalComponent c2) {
//		return equals(scope, stoichiometry(scope, 1, s1),
//				stoichiometry(scope, 1, c2));
//	}
//
//	@operator(value = "=", category = CHEMICAL_OPERATORS_CATEGORY)
//	@doc(value = """
//			Chemical equation builder operator.
//			
//			Assigns a stoichiometric coefficient of +1 to both components and
//			merges the obtained equations using the = operator.
//			""", returns = "Built chemical equation.")
//	@no_test
//	public static ChemicalEquation equals(final IScope scope,
//			ChemicalComponent c1, ChemicalComponent c2) {
//		return equals(scope, stoichiometry(scope, 1, c1),
//				stoichiometry(scope, 1, c2));
//	}
}
