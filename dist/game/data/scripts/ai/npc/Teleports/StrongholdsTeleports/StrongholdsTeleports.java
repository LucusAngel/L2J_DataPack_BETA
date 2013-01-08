/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.npc.Teleports.StrongholdsTeleports;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Strongholds teleport AI.<br>
 * Original Jython script by Kerberos.
 * @author Plim
 */
public class StrongholdsTeleports extends AbstractNpcAI
{
	private final static int[] NPCs =
	{
		32163, 32181, 32184, 32186
	};
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		
		if (player.getLevel() < 20)
			htmltext = String.valueOf(npc.getNpcId()) + ".htm";
		else
			htmltext = String.valueOf(npc.getNpcId()) + "-no.htm";
		
		return htmltext;
	}
	
	private StrongholdsTeleports(String name, String descr)
	{
		super(name, descr);
		
		addFirstTalkId(NPCs);
	}
	
	public static void main(String[] args)
	{
		new StrongholdsTeleports(StrongholdsTeleports.class.getSimpleName(), "ai/npc/Teleports/");
	}
}
