package ummisco.gama.chemmisol.statements;

import msi.gama.precompiler.GamlAnnotations.facet;
import msi.gama.precompiler.GamlAnnotations.facets;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.descriptions.IDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.statements.AbstractStatementSequence;
import msi.gama.common.interfaces.IKeyword;
import msi.gaml.types.IType;
import msi.gaml.types.Types;
import ummisco.gama.chemmisol.ChemicalSystem;

@symbol(name = ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT, kind=ISymbolKind.BEHAVIOR, with_sequence=true)
@inside(kinds = { ISymbolKind.SPECIES })
@facets(value = {
		@facet(name = IKeyword.NAME, type=IType.ID, optional=true)
}, omissible=IKeyword.NAME)
public class ChemicalSystemStatement extends AbstractStatementSequence {

	static final String CHEMICAL_SYSTEM_STATEMENT = "chemical_system";

	
	public ChemicalSystemStatement(final IDescription desc) {
	    super(desc);
	    String chemical_system_name = hasFacet(IKeyword.NAME) ? desc.getLitteral(IKeyword.NAME) : "";
	    System.out.println("New chem system architecture: " + getName() + " - " + chemical_system_name);
	}
	
	@Override
	public Object privateExecuteIn(IScope scope) throws GamaRuntimeException {

		ChemicalSystem chemical_system = new ChemicalSystem();
		System.out.println("Scope:");
		System.out.println("  Name:" + scope.getName());
		System.out.println("  Symbol:" + scope.getCurrentSymbol().getKeyword() + "-" + scope.getCurrentSymbol().getName());
		scope.getGui().getConsole().informConsole("This is a super chemical system: " + getKeyword() + "-" + getName() + "-" + name, scope.getRoot());
		return null;
	}
}
