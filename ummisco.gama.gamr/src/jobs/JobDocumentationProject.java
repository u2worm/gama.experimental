package jobs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import msi.gama.precompiler.GamlAnnotations.file;
import msi.gama.runtime.GAMA;
import ummisco.gama.ui.navigator.contents.WrappedResource;
import ummisco.gama.ui.utils.SwtGui;

/**
 * 
 * @author damienphilippon Date : 19 Dec 2017 Class used to do a Documentation
 *         job for a Project (will defined an Index of all the species and
 *         experiments of the projects, and do the documentation of all the
 *         models contained in the project)
 */
public class JobDocumentationProject extends JobDocumentation {

	/**
	 * The project concerned by this job
	 */
	public IProject project;

	/**
	 * Constructor using a given project (will be saved in a default documentation
	 * folder )
	 * 
	 * @param project
	 *            {@code IProject} the project to document
	 */
	public JobDocumentationProject(IProject project) {
		super(project.getLocation().toOSString());
		this.project = project;
	}

	/**
	 * Constructor using a given project (will be saved in a given documentation
	 * folder )
	 * 
	 * @param project
	 *            {@code IProject} the project to document
	 * @param path
	 *            {@code String} the path (String) in which the documentation will
	 *            be saved
	 */
	public JobDocumentationProject(IProject project, String path) {
		super(path);
		this.project = project;
	}

	public void pack(String sourceDirPath, String zipFilePath) throws IOException {
		System.out.println(System.getProperty("user.dir"));
		Files.deleteIfExists(Paths.get(zipFilePath));
		Path p = Files.createFile(Paths.get(zipFilePath));
		try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
			Path pp = Paths.get(sourceDirPath);
			Files.walk(pp).filter(path -> !Files.isDirectory(path)).forEach(path -> {
				ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
				try {
					zs.putNextEntry(zipEntry);
					Files.copy(path, zs);
					zs.closeEntry();
				} catch (IOException e) {
					System.err.println(e);
				}
			});
		}
	}

	/**
	 * Method to run the job in background
	 */
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
		if (project != null) {
			// WrappedResource file_to_convert =
			// ummisco.gama.ui.navigator.contents.ResourceManager.getInstance().findWrappedInstanceOf(project);
			// Object[] childrenResources = file_to_convert.getNavigatorChildren().clone();
			try {
				pack(project.getLocation().toOSString(), directory + project.getName() + ".zip");
				GAMA.getGui().tell("Zip created!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// this.dispose();
		return Status.OK_STATUS;
	}
}
