package com.nemock.CFGVillager.core.handler;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigurationHandler
{
	public static Configuration config = new Configuration();
	
	//the Base id from where villagers start being added
	public static int idBase;
	//the package of trades the villagers have
	public static String trades[][][];
	//the amount of newly addet villagers
	public static int countVillagers;
	
	public static void preInit(File file)
	{
		config = new Configuration(file);
		config.load();
		
		//comment in config file
		config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, "Read the manual.txt to learn how to add a new villager and new trades to the game (ints in the CFGVillager.zip file).\nidBase starts at 9, set it higher if there are more than 3 other mods adding new Villagers.\namount regulates the amount of new types of villagers you want to add.");
		
		//first Villagers id from wich this mods starts adding them
		idBase = config.get(Configuration.CATEGORY_GENERAL, "idBase", 9).getInt();
		
		//amount of new villager types
		int amount = config.get(Configuration.CATEGORY_GENERAL, "VillagerAmount", 0).getInt();
		
		//initiates the trades array
		trades = new String[amount][][];
		
		for( int i = 1; i <= amount; i++)
		{
			
			//the categorys name
			String category = "Villager " + Integer.toString(i) +" trades";
			
			//gives the amount of trades
			int tradeCount = config.get(category, "HowManyTrades", 1).getInt();
			
			//default trade value
			String def[] = {"0001-1-0","0001-0-0","0001-1-0"};
			String tradePackages [][] = new String[tradeCount][];
			
			for(int j = 0; j < tradeCount; j++)
			{
				tradePackages [j] = config.get(category, "trade-"+ Integer.toString(j+1) , def).getStringList();
			}
			trades[i-1]= tradePackages;
			
		}
		
		countVillagers = amount;
		
		config.save();
	}
}
