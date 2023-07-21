package ummisco.gama.chemmisol.units;

import msi.gaml.constants.IConstantAcceptor;
import msi.gaml.constants.IConstantsSupplier;

public class ChemicalConstantSupplier implements IConstantsSupplier {

	@Override
	public void supplyConstantsTo(final IConstantAcceptor acceptor) {
		browse(ChemicalConstants.class, acceptor);
		browse(ChemicalUnits.class, acceptor);
	}

}
