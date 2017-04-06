package uniandes.unacloud.agent.platform.VirtualBox;

/**
 * Represents VirtualBox version API 5.*
 * @author Cesar
 *
 */
public class VBox5 extends VBoxAPIVersion{

	public static final String VERSION = "5";
	

	@Override
	public String[] createExecutionCommand(String path, String imageName,String command, String username, String password) {		
		return new String[]{path, "--nologo","guestcontrol", imageName, "--username", username, "--password", password, "run", "--exe", command, "--wait-stdout", "--wait-stderr", "--", "-l"};
	}

	@Override
	public String[] createCopyToCommand(String path, String imageName,String sourcePath, String guestPath, String username,String password) {		
		return new String[]{path, "--nologo", "guestcontrol", imageName, "--username", username, "--password", password, "copyto", "--target-directory", guestPath, sourcePath};
	}

}