package tc.oc.rocket;

import java.util.Random;

import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Packet31RelEntityMove;

import org.bukkit.*;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

public class RocketUtils {
    static Random random = new Random();

    public static Firework getRandomFirework(Location loc) {
        FireworkMeta fireworkMeta = (FireworkMeta) (new ItemStack(Material.FIREWORK)).getItemMeta();
        Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);

        fireworkMeta.setPower(RocketConfig.FIREWORK_POWER);
        fireworkMeta.addEffect(FireworkEffect.builder()
                    .with(RocketUtils.randomFireworkType())
                    .withColor(RocketUtils.randomColor())
                    .trail(RocketConfig.FIREWORK_TRAIL)
                    .build());

        firework.setFireworkMeta(fireworkMeta);
        return firework;
    }

    public static FireworkEffect.Type randomFireworkType() {
        return FireworkEffect.Type.values()[random.nextInt(FireworkEffect.Type.values().length)];
    }

    public static Color randomColor() {
        int color = random.nextInt(12);

        switch(color) {
            case 0: return Color.AQUA;
            case 1: return Color.BLUE;
            case 2: return Color.FUCHSIA;
            case 3: return Color.GREEN;
            case 4: return Color.LIME;
            case 5: return Color.MAROON;
            case 6: return Color.NAVY;
            case 7: return Color.OLIVE;
            case 8: return Color.ORANGE;
            case 9: return Color.PURPLE;
            case 10: return Color.RED;
            case 11: return Color.TEAL;
            case 12: return Color.YELLOW;
        }

        return Color.BLACK;
    }

    public static void takeOff(Player observer, Location loc) {
        for(int i = 0; i < RocketConfig.SMOKE_COUNT; i++) {
            double angle = 2 * Math.PI * i / RocketConfig.SMOKE_COUNT;
            Location base = loc.clone().add(new Vector(RocketConfig.SMOKE_RADIUS * Math.cos(angle), 0, RocketConfig.SMOKE_RADIUS * Math.sin(angle)));
            for(int j = 0; j <= 8; j++) {
                observer.playEffect(base, Effect.SMOKE, j);
            }
        }
    }

    public static void fakeDelta(Player observer, Player victim, Vector delta) {
        Packet packet = new Packet31RelEntityMove(((CraftPlayer) victim).getHandle().id, (byte) (delta.getX() * 32), (byte) (delta.getY() * 32), (byte) (delta.getZ() * 32));

        sendPacket((CraftPlayer) observer, packet);
    }

    private static void sendPacket(CraftPlayer player, Packet packet) {
        player.getHandle().playerConnection.sendPacket(packet);
    }
}
