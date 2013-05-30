package tc.oc.rocket;

import java.util.Iterator;

import org.bukkit.util.Vector;

public class RocketTask implements Runnable{
    public RocketTask(RocketPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Iterator<Rocket> it = this.plugin.rockets.iterator();

        while(it.hasNext()) {
            Rocket rocket = it.next();

            if(rocket.allFireworksAlive()) {
                Vector center = rocket.getCenter();
                Vector delta = center.clone().subtract(rocket.getPreviousCenter());

                RocketUtils.fakeDelta(rocket.getObserver(), rocket.getVictim(), delta);
                rocket.setPreviousCenter(center);
            } else {
                rocket.getObserver().hidePlayer(rocket.getVictim());
                it.remove();
            }
        }
    }

    final RocketPlugin plugin;
}
