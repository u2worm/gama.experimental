package ummisco.gama.chemmisol.statements;

import msi.gaml.compilation.IDescriptionValidator;
import msi.gaml.descriptions.IDescription;

public class ReactionValidator implements IDescriptionValidator<IDescription> {

	@Override
	public void validate(IDescription description) {
		if(!description.isIn(ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT)) {
			description.error("A reaction can only be specified within a chemical_system.");
		}
	}

}
