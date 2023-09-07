package ummisco.gama.chemmisol.types;

import java.util.List;
import java.util.Map;

import msi.gama.precompiler.ISymbolKind;
import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.example;
import msi.gama.precompiler.GamlAnnotations.type;
import msi.gama.precompiler.GamlAnnotations.usage;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.constants.GamlCoreUnits;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;
import ummisco.gama.chemmisol.Phase;
import ummisco.gama.chemmisol.architecture.ChemicalArchitecture;
import ummisco.gama.chemmisol.units.ChemicalUnits;

@type(
	    name = ChemicalComponentType.CHEMICAL_COMPONENT_TYPE, 
	    id = ChemicalComponentType.CHEMICAL_COMPONENT_TYPE_ID, wraps = { ChemicalComponent.class }, 
	    kind = ISymbolKind.Variable.REGULAR
	    )
@doc(value="""
		Chemical components are the building blocks of chemical reactions.
		
		Reactions are specified so that complex chemical species are formed from \
		canonical species associated to components. The total concentration of \
		the chemical component is then the sum of the concentration of its \
		associated species and the concentrations of all complex species formed \
		from this component.
		The name of the component is set to the name of the variable used to declare it.
		""",
		examples={@example("""
				chemical_system "test" {
					// Components
					chemical_component H;
					chemical_component PO4 <- chemical_component(6.7e-6 #mol/#l)
					// Solvent, also defined as a component
					chemical_component H2O <- chemical_component([phase::SOLVENT])
					
					// Species produced by reactions Their concentrations are
					// automatically deduced when the equilibrium is solved,
					// from the equilibrium constants and total concentrations
					// of components.
					chemical_species OH;
					chemical_species H3PO4;
					
					// Reactions
					// Notice that each reaction involved exactly one species
					// that is not a chemical component.
					reaction "OH"    chemical_equation: H2O = H + OH      log_k: -14;
					reaction "H3PO4" chemical_equation: 3*H + PO4 = H3PO4 log_k: 21.721
				}
				""")})
public class ChemicalComponentType extends GamaType<ChemicalComponent> {
	public static final String CHEMICAL_CONCENTRATION = ChemicalArchitecture.TOTAL_CONCENTRATION;
	public static final String CHEMICAL_PHASE = ChemicalArchitecture.PHASE;
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
	@doc(value = """
			chemical_component constructor.
			""", usages = {
					@usage(
							value = """
									if the operand is a number, this number is \
									used as the total concentration of the \
									component and the phase of the component is \
									assumed to be aqueous.
									""",
							examples = { @example("chemical_component P <- chemical_component(6.7e-6#mol/#l)") }),
					@usage(
							value = """
									if the operand is a list of this 2, the
									first item must correspond to the phase of
									the species and the second to the total
									concentration of the component.
									""",
							examples = {
									@example("chemical_component P <- chemical_component([AQUEOUS, 6.7e-6#mol/#l])"),
									@example("chemical_component SOH <- chemical_component([MINERAL, 0.8])"),
									}
					),
					@usage(
							value = """
									if the operand is a map, the phase and total \
									concentrations are retrieved from the \
									"phase" and "total_concentration" entries.
									If the phase is not specified, AQUEOUS is assumed.
									If the total_concentration is not specified, \
									a total concentration of 0 is assumed. \
									Notice that a total concentration of 0 does \
									not have much sense, except if the component \
									is a SOLVENT.
									""",
							examples = {
									@example("chemical_component P <- chemical_component([phase::AQUEOUS, total_concentration::6.7e-6#mol/#l])"),
									@example("chemical_component SOH <- chemical_component([phase::MINERAL, total_concentration::0.8])"),
									@example("chemical_component H2O <- chemical_component([phase::SOLVENT])"),
									}),
					@usage("""
							In any other case, the returned value is null.
							"""
							)
					})
	public ChemicalComponent cast(IScope scope, Object obj, Object param, boolean copy) throws GamaRuntimeException {
		if(obj == null) return null;
		if(obj instanceof ChemicalComponent) {
			ChemicalComponent component = (ChemicalComponent) obj;
			if(copy) {
				return new ChemicalComponent(component.getName(), component.getPhase(), component.getTotalConcentration());
			}
			return component;
		}
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
			if(l.size() == 2) {
				return new ChemicalComponent((Phase) l.get(0), ((double) l.get(1)) / base_concentration_unit);
			}
		}
		return null;
	}

}
