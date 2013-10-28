package com.nemock.CFGVillager.core.handler;

import java.util.Random;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;


public class CFGVillagerHandler implements IVillageTradeHandler
{

	private int tradesData[][][];
	
	public CFGVillagerHandler(String[][] trades)
	{
		this.tradesData = new int[trades.length][3][3];
		String buffer[] = new String[3];
		for(int i = 0; i < trades.length; i++)
		{
			for(int j = 0; j < trades[i].length; j++)
			{
				buffer = trades[i][j].split("-");
				for (int k = 0; k < buffer.length; k++)
				{
					this.tradesData[i][j][k] = Integer.parseInt(buffer[k]);
				}
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipes, Random random)
	{
	    for (int i = 0; i < tradesData.length ; i++)
	    {
	    	if (tradesData[i][0][0] != 0000 && tradesData[i][1][0] != 0000 && tradesData[i][2][0] != 0000)
	    	{
	    		if (tradesData[i][1][1] != 0)
	    		{
	    			recipes.add(new MerchantRecipe(
	    					//first item to pay
	    					new ItemStack(tradesData[i][0][0], tradesData[i][0][1], tradesData[i][0][2]),
	    					//second item to pay
	    					new ItemStack(tradesData[i][1][0], tradesData[i][1][1], tradesData[i][1][2]),
	    					//item you recive
	    					new ItemStack(tradesData[i][2][0], tradesData[i][2][1], tradesData[i][2][2])));
	    		}
	    		else
	    		{
	    			recipes.add(new MerchantRecipe(
	    					//first item to pay
	    					new ItemStack(tradesData[i][0][0], tradesData[i][0][1], tradesData[i][0][2]),
	    					//item you recive
	    					new ItemStack(tradesData[i][2][0], tradesData[i][2][1], tradesData[i][2][2])));
	    		}
	    	}
	    	
	    }
	}

}
