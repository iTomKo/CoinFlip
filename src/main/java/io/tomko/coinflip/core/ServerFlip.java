package io.tomko.coinflip.core;

import io.tomko.coinflip.CoinFlip;
import org.bukkit.entity.Player;

public record ServerFlip(Player player, int amount) {
    public boolean flip() {
        int randomNumber = CoinFlip.random.nextInt(0, 200);
        boolean wonFlip = randomNumber % 2 == 0 && System.currentTimeMillis() % 2 == 0; //Random boolean state generation

        CoinFlip.instance().getLogger().info("[CF] %s {%s} %s!".formatted(player.getName(), amount, wonFlip ? "won" : "failed"));
        return wonFlip;
    }
}
