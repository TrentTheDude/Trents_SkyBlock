package me.trent.skyblock.filemanager;

import me.trent.skyblock.SkyBlock;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CustomFile {

    private File file;
    private YamlConfiguration fileConfig;
    private HashMap<String, Object> cachedObjects = new HashMap<>();

    public enum dataTypes{
        STRING, INT, DOUBLE, STRINGLIST, BOOLEAN, MAP
    }

    public CustomFile(File file){
        this.file = file;
        this.fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void setup(boolean loadFromProject, String inFolder){
        if (!getFile().exists()){
            if (loadFromProject){
                if (!inFolder.equalsIgnoreCase("")){
                    SkyBlock.getInstance().saveResource(inFolder+"/"+file.getName(), false);
                }else{
                    SkyBlock.getInstance().saveResource(file.getName(), false);
                }
            }else{
                try{
                    getFile().createNewFile();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        loadFile();
    }

    public void loadFile() {
        this.fileConfig = YamlConfiguration.loadConfiguration(file);
        this.cachedObjects.clear(); // remove cached objects
    }

    public void saveFile(){
        try{
            getConfig().save(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String fetchString(String key){
        return (String) getObj(key, dataTypes.STRING);
    }

    public int fetchInt(String key){
        return (int) getObj(key, dataTypes.INT);
    }

    public double fetchDouble(String key) {
        return (double) getObj(key, dataTypes.DOUBLE);
    }

    public List<String> fetchStringList(String key){
        return (List<String>) getObj(key, dataTypes.STRINGLIST);
    }

    public boolean fetchBoolean(String key){
        return (boolean) getObj(key, dataTypes.BOOLEAN);
    }


    public Object getObj(String key, Enum data){
        //check for cache first

        if (getCachedObjects().containsKey(key)){
            return getCachedObjects().get(key);
        }

        if (data.equals(dataTypes.STRING)){
            String d = getConfig().getString(key);
            this.cachedObjects.put(key, d);
            return d;
        }

        if (data.equals(dataTypes.DOUBLE)){
            double d = getConfig().getDouble(key);
            this.cachedObjects.put(key, d);
            return d;
        }

        if (data.equals(dataTypes.INT)){
            int d = getConfig().getInt(key);
            this.cachedObjects.put(key, d);
            return d;
        }

        if (data.equals(dataTypes.BOOLEAN)){
            boolean d = getConfig().getBoolean(key);
            this.cachedObjects.put(key, d);
            return d;
        }

        if (data.equals(dataTypes.STRINGLIST)){
            List<String> d = getConfig().getStringList(key);
            this.cachedObjects.put(key, d);
            return d;
        }
        return null;
    }

    public HashMap<String, Object> getCachedObjects() {
        return cachedObjects;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFileConfig(YamlConfiguration fileConfig) {
        this.fileConfig = fileConfig;
    }

    public YamlConfiguration getConfig() {
        return fileConfig;
    }
}
