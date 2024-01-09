package br.com.centralandradina;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;




/**
 * Helper to translate based on Minecraft translate file
 * 
 * idea from https://github.com/LOOHP/InteractiveChat/blob/6a5d9a60df31fe4f24ee36880c65c1855725f8f5/src/main/java/com/loohp/interactivechat/utils/LanguageUtils.java#L66
 */
public class LanguageUtils
{

	protected String language;
	protected JavaPlugin plugin;
	private static final Map<String, Map<String, String>> translations = new HashMap<>();

	public LanguageUtils(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}

	public void loadTranslations(String language)
	{
		this.language = language;
		String languageFilePath = this.plugin.getDataFolder() + "/languages/" + language + ".json";
		String mcVersion = "1.20.2";
		String languageUrl = "https://raw.githubusercontent.com/InventivetalentDev/minecraft-assets/" + mcVersion + "/assets/minecraft/lang/" + language + ".json";

		// verify if language file exists
		File file = new File(languageFilePath);
		if(!file.exists()) {
			plugin.getLogger().warning("language not found, trying to download");

			// verify if directory exists
			File dir = new File(this.plugin.getDataFolder() + "/languages/");
			if(!dir.exists()) {
				dir.mkdirs();
			}

			// if not, download from 
			try (BufferedInputStream in = new BufferedInputStream(new URL(languageUrl).openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(languageFilePath)) {
				byte dataBuffer[] = new byte[1024];
				int bytesRead;
				while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
					fileOutputStream.write(dataBuffer, 0, bytesRead);
				}

				file = new File(languageFilePath);

				plugin.getLogger().warning("language downloaded");
			}
			catch (IOException e) {
				plugin.getLogger().severe("02: " + e.getMessage());
			}
		}

		// 
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			JSONObject json = (JSONObject) new JSONParser().parse(reader);
			reader.close();

			Map<String, String> mapping = new HashMap<>();
			for (Object obj : json.keySet()) {
				try {
					String key = (String) obj;
					mapping.put(key, (String) json.get(key));
				} 
				catch (Exception e) {
				}
			}
			translations.put(language, mapping);
			
		}
		catch (Exception e) {
			plugin.getLogger().severe("Cannot load file " + languageFilePath);
			plugin.getLogger().severe("Extract assets/minecraft/lang/" + language + ".json from your minecraft client .jar, or download from https://mcasset.cloud/" + mcVersion + "/assets/minecraft/lang");
			e.printStackTrace();
		}

		
	}

	public String getTranslation(String translationKey) {
		try {
			Map<String, String> mapping = translations.get(language);
			if(mapping == null) {
				return getTranslation(translationKey);
			}
			else {
				return mapping.getOrDefault(translationKey, translationKey);
			}

		} catch (Exception e) {
			return translationKey;
		}
	}	
}
