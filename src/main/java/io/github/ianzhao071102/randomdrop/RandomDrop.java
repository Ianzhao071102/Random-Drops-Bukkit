package io.github.ianzhao071102.randomdrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class RandomDrop extends JavaPlugin implements Listener {
    public void onEnable(){
        loadConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    @EventHandler
    public void Playerkillmobs(EntityDeathEvent evt){
        Monster MonsterEnt = (Monster) evt.getEntity();
        Player player = MonsterEnt.getKiller();
        if(player == null){
            Bukkit.getServer().getLogger().log(Level.WARNING,"RandomDrop.java at line 32(accuratily) with the error player == null!!!!");
        }else{
            setupLoot(player);
        }

    }
    @EventHandler
    public void onPlayerBreakBlockEvent(BlockBreakEvent blockbevent){
        Player player = blockbevent.getPlayer();
        setupLoot(player);
    }
    private void setupLoot(Player player) {
        List<String> configItems = this.getConfig().getStringList("Items");

        int index = new Random().nextInt(configItems.size());
        String items = configItems.get(index);

        ItemStack newItem = new ItemStack(Material.getMaterial(items.toUpperCase()));

        player.getInventory().addItem(newItem);
    }
}