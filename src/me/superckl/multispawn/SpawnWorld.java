package me.superckl.multispawn;

import java.util.EnumSet;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class SpawnWorld implements Listener{

	private World spawnWorld;
	private WorldCreator creator;
        private EnumSet<Material> blocked = EnumSet.of(Material.ANVIL,
                                             Material.BEACON,
                                             Material.BED,
                                             Material.BOAT,
                                             Material.BREWING_STAND,
                                             Material.BUCKET,
                                             Material.BURNING_FURNACE,
                                             Material.CAKE,
                                             Material.CARROT_ITEM,
                                             Material.CAULDRON,
                                             Material.CHEST,
                                             Material.COMMAND,
                                             Material.CROPS,
                                             Material.DIODE_BLOCK_OFF,
                                             Material.DIODE_BLOCK_ON,
                                             Material.DISPENSER,
                                             Material.DRAGON_EGG,
                                             Material.DROPPER,
                                             Material.EGG,
                                             Material.EXPLOSIVE_MINECART,
                                             Material.FIRE,
                                             Material.FIREBALL,
                                             Material.FLINT_AND_STEEL,
                                             Material.FLOWER_POT,
                                             Material.FURNACE,
                                             Material.GLASS_BOTTLE,
                                             Material.GOLD_RECORD,
                                             Material.GREEN_RECORD,
                                             Material.HOPPER,
                                             Material.HOPPER_MINECART,
                                             Material.ICE,
                                             Material.INK_SACK,
                                             Material.ITEM_FRAME,
                                             Material.JUKEBOX,
                                             Material.LAVA,
                                             Material.LAVA_BUCKET,
                                             Material.LEVER,
                                             Material.LOCKED_CHEST,
                                             Material.MELON_SEEDS,
                                             Material.MILK_BUCKET,
                                             Material.MINECART,
                                             Material.MONSTER_EGG,
                                             Material.MONSTER_EGGS,
                                             Material.NETHER_WARTS,
                                             Material.NOTE_BLOCK,
                                             Material.PAINTING,
                                             Material.POTATO_ITEM,
                                             Material.POTION,
                                             Material.POWERED_MINECART,
                                             Material.PUMPKIN_SEEDS,
                                             Material.RECORD_10,
                                             Material.RECORD_11,
                                             Material.RECORD_12,
                                             Material.RECORD_3,
                                             Material.RECORD_4,
                                             Material.RECORD_5,
                                             Material.RECORD_6,
                                             Material.RECORD_7,
                                             Material.RECORD_8,
                                             Material.RECORD_9,
                                             Material.REDSTONE_COMPARATOR_OFF,
                                             Material.REDSTONE_COMPARATOR_ON,
                                             Material.SKULL_ITEM,
                                             Material.STATIONARY_LAVA,
                                             Material.STATIONARY_WATER,
                                             Material.STORAGE_MINECART,
                                             Material.TRAPPED_CHEST,
                                             Material.TRAP_DOOR,
                                             Material.WATER,
                                             Material.WATER_BUCKET);
	
	public SpawnWorld(){
		this.creator = WorldCreator.name("Spawn");
		this.prepareCreator();
		this.spawnWorld = this.creator.createWorld();
		this.enforceSpawn();
	}
	
	private void prepareCreator(){
		this.creator.environment(Environment.NORMAL);
		this.creator.generateStructures(false);
		this.creator.type(WorldType.FLAT);
	}
	protected void enforceSpawn(){
		this.spawnWorld.setPVP(false);
		this.spawnWorld.setDifficulty(Difficulty.PEACEFUL);
	}
        
        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onWorldChanged(PlayerChangedWorldEvent e) {
                if(!e.getPlayer().getWorld().getName().equalsIgnoreCase("spawn"))
                        if(!e.getPlayer().hasPermission("essentials.fly") && !e.getPlayer().hasPermission("essentials.gamemode")){
                                e.getPlayer().setAllowFlight(false);
                                e.getPlayer().setFlying(false);
                        }
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void onPlayerInteract(PlayerInteractEvent e){
                if(!e.getPlayer().getWorld().getName().equalsIgnoreCase("spawn")) return;
                if(e.getPlayer().isOp()) return;
                if(this.blocked.contains(e.getMaterial())){
                        e.setCancelled(true);
                        return;
                }
                if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                        if(this.blocked.contains(e.getClickedBlock().getType())){
                                e.setCancelled(true);
                        }
                }
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void onPlayerInteractEntity(PlayerInteractEntityEvent e){
                if(!e.getPlayer().getWorld().getName().equalsIgnoreCase("spawn")) return;
                if(e.getPlayer().isOp()) return;
                if(e.getRightClicked() instanceof Hanging){
                        e.setCancelled(true);
                }
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void onHangingBreakByEntity(HangingBreakByEntityEvent e){
                if(!e.getEntity().getWorld().getName().equalsIgnoreCase("spawn")) return;
                if(e.getRemover() instanceof Player){
                        if(((Player)e.getRemover()).isOp()){
                            return;
                        }
                        e.setCancelled(true);
                }
        }
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerDamage(EntityDamageEvent e){
		if(e.getEntityType() == EntityType.PLAYER && e.getEntity().getWorld().getName().equalsIgnoreCase("spawn")){
			e.setCancelled(true);
			if(e.getCause() == DamageCause.VOID)
				e.getEntity().teleport(this.spawnWorld.getSpawnLocation());
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerHunger(FoodLevelChangeEvent e){
		if(e.getEntity().getWorld().getName().equalsIgnoreCase("spawn")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent e){
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("spawn") && !e.getPlayer().isOp()) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent e){
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("spawn") && !e.getPlayer().isOp()) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockIgnite(BlockIgniteEvent e){
		if(e.getBlock().getWorld().getName().equalsIgnoreCase("spawn")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onExplode(EntityExplodeEvent e){
		if(e.getLocation().getWorld().getName().equalsIgnoreCase("spawn")) if(e.blockList() != null) e.blockList().clear();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWeatherChange(WeatherChangeEvent e){
		if(e.getWorld().getName().equalsIgnoreCase("spawn") && e.toWeatherState()) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerEmptyBucket(PlayerBucketEmptyEvent e){
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("spawn") && !e.getPlayer().isOp()) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerFillBucket(PlayerBucketFillEvent e){
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("spawn") && !e.getPlayer().isOp()) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerSleep(PlayerBedEnterEvent e){
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("spawn") && !e.getPlayer().isOp()) e.setCancelled(true);
	}
        
	/**
	 * Credit to DancingWalrus & contributors for double jumping
	 */
	  @EventHandler
	  public void onMove(PlayerMoveEvent event)
	  {
	    if ((event.getPlayer().getGameMode() != GameMode.CREATIVE) && (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) && (event.getTo().getWorld().getName().equalsIgnoreCase("spawn")))
	    {
	      event.getPlayer().setAllowFlight(true);
	    }
	  }

	  @EventHandler
	  public void onFly(PlayerToggleFlightEvent event)
	  {
	    Player player = event.getPlayer();
	    if ((player.getGameMode() != GameMode.CREATIVE) && (player.getLocation().getWorld().getName().equalsIgnoreCase("spawn"))) {
	      event.setCancelled(true);
	      player.setAllowFlight(false);
	      player.setFlying(false);
	      player.setVelocity(player.getLocation().getDirection().multiply(1.6D).setY(1.0D));
	      player.getLocation().getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0F, -5.0F);
	        for (Player p : Bukkit.getOnlinePlayers())
	            try {
	              ParticleEffects.CLOUD.sendToPlayer(p, player.getLocation(), 1.0F, 1.0F, 1.0F, 1.0F, 20);
	            } catch (Exception e) {
	              e.printStackTrace();
	            }
	    }
	}
}
