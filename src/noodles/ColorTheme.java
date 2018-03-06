//Aaron Griffis

package noodles;
import java.awt.Color;
import java.util.*;

public class ColorTheme{
   private static final Map<String, Color[]> THEMES = initThemes();
   
   private static Map<String, Color[]> initThemes(){
      Map<String, Color[]> themes = new LinkedHashMap<>();
      themes.put("Brick Red", initTheme(new Color(92, 47, 35)));
      themes.put("Copper Orange", initTheme(new Color(112, 64, 16)));
      themes.put("Forest Green", initTheme(new Color(50, 80, 20)));
      themes.put("Sea Blue", initTheme(new Color(15, 60, 100)));
      themes.put("Lavender Purple", initTheme(new Color(70, 40, 100)));
      themes.put("Stone Gray", initTheme(new Color(67, 64, 58)));
      themes.put("Stranded", initTheme(new Color(20,110,140), new Color(180, 150, 100), new Color(220,192,146)));
      themes.put("Volcano", initTheme(new Color(55, 40, 40), new Color(250, 100, 10), new Color(150, 40, 20)));
      return Collections.unmodifiableMap(themes);
   }//end initThemes
   
   //get methods
   public static String[] getThemeNames(){
      Set<String> keys = THEMES.keySet();
      String[] sortedKeys = new String[keys.size()];
      sortedKeys = keys.toArray(sortedKeys);
      return sortedKeys;
   }
   public static Color[] getTheme(String theme){return THEMES.get(theme);}
   
   private static Color[] initTheme(Color color){
      Color[] colors = new Color[3];
      float[] hsbVals = new float[3];
      Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbVals);
      colors[0] = color;
      colors[1] = Color.getHSBColor(hsbVals[0], Math.max(hsbVals[1] - 0.2f, 0.0f), Math.min(hsbVals[2] + 0.4f, 1.0f));
      colors[2] = Color.getHSBColor(hsbVals[0], Math.max(hsbVals[1] - 0.1f, 0.0f), Math.min(hsbVals[2] + 0.2f, 1.0f));
      return colors;
   }//end initTheme (single color)
   
   private static Color[] initTheme(Color powerColor, Color noodleColor, Color baseColor){
      Color[] colors = new Color[3];
      colors[0] = powerColor;
      colors[1] = noodleColor;
      colors[2] = baseColor;
      return colors;
   }//end initTheme (multiple colors)
}//end ColorTheme