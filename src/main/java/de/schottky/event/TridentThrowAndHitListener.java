package de.schottky.event;

import com.github.schottky.zener.api.Zener;
import com.github.schottky.zener.util.item.ItemStorage;
import de.schottky.core.UpgradableRangedWeapon;
import de.schottky.util.AttributeUtil;
import de.schottky.util.Timers;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.persistence.PersistentDataType.DOUBLE;

public class TridentThrowAndHitListener implements Listener {

    private static final String HAS_DAMAGE_ATTRIBUTE_KEY = "additionalDamage";

    /*
    The Real handling of adding damage to trident is handled at ArrowShootAndHitListener
     */
    @EventHandler
    public void onTridentShoot(@NotNull ProjectileLaunchEvent event) {
        if(event.getEntity().getType() != EntityType.TRIDENT) return;
        final var stack = ((Trident)event.getEntity()).getItem();
        final var projectile = event.getEntity();
        if (stack == null) return;
        final var meta = stack.getItemMeta();
        AttributeUtil.getAttribute(meta, Attribute.GENERIC_ATTACK_DAMAGE)
                .ifPresent(damageModifier -> {
                    final var container = projectile.getPersistentDataContainer();
                    container.set(Zener.key(HAS_DAMAGE_ATTRIBUTE_KEY), DOUBLE, damageModifier);
                    Bukkit.getLogger().info(""+damageModifier);
                });
    }
}
