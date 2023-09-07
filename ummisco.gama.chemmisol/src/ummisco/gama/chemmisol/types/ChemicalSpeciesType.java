package ummisco.gama.chemmisol.types;

import java.util.List;
import java.util.Map;

import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.type;
import msi.gama.precompiler.GamlAnnotations.example;
import msi.gama.precompiler.GamlAnnotations.usage;
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
@doc(value="""
		A chemical species represents an actual entity within a chemical system, \
		associated to a concentration it this system.
		""",
	comment="""
			A species is always associated to each component, but species can \
			also be declared independently when they represent species that are \
			not components: this is typically the case for species produced by \
			reactions.
			""")
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
	@doc(value = """
			chemical_species constructor.
			""",
			comment="""
					Notice that it is not possible to initialize the \
					concentration of a species, since it should be determined by \
					the equilibrium state of the system. It is however possible \
					to initialize the total concentration of components.
					""",
			usages = {
					@usage(
							value = """
									if the operand is a list of this 1, the item \
									of the list is considered as the phase of \
									the species.								
									""",
							examples = {
									@example("chemical_species H3PO4 <- chemical_component([AQUEOUS])"),
									@example("chemical_species SOPO4 <- chemical_component([MINERAL])"),
									}
					),
					@usage(
							value = """
									if the operand is a map, the phase of the \
									species is retrieved from the "phase" \
									entries.

									The phase constant variable of agents can be \
									used to easily specify the phase keyword.
									""",
							examples = {
									@example("chemical_species P <- chemical_component([phase::AQUEOUS])"),
									@example("chemical_species SOPO4 <- chemical_component([phase::MINERAL])"),
									}),
					@usage("""
							In any other case, the returned value is null.
							"""
							)
					})
	public ChemicalSpecies cast(IScope scope, Object obj, Object param, boolean copy) throws GamaRuntimeException {
		if(obj == null) return null;
		if(obj instanceof ChemicalSpecies) {
			ChemicalSpecies species = (ChemicalSpecies) obj;
			if(copy) {
				ChemicalSpecies species_copy = new ChemicalSpecies(species.getName(), species.getPhase());
				species_copy.setConcentration(species.getConcentration());
				return species_copy;
			}
			return species;
		}
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
