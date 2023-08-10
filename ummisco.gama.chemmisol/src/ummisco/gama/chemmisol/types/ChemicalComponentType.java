package ummisco.gama.chemmisol.types;

import java.util.List;
import java.util.Map;

import msi.gama.precompiler.ISymbolKind;
import msi.gama.precompiler.GamlAnnotations.type;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.constants.GamlCoreUnits;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Phase;
import ummisco.gama.chemmisol.units.ChemicalUnits;

@type(
	    name = ChemicalComponentType.CHEMICAL_COMPONENT_TYPE, 
	    id = ChemicalComponentType.CHEMICAL_COMPONENT_TYPE_ID, wraps = { ChemicalComponent.class }, 
	    kind = ISymbolKind.Variable.REGULAR
	    )
public class ChemicalComponentType extends GamaType<ChemicalComponent> {
	public static final String CHEMICAL_CONCENTRATION = "concentration";
	public static final String CHEMICAL_PHASE = "phase";
	public static final int CHEMICAL_COMPONENT_TYPE_ID = IType.AVAILABLE_TYPES+3;
	public static final String CHEMICAL_COMPONENT_TYPE = "chemical_component";
	
	// Base unit used by the chemmisol library (0.1mol/l specified in GAML must correspond to concentration=0.1 in chemmisol)
	private static final double base_concentration_unit = ChemicalUnits.mol / GamlCoreUnits.l;
	
	@Override
	public ChemicalComponent getDefault() {
		return new ChemicalComponent();
	}

	@Override
	public boolean canCastToConst() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ChemicalComponent cast(IScope scope, Object obj, Object param, boolean copy) throws GamaRuntimeException {
		if(obj == null) return null;
		if(obj instanceof ChemicalComponent) return (ChemicalComponent) obj;
		if(obj instanceof Number) {
			return new ChemicalComponent(((Number) obj).doubleValue() / base_concentration_unit);
		}
		if(obj instanceof Map) {
			Double concentration = (Double) ((Map<?, ?>) obj).get(ChemicalComponentType.CHEMICAL_CONCENTRATION);
			Phase phase = (Phase) ((Map<?, ?>) obj).get(ChemicalComponentType.CHEMICAL_PHASE);
			if(concentration == null && phase == null) {
				return new ChemicalComponent();
			}
			if(concentration == null)
				return new ChemicalComponent(phase);
			if(phase == null)
				return new ChemicalComponent(concentration / base_concentration_unit);
			return new ChemicalComponent(phase, concentration / base_concentration_unit);
		}
		if(obj instanceof List) {
			List<?> l = (List<?>) obj;
			if (l.size() == 1) {
				return new ChemicalComponent((Phase) l.get(0));
			} else if(l.size() == 2) {
				return new ChemicalComponent((Phase) l.get(0), ((double) l.get(1)) / base_concentration_unit);
			}
		}
		return null;
	}

}
