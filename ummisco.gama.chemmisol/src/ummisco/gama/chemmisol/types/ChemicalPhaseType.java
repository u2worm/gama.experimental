package ummisco.gama.chemmisol.types;

import java.util.regex.Pattern;

import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.type;
import msi.gama.precompiler.GamlAnnotations.usage;
import msi.gama.precompiler.GamlAnnotations.example;
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
@doc("""
		Represents the different phases of a chemical species.
		"""
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
	@doc(value = "Constructs a chemical_phase.",
			comment = """
					The predefined AQUEOUS, MINERAL and SOLVENT constants \
					defined in agents with the chemical skill can be used \
					instead. The string operand match is case insensitive.
					""",
			usages= {
					@usage(value="""
							if the operand matches "aqueous", returns AQUEOUS.
							""",
							examples= {
									@example("""
											chemical_phase phase <- chemical_phase("AQUEOUS")
											"""),
									@example("""
											chemical_phase phase <- chemical_phase("aqueous")
											"""),
									@example("""
											// using the predefined AQUEOUS variable in an agent with the
											// chemical skill
											chemical_component P <- chemica_phase([phase::AQUEOUS])
											"""),
							}
					),
					@usage(value="""
							if the operand matches "mineral", returns MINERAL.
							""",
							examples= {
									@example("""
											chemical_phase phase <- chemical_phase("MINERAL")
											"""),
									@example("""
											chemical_phase phase <- chemical_phase("mineral")
											"""),
									@example("""
											// using the predefined MINERAL variable in an agent with the
											// chemical skill
											chemical_component SOH <- chemica_phase([phase::MINERAL])
											"""),
							}
					),
					@usage(value="""
							if the operand matches "solvent", returns SOLVENT.
							""",
							examples= {
									@example("""
											chemical_phase phase <- chemical_phase("SOLVENT")
											"""),
									@example("""
											chemical_phase phase <- chemical_phase("solvent")
											"""),
									@example("""
											// using the predefined SOLVENT variable in an agent with the
											// chemical skill
											chemical_component H2O <- chemica_phase([phase::SOLVENT])
											"""),
							}
					)
			})
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
