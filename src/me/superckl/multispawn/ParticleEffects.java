package me.superckl.multispawn;

import net.minecraft.server.v1_6_R1.EntityPlayer;
import net.minecraft.server.v1_6_R1.Packet63WorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public enum ParticleEffects
{
  HUGE_EXPLOSION("hugeexplosion"), 
  LARGE_EXPLODE("largeexplode"), 
  FIREWORKS_SPARK("fireworksSpark"), 
  BUBBLE("bubble"), 
  SUSPEND("suspend"), 
  DEPTH_SUSPEND("depthSuspend"), 
  TOWN_AURA("townaura"), 
  CRIT("crit"), 
  MAGIC_CRIT("magicCrit"), 
  MOB_SPELL("mobSpell"), 
  MOB_SPELL_AMBIENT("mobSpellAmbient"), 
  SPELL("spell"), 
  INSTANT_SPELL("instantSpell"), 
  WITCH_MAGIC("witchMagic"), 
  NOTE("note"), 
  PORTAL("portal"), 
  ENCHANTMENT_TABLE("enchantmenttable"), 
  EXPLODE("explode"), 
  FLAME("flame"), 
  LAVA("lava"), 
  FOOTSTEP("footstep"), 
  SPLASH("splash"), 
  LARGE_SMOKE("largesmoke"), 
  CLOUD("cloud"), 
  RED_DUST("reddust"), 
  SNOWBALL_POOF("snowballpoof"), 
  DRIP_WATER("dripWater"), 
  DRIP_LAVA("dripLava"), 
  SNOW_SHOVEL("snowshovel"), 
  SLIME("slime"), 
  HEART("heart"), 
  ANGRY_VILLAGER("angryVillager"), 
  HAPPY_VILLAGER("happerVillager"), 
  ICONCRACK("iconcrack_"), 
  TILECRACK("tilecrack_");

  private String particleName;

  private ParticleEffects(String particleName) { this.particleName = particleName; }

  public void sendToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception
  {
    Packet63WorldParticles packet = new Packet63WorldParticles();
    ReflectionUtilities.setValue(packet, "a", this.particleName);
    ReflectionUtilities.setValue(packet, "b", Float.valueOf((float)location.getX()));
    ReflectionUtilities.setValue(packet, "c", Float.valueOf((float)location.getY()));
    ReflectionUtilities.setValue(packet, "d", Float.valueOf((float)location.getZ()));
    ReflectionUtilities.setValue(packet, "e", Float.valueOf(offsetX));
    ReflectionUtilities.setValue(packet, "f", Float.valueOf(offsetY));
    ReflectionUtilities.setValue(packet, "g", Float.valueOf(offsetZ));
    ReflectionUtilities.setValue(packet, "h", Float.valueOf(speed));
    ReflectionUtilities.setValue(packet, "i", Integer.valueOf(count));
    EntityPlayer ep = ((CraftPlayer)player).getHandle();
    ep.server.getPlayerList().sendPacketNearby(ep.locX, ep.locY, ep.locZ, 50, ep.dimension, packet);
  }
}