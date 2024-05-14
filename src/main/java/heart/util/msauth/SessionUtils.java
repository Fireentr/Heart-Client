package heart.util.msauth;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import sun.awt.datatransfer.ClipboardTransferable;

import javax.json.JsonObject;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;

public class SessionUtils {

	static String recentPkce;
	
	/**
    * Sets the minecraft session to the provided argument session, you may need to set "session" to public
    *
    * @param session      Session instance from net.minecraft.util.Session
    */
	public static void setSession(Session session) {
		Minecraft.getMinecraft().session = session;
	}
	
	/**
    * Generates Proof Key for Code Exchange or a PKCE
    *
	* @return random PKCE
    */
	public static String generatePKCE() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] codeVerifierBytes = new byte[32];
		secureRandom.nextBytes(codeVerifierBytes);
		String codeVerifier = Base64.encodeBase64URLSafeString(codeVerifierBytes);

		return codeVerifier;
	}

	
	/**
	* Tries to login using browser
	* 
	* @throws IOException If an I/O error occurs during the process.
 	* @throws URISyntaxException If an URI Syntax error occurs during the process.
    */
	public static void tryLoginBrowser() throws IOException, URISyntaxException {
		recentPkce = generatePKCE();
		
		WebServer.initWebServer();
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			Desktop.getDesktop()
					.browse(new URI("https://login.live.com/oauth20_authorize.srf?"
							+ "client_id=" + Authentication.CLIENT_ID + "&prompt=select_account"
							+ "&scope=Xboxlive.signin+Xboxlive.offline_access" + "&code_challenge_method=S256"
							+ "&code_challenge=" + Base64.encodeBase64URLSafeString(DigestUtils.sha256(recentPkce))
							+ "&response_type=code" + "&redirect_uri=" + Authentication.REDIRECT_URI));
		} else {
			String myString = "https://login.live.com/oauth20_authorize.srf?"
					+ "client_id=" + Authentication.CLIENT_ID + "&prompt=select_account"
					+ "&scope=Xboxlive.signin+Xboxlive.offline_access" + "&code_challenge_method=S256"
					+ "&code_challenge=" + Base64.encodeBase64URLSafeString(DigestUtils.sha256(recentPkce))
					+ "&response_type=code" + "&redirect_uri=" + Authentication.REDIRECT_URI;
			StringSelection stringSelection = new StringSelection(myString);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}
	}

	/**
    * Called when the webserver got the response with the code.
	* 
	* @param code          the code provided from login.live.com/oauth20_authorize.srf
	* @return A status string you can put on your GUI
    */
	public static String recieveResponse(String code) {
		try {
			String accessToken = new Authentication().retrieveAccessToken(code,recentPkce);			
			JsonObject loginProfileInfo = Authentication.getAccountInfo(accessToken);
			
			String name = loginProfileInfo.getString("name");
            		String id = loginProfileInfo.getString("id");
			
			setSession(new Session(name, id, accessToken, "legacy"));
			
			return "Logged in successfully as " + name +"!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Could not log-in. Please check console!";
		}
	}
}
