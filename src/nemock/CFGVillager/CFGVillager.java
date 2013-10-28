package nemock.CFGVillager;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraftforge.common.Configuration;



@Mod(modid = "CFGVillagers", name = "CFG Villagers", version = "Alpha-0.0.1", dependencies = "before:mca")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class CFGVillager {
	
	//the amount of newly addet villagers
	private int countVillagers;
	
	//the package of trades the villagers have
	private String trades[][][];
	
	private int idBase;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {

		//-----Config file-----
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();
		
		//comment in config file
		config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, "Read the manual.txt to learn how to add a new villager and new trades to the game (ints in the CFGVillager.zip file).\nidBase starts at 9, set it higher if there are more than 3 other mods adding new Villagers.\namount regulates the amount of new types of villagers you want to add.");
		
		//first Villagers id from wich this mods starts adding them
		idBase = config.get(Configuration.CATEGORY_GENERAL, "idBase", 9).getInt();
		
		//amount of new villager types
		int amount = config.get(Configuration.CATEGORY_GENERAL, "VillagerAmount", 0).getInt();
		
		//initiates the trades array
		this.trades = new String[amount][][];
		
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
			this.trades[i-1]= tradePackages;
			
		}
		
		this.countVillagers = amount;
		
		config.save();
		
		//-----Config file end-----
		
	}
	
	
	@Init
	public void init(FMLInitializationEvent event)
	{
		for (int i = 0; i < countVillagers; i++)
		{
			//Still need to figure this out
			String tPath ="/textures/villager_"+ Integer.toString(i+1) +".png";
			int id = idBase + i;
			
			//registers new villager, first id (int) then texture path (String)
			VillagerRegistry.instance().registerVillagerType( id, tPath);
			VillagerRegistryMCA.registerVillagerType(id);
			
			String villagertrades[][] = trades[i];
			
			CFGVillagerHandler newTradeHandler = new CFGVillagerHandler(villagertrades);
			
			VillagerRegistry.instance().registerVillageTradeHandler(id, newTradeHandler);
		}
		
		VillagerRegistry.instance();
		
		//required to get Villagers spawning naturaly
		VillagerRegistry.getRegisteredVillagers(); 
	}

	@PostInit
	public static void postInit(FMLPostInitializationEvent event)
	{
		
	}

}
