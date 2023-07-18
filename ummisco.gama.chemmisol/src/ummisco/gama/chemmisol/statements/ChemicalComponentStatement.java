package ummisco.gama.chemmisol.statements;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.precompiler.GamlAnnotations.facet;
import msi.gama.precompiler.GamlAnnotations.facets;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.descriptions.IDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.statements.AbstractStatement;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Component;
import ummisco.gama.chemmisol.Phase;
import ummisco.gama.chemmisol.types.ChemicalPhaseType;

@symbol(name = ChemicalComponentStatement.CHEMICAL_COMPONENT_STATEMENT, kind=ISymbolKind.SINGLE_STATEMENT, with_sequence=false)
@inside(kinds = { ISymbolKind.SPECIES, ISymbolKind.BEHAVIOR })
@facets(value = {
		@facet(name = IKeyword.NAME, type=IType.ID, optional=false),
		@facet(name = ChemicalComponentStatement.CHEMICAL_PHASE, type = ChemicalPhaseType.CHEMICAL_PHASE_TYPE_ID, optional=true),
		@facet(name = ChemicalComponentStatement.CHEMICAL_CONCENTRATION, type=IType.FLOAT, optional=true)
}, omissible=IKeyword.NAME)
public class ChemicalComponentStatement extends AbstractStatement {
	public static final String CHEMICAL_CONCENTRATION = "concentration";
	public static final String CHEMICAL_PHASE = "phase";
	public static final String CHEMICAL_COMPONENT_STATEMENT = "chemical_component";
	
	private final String component_name;
	private final IExpression concentration_exp;
	private final IExpression phase_exp;
	
	public ChemicalComponentStatement(IDescription desc) {
		super(desc);
		component_name = desc.getLitteral(IKeyword.NAME);
		if(desc.hasFacet(CHEMICAL_CONCENTRATION)) {
			concentration_exp = desc.getFacetExpr(CHEMICAL_CONCENTRATION);
		} else {
			concentration_exp = null;
		}
		if(desc.hasFacet(CHEMICAL_PHASE)) {
			phase_exp = desc.getFacetExpr(CHEMICAL_PHASE);
		} else {
			phase_exp = null;
		}
	}

	@Override
	protected Component privateExecuteIn(IScope scope) throws GamaRuntimeException {
		Component chemical_component = new Component(
				component_name,
				phase_exp == null ? Phase.AQUEOUS :
					(Phase) scope.evaluate(phase_exp, scope.getAgent()).getValue(),
				concentration_exp == null ? 0.0 :
					(Double) scope.evaluate(concentration_exp, scope.getAgent()).getValue()
				);
		return chemical_component;
	}
}
