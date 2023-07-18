package ummisco.gama.chemmisol.types;

import java.util.regex.Pattern;

import msi.gama.precompiler.GamlAnnotations.type;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Phase;

@type(
	    name = ChemicalPhaseType.CHEMICAL_PHASE_TYPE, 
	    id = ChemicalPhaseType.CHEMICAL_PHASE_TYPE_ID, wraps = { Phase.class }, 
	    kind = ISymbolKind.Variable.REGULAR
	    )
public class ChemicalPhaseType extends GamaType<Phase> {
	public static final String CHEMICAL_PHASE_TYPE = "chemical_phase";
	public static final int CHEMICAL_PHASE_TYPE_ID = IType.AVAILABLE_TYPES+2;
	
	private static final Pattern aqueous_pattern = Pattern.compile("aqueous", Pattern.CASE_INSENSITIVE);
	private static final Pattern mineral_pattern = Pattern.compile("mineral", Pattern.CASE_INSENSITIVE);
	private static final Pattern solvent_pattern = Pattern.compile("solvent", Pattern.CASE_INSENSITIVE);

	@Override
	public Phase getDefault() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canCastToConst() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Phase cast(IScope scope, Object obj, Object param, boolean copy) throws GamaRuntimeException {
		if(obj instanceof Phase) {
			return (Phase) obj;
		}
		if(obj instanceof String) {
			if(aqueous_pattern.matcher((String) obj).matches()) {
				return Phase.AQUEOUS;
			}
			if (mineral_pattern.matcher((String) obj).matches()) {
				return Phase.MINERAL;
			}
			if (solvent_pattern.matcher((String) obj).matches()) {
				return Phase.SOLVENT;
			} 
		}
		return null;
	}

	@Override
	public boolean isAssignableFrom(final IType<?> t) {
		return super.isAssignableFrom(t) || t.id() == IType.STRING;
	}
}
