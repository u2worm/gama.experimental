package ummisco.gama.chemmisol;

import java.io.IOException;

public class ChemmisolLoader {
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
		return resource_path + "/";
	}
	
	public static void loadChemmisol() {
		String os_resource_path = resourcePath();
		try {
			Chemmisol.loadChemmisolLibrariesFromResource(ChemmisolLoader.class, os_resource_path);
		} catch(IOException e) {
			System.err.println(e);
			System.err.println("Cannot load native chemmisol library from "
					+ os_resource_path + System.mapLibraryName("chemmisol-java"));
		}
	}
}
