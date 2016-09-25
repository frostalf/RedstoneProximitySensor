package com.ugleh.redstoneproximitysensor;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.ugleh.redstoneproximitysensor.commands.CommandRPS;
import com.ugleh.redstoneproximitysensor.listeners.PlayerListener;
import com.ugleh.redstoneproximitysensor.listeners.SensorListener;
import com.ugleh.redstoneproximitysensor.utils.GeneralConfig;
import com.ugleh.redstoneproximitysensor.utils.Glow;
import com.ugleh.redstoneproximitysensor.utils.SConfig;

public class RedstoneProximitySensor extends JavaPlugin{

	public GeneralConfig gConfig;
	public SConfig sConfig;
	public ItemStack rps;
	public ShapedRecipe rpsRecipe;
	public String chatPrefix = ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE + "RPS" + ChatColor.DARK_PURPLE + "] " + ChatColor.RED ;

	@Override
	public void onEnable()
	{
		//Init configs.
		gConfig = new GeneralConfig(this);
		sConfig = new SConfig(this, "sensors.yml", "sensors.yml");
		
		//Setup Glow
		registerGlow();
		
		//Setting command Executors.
		this.getServer().getPluginCommand("rps").setExecutor(new CommandRPS(this));
		
		//Init Listeners
		this.getServer().getPluginManager().registerEvents(new SensorListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		//Others
		createRecipes();
	}
	
	private void createRecipes() {
		rps = new ItemStack(Material.REDSTONE_TORCH_ON, 1);
		ItemMeta rpsMeta = rps.getItemMeta();
		rpsMeta.setDisplayName(ChatColor.RED + "Redstone Proximity Sensor");
		Glow glow = new Glow(1234);
		rpsMeta.addEnchant(glow, 1, true);
		rps.setItemMeta(rpsMeta);
		 
		rpsRecipe = new ShapedRecipe(rps);
		rpsRecipe.shape("-R-","-R-","-R-");
		rpsRecipe.setIngredient('R', Material.REDSTONE_TORCH_ON);
		this.getServer().addRecipe(rpsRecipe);
		
	}
	
	
	@Override
	public void onDisable()
	{
		
	}
	
	
	public GeneralConfig getgConfig() {
		return gConfig;
	}

	public SConfig getSensorConfig() {
		return sConfig;
	}
	
	
	public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(70);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
 
}