package ummisco.gama.chemmisol.skills;

import ummisco.gama.Chemmisol;
import msi.gama.precompiler.GamlAnnotations.skill;

import java.io.IOException;

import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gaml.skills.Skill;
import ummisco.gama.chemmisol.ChemicalSystem;

@skill(name=IChemicalSkill.SKILL_NAME, doc= @doc("Enables species to simulate chemical systems"))
public class ChemicalSkill extends Skill {
	
	private static String resourcePath() {
		String os = System.getProperty("os.name").toLowerCase();
		String arch = System.getProperty("os.arch").toLowerCase();
		String resource_path = new String();
		
		if(os.matches(".*linux.*")) {
			resource_path = resource_path + "linux";
		} else if (os.matches(".*win.*")) {
			resource_path = resource_path + "windows";
		} else if (os.matches(".*mac.*")) {
			// resource_path = resource_path + "mac";
			throw new RuntimeException("Cannot load native chemmisol-cpp library for unsuported OS: " + os);
		} else {
			throw new RuntimeException("Cannot load native chemmisol-cpp library for unsuported OS: " + os);
		}
		
		resource_path = resource_path + "-";
		
		if(arch.matches("amd64")) {
			resource_path = resource_path + "x86_64";
		} else if(arch.matches("arm")) {
			// resource_path = resource_path + "arm_32";
			throw new RuntimeException("Cannot load native chemmisol-cpp library for unsuported architecture: " + arch);
		} else if(arch.matches("aarch64")) {
			// resource_path = resource_path + "arm_64";
			throw new RuntimeException("Cannot load native chemmisol-cpp library for unsuported architecture: " + arch);
		} else {
			throw new RuntimeException("Cannot load native chemmisol-cpp library for unsuported architecture: " + arch);
		}
		System.out.println(resource_path);
		return resource_path + "/";
	}
	
	static {
		String os_resource_path = resourcePath();
		try {
			System.out.println("Trying to load " + os_resource_path);
			Chemmisol.loadLibraryFromResource(ChemicalSkill.class, os_resource_path);
			System.out.println("Done.");
		} catch(IOException e) {
			System.err.println(e);
			System.err.println("Cannot load native chemmisol library from "
					+ os_resource_path + System.mapLibraryName("chemmisol-java"));
		}
	}

	private ChemicalSystem chemical_system;

	public ChemicalSkill() {
		System.out.println("Try to init ChemSys");
		this.chemical_system = new ChemicalSystem();
		System.out.println("done");
	}
}
