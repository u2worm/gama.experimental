package ummisco.gama.chemmisol.types;

import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.type;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;

@type(name = ChemicalSystemType.CHEMICAL_SYSTEM_TYPE,
	id= ChemicalSystemType.CHEMICAL_SYSTEM_TYPE_ID,
	kind=ISymbolKind.Variable.REGULAR,
	wraps= { ChemicalSystem.class },
	internal=true
)
@doc(value="""
		Internal chemical system type.
		""",
		comment = """
				See the documentations of the chemical_system statement and the \
				chemical architecture for more information about chemical \
				systems.
				""")
public class ChemicalSystemType extends GamaType<ChemicalSystem> {
	public static final int CHEMICAL_SYSTEM_TYPE_ID = IType.AVAILABLE_TYPES+1;
	public static final String CHEMICAL_SYSTEM_TYPE = "chemical_system";

	@Override
	public ChemicalSystem getDefault() {
		return null;
	}

	@Override
	public boolean canCastToConst() {
		return false;
	}

	@Override
	@doc(value="""
			Currently not used, since the chemical system type is currently for \
			internal use only.
			""")
	public ChemicalSystem cast(IScope scope, Object obj, Object param, boolean copy) throws GamaRuntimeException {
		return null;
	}

}
