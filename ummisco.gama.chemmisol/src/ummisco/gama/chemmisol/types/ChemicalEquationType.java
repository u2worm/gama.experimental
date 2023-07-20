package ummisco.gama.chemmisol.types;

import msi.gama.precompiler.GamlAnnotations.type;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;

@type(name = ChemicalEquationType.CHEMICAL_EQUATION_TYPE,
	id= ChemicalEquationType.CHEMICAL_EQUATION_TYPE_ID,
	kind=ISymbolKind.Variable.REGULAR,
	wraps= { ChemicalEquation.class },
	internal=true
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
	public ChemicalEquation cast(IScope scope, Object obj, Object param, boolean copy) throws GamaRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

}
