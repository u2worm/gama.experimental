package ummisco.gama.chemmisol.types;

import java.util.List;
import java.util.Map;

import msi.gama.precompiler.GamlAnnotations.type;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Phase;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;

@type(
	    name = ChemicalSpeciesType.CHEMICAL_SPECIES_TYPE, 
	    id = ChemicalSpeciesType.CHEMICAL_SPECIES_TYPE_ID, wraps = { ChemicalSpecies.class }, 
	    kind = ISymbolKind.Variable.REGULAR
	    )
public class ChemicalSpeciesType extends GamaType<ChemicalSpecies> {
	public static final String CHEMICAL_PHASE = "phase";
	public static final int CHEMICAL_SPECIES_TYPE_ID = IType.AVAILABLE_TYPES+5;
	public static final String CHEMICAL_SPECIES_TYPE = "chemical_species";
	@Override
	public ChemicalSpecies getDefault() {
		return new ChemicalSpecies();
	}

	@Override
	public boolean canCastToConst() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ChemicalSpecies cast(IScope scope, Object obj, Object param, boolean copy) throws GamaRuntimeException {
		if(obj == null) return null;
		if(obj instanceof ChemicalSpecies) return (ChemicalSpecies) obj;
		if(obj instanceof Map) {
			Phase phase = (Phase) ((Map<?, ?>) obj).get(ChemicalComponentType.CHEMICAL_PHASE);
			return new ChemicalSpecies(phase);
		}
		if(obj instanceof List) {
			List<?> l = (List<?>) obj;
			if (l.size() == 1) {
				return new ChemicalSpecies((Phase) l.get(0));
			}
		}
		return null;
	}

}
