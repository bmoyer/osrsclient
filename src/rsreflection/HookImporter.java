/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsreflection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ben
 */
public class HookImporter {

	public HookImporter() {

	}

	public static HashMap<String, FieldInfo> readJSON(String path) {

		HashMap<String, FieldInfo> hookMap = new HashMap();
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(path));
			JSONObject jsonObject = (JSONObject) obj;
			Set keys = jsonObject.keySet();

			for (Object s : keys) {

				JSONObject cur = (JSONObject) jsonObject.get(s);
				FieldInfo f = new FieldInfo(cur.get("className").toString(), cur.get("fieldName").toString(), Integer.parseInt(cur.get("multiplier").toString()));
				hookMap.put(s.toString(), f);

			}

			return hookMap;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			Logger.getLogger(HookImporter.class.getName()).log(Level.SEVERE, null, ex);
		}

		return hookMap;
	}
}
