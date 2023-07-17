package ummisco.gama.chemmisol.statements;

import msi.gama.precompiler.GamlAnnotations.facet;
import msi.gama.precompiler.GamlAnnotations.facets;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.descriptions.IDescription;
import msi.gaml.statements.AbstractStatementSequence;
import msi.gama.common.interfaces.IKeyword;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.types.ChemicalSystem;

@symbol(name = ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT, kind=ISymbolKind.BEHAVIOR, with_sequence=true)
@inside(kinds = { ISymbolKind.SPECIES })
@facets(value = {
		@facet(name = IKeyword.NAME, type=IType.ID, optional=true)
}, omissible=IKeyword.NAME)
public class ChemicalSystemStatement extends AbstractStatementSequence {

	static final String CHEMICAL_SYSTEM_STATEMENT = "chemical_system";
	
	
	public ChemicalSystemStatement(final IDescription desc) {
	    super(desc);
	}
	
	@Override
	public ChemicalSystem privateExecuteIn(IScope scope) throws GamaRuntimeException {
		ChemicalSystem chemical_system = new ChemicalSystem(
				scope.getCurrentSymbol().getDescription().getLitteral(IKeyword.NAME)
				);
		return chemical_system;
	}
}
