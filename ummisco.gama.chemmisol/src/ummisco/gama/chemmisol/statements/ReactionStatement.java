package ummisco.gama.chemmisol.statements;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.precompiler.GamlAnnotations.facet;
import msi.gama.precompiler.GamlAnnotations.facets;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.compilation.annotations.validator;
import msi.gaml.descriptions.IDescription;
import msi.gaml.statements.AbstractStatement;
import msi.gaml.types.IType;

@symbol(name = ReactionStatement.REACTION_STATEMENT, kind=ISymbolKind.SINGLE_STATEMENT, with_sequence=true)
@inside(kinds = { ISymbolKind.BEHAVIOR })
@facets(value = {
		@facet(name = IKeyword.NAME, type=IType.STRING, optional=true)
}, omissible=IKeyword.NAME)
@validator(value = ReactionValidator.class)
public class ReactionStatement extends AbstractStatement {
	static final String REACTION_STATEMENT = "reaction";
	
	public ReactionStatement(final IDescription desc) {
		super(desc);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

}
