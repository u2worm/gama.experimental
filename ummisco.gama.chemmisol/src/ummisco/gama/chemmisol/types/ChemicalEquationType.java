package ummisco.gama.chemmisol.types;

import java.util.LinkedList;
import java.util.List;

import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.type;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Reagent;

@type(name = ChemicalEquationType.CHEMICAL_EQUATION_TYPE,
	id= ChemicalEquationType.CHEMICAL_EQUATION_TYPE_ID,
	kind=ISymbolKind.Variable.REGULAR,
	wraps= { ChemicalEquation.class },
	internal=true
)
@doc(value="""
		A chemical_equation represents the reagents of a reaction and associated \
		stoichiometric coefficients.
		""",
		comment="""
				chemical_equation instances are automatically built by chemical \
				operators (+,*,= applied to chemical_species and \
				chemical_components).
				"""
)
public class ChemicalEquationType extends GamaType<ChemicalEquation> {
	public static final String CHEMICAL_EQUATION_TYPE = "chemical_equation";
	public static final int CHEMICAL_EQUATION_TYPE_ID = IType.AVAILABLE_TYPES+4;

	@Override
	public ChemicalEquation getDefault() {
		// TODO Auto-generated method stub
		return new ChemicalEquation();
	}

	@Override
	public boolean canCastToConst() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@doc("""
			Default cast operator.
			""")
	public ChemicalEquation cast(IScope scope, Object obj, Object param, boolean copy) throws GamaRuntimeException {
		// TODO Auto-generated method stub
		if(obj instanceof ChemicalEquation) {
			ChemicalEquation equation = (ChemicalEquation) obj;
			if(copy) {
				ChemicalEquation equation_copy = new ChemicalEquation();
				List<Reagent> reagents = new LinkedList<Reagent>();
				for(Reagent reagent : equation.getReagents()) {
					reagents.add(new Reagent(reagent.getName(), reagent.getCoefficient(), reagent.getPhase()));
				}
				equation_copy.addAll(reagents);
				return equation_copy;
			}
			return equation;
		}
		return null;
	}

}
