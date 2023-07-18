package ummisco.gama.chemmisol.statements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import msi.gaml.compilation.IDescriptionValidator;
import msi.gaml.descriptions.IDescription;
import msi.gaml.descriptions.IExpressionDescription;

public class ReactionValidator implements IDescriptionValidator<IDescription> {

	@Override
	public void validate(IDescription description) {
		if(!description.isIn(ChemicalSystemStatement.CHEMICAL_SYSTEM_STATEMENT)) {
			description.error("A reaction can only be specified within a chemical_system.");
		} else {
			checkReagents(description);
		}
	}
	
	void checkReagents(IDescription description) {
		if(!description.hasFacet(ReactionStatement.EQUATION)) {
			return;
		}
		String equation = description.getFacet(ReactionStatement.EQUATION).toString().replace(" ", "");
		System.out.println("Equation: " + equation);
		Pattern p = Pattern.compile("(.+)=(.+)");
		Matcher m = p.matcher(equation.toString());
		if(!m.matches()) {
			description.error("Missing products or reactives. The chemical equation must be specified as <reactives> = <products>.");
		}
	}

}
