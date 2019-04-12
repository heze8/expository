package GameEvents;

import java.util.concurrent.ThreadLocalRandom;

import ExpositoryConstant.ExpositoryConstant;

public class RandomDustEvents implements ExpositoryConstant {
	
	public static void rngLoot(LootSource source) {
		switch (source) {
		case EXPLORATION:
			int loot = ThreadLocalRandom.current().nextInt(1, 101);
			if (loot < 50) {
				
			}
			break;
		case ROBBING:
			break;
		case SCAVENGE:
			break;
		case WINNING_FIGHTS:
			break;
		default:
			break;
		}
		
//		Ores 50%
//		Copper 35%
//		cloth 50
//		Glass shards 50%
//		Metal alloy 10%
//		Also some possibility of finding blueprint 15%
//		Will unlock various options to build things within the spaceship

	}
}
