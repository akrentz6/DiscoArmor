package me.scruffyboy13.DiscoArmor.nms;

import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import net.minecraft.server.v1_13_R2.EntityLiving;

public class v1_13_R2 implements NMS {

	@Override
	public void damage(EntityDamageEvent event) {
		Player p = (Player) event.getEntity();
		double dmg = event.getDamage();
		double health = ((Damageable) event.getEntity()).getHealth();
		double newHealth = health - dmg;
		EntityLiving el = ((CraftPlayer) p).getHandle();
		double ah = el.getAbsorptionHearts();
		event.setDamage(0.0);
		if (ah - dmg > 0.0) {
			el.setAbsorptionHearts((float) (ah - dmg));
			return;
		}
		if (ah - dmg <= 0.0 && ah != 0) {
			el.setAbsorptionHearts((float) 0);
			newHealth += dmg - ah;
		}
		if (newHealth <= 0)
			event.setDamage(p.getHealth() + 1.0);
		else
			((Damageable) event.getEntity()).setHealth(newHealth);
	}
	
}
