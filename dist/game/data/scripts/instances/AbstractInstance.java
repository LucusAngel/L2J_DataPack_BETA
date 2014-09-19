/*
 * Copyright (C) 2004-2014 L2J DataPack
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
package instances;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.util.Util;

/**
 * Abstract class for Instances.
 * @author FallenAngel
 */
public abstract class AbstractInstance extends AbstractNpcAI
{
	public final Logger _log = Logger.getLogger(getClass().getSimpleName());
	
	public AbstractInstance(String name, String desc)
	{
		super(name, desc);
	}
	
	public AbstractInstance(String name)
	{
		super(name, "instances");
	}
	
	protected void enterInstance(L2PcInstance player, InstanceWorld instance, String template, int templateId)
	{
		final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		if (world != null)
		{
			if (world.getTemplateId() == templateId)
			{
				onEnterInstance(player, world, false);
				return;
			}
			player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
			return;
		}
		
		if (checkConditions(player))
		{
			final InstanceWorld playerWorld = instance;
			playerWorld.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
			playerWorld.setTemplateId(templateId);
			playerWorld.setStatus(0);
			InstanceManager.getInstance().addWorld(playerWorld);
			onEnterInstance(player, playerWorld, true);
			
			if (Config.DEBUG_INSTANCES)
			{
				_log.info("Instance " + InstanceManager.getInstance().getInstance(playerWorld.getInstanceId()).getName() + " (" + playerWorld.getTemplateId() + ") has been created by player " + player.getName());
			}
		}
	}
	
	protected void onEnterInstance(L2PcInstance player, InstanceWorld world, boolean firstEntrance)
	{
		_log.log(Level.WARNING, "Undefined onEnterInstance! ", new RuntimeException());
	}
	
	protected boolean checkConditions(L2PcInstance player)
	{
		return true;
	}
	
	/**
	 * Spawns group of instance NPC's
	 * @param groupName - name of group from XML definition to spawn
	 * @param instanceId - ID of instance
	 * @return list of spawned NPC's
	 */
	protected List<L2Npc> spawnGroup(String groupName, int instanceId)
	{
		return InstanceManager.getInstance().getInstance(instanceId).spawnGroup(groupName);
	}
	
	/**
	 * Save Reenter time for every player in InstanceWorld.
	 * @param world - the InstanceWorld
	 * @param days - List of days
	 * @param hour - the hour
	 * @param min - the min
	 */
	protected void setReenterTime(InstanceWorld world, DayOfWeek[] days, int hour, int min)
	{
		setReenterTime(world, Arrays.asList(days), hour, min);
	}
	
	/**
	 * Save Reenter time for every player in InstanceWorld.
	 * @param world - the InstanceWorld
	 * @param days - List of days
	 * @param hour - the hour
	 * @param min - the min
	 */
	protected void setReenterTime(InstanceWorld world, List<DayOfWeek> days, int hour, int min)
	{
		final Calendar reenter = Calendar.getInstance();
		final LocalDateTime date = Util.getNextClosestDateTime(days, hour, min);
		reenter.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond());
		
		for (int objectId : world.getAllowed())
		{
			InstanceManager.getInstance().setInstanceTime(objectId, world.getTemplateId(), reenter.getTimeInMillis());
			final L2PcInstance player = L2World.getInstance().getPlayer(objectId);
			if ((player != null) && player.isOnline())
			{
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.INSTANT_ZONE_S1_RESTRICTED).addString(InstanceManager.getInstance().getInstance(world.getInstanceId()).getName()));
			}
		}
	}
	
	/**
	 * Save Reenter time for every player in InstanceWorld.
	 * @param world - the InstanceWorld
	 * @param time - Time in miliseconds
	 */
	protected void setReenterTime(InstanceWorld world, int time)
	{
		for (int objectId : world.getAllowed())
		{
			InstanceManager.getInstance().setInstanceTime(objectId, world.getTemplateId(), System.currentTimeMillis() + time);
			final L2PcInstance player = L2World.getInstance().getPlayer(objectId);
			if ((player != null) && player.isOnline())
			{
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.INSTANT_ZONE_S1_RESTRICTED).addString(InstanceManager.getInstance().getInstance(world.getInstanceId()).getName()));
			}
		}
	}
}