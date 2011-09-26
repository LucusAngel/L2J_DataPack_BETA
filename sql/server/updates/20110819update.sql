ALTER TABLE `olympiad_nobles` CHANGE `class_id` `class_id` tinyint(3) unsigned NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles` CHANGE `olympiad_points` `olympiad_points` int(10) NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles` CHANGE `competitions_done` `competitions_done` smallint(3) NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles` CHANGE `competitions_won` `competitions_won` smallint(3) NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles` CHANGE `competitions_lost` `competitions_lost` smallint(3) NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles` CHANGE `competitions_drawn` `competitions_drawn` smallint(3) NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles` CHANGE `competitions_done_week` `competitions_done_week` tinyint(3) NOT NULL default 0;
ALTER TABLE `olympiad_nobles` CHANGE `competitions_done_week_classed` `competitions_done_week_classed` tinyint(3) NOT NULL default 0;
ALTER TABLE `olympiad_nobles` CHANGE `competitions_done_week_non_classed` `competitions_done_week_non_classed` tinyint(3) NOT NULL default 0;
ALTER TABLE `olympiad_nobles` CHANGE `competitions_done_week_team` `competitions_done_week_team` tinyint(3) NOT NULL default 0;
ALTER TABLE `olympiad_nobles_eom` CHANGE `class_id` `class_id` tinyint(3) unsigned NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles_eom` CHANGE `olympiad_points` `olympiad_points` int(10) NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles_eom` CHANGE `competitions_done` `competitions_done` smallint(3) NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles_eom` CHANGE `competitions_won` `competitions_won` smallint(3) NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles_eom` CHANGE `competitions_lost` `competitions_lost` smallint(3) NOT NULL DEFAULT 0;
ALTER TABLE `olympiad_nobles_eom` CHANGE `competitions_drawn` `competitions_drawn` smallint(3) NOT NULL DEFAULT 0;