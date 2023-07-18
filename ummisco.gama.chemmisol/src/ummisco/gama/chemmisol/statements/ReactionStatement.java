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
import msi.gaml.compilation.annotations.serializer;
import msi.gaml.compilation.annotations.validator;
import msi.gaml.descriptions.IDescription;
import msi.gaml.statements.AbstractStatement;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Component;

@symbol(name = ReactionStatement.REACTION_STATEMENT, kind=ISymbolKind.SINGLE_STATEMENT, with_sequence=false)
@inside(kinds = { ISymbolKind.BEHAVIOR })
@facets(value = {
		@facet(name = IKeyword.NAME, type=IType.STRING, optional=true),
		@facet(name = ReactionStatement.EQUATION, type=IType.INT, optional=false),
		@facet(name = ReactionStatement.LOG_K, type=IType.FLOAT, optional=false)
})
@validator(value = ReactionValidator.class)
public class ReactionStatement extends AbstractStatement {
	static final String REACTION_STATEMENT = "reaction";
	static final String EQUATION = "chemical_equation";
	static final String LOG_K = "log_k";
	
	public ReactionStatement(final IDescription desc) {
		super(desc);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		System.out.println("Running reaction statement: " + scope.getCurrentSymbol());
		return null;
	}

	@operator({"*"})
	public static Object stoichiometry(final IScope scope, int coef, Component component) {
		System.out.println("* operator: " + scope.getCurrentSymbol().getName());
		return null;
	}
}
