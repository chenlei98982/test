package com.zznode.nsm.icp.alarm.daosvc;

import java.util.Date;

import com.zznode.nsm.icp.alarm.data.PlatFormAlarm;
import com.zznode.nsm.icp.alarm.exception.PlatFormAlarmException;

public interface IPlatFormAlarmDaoSvc {
	public void newAlarm(PlatFormAlarm alarm) throws PlatFormAlarmException;
	public void clearAlarm(String alarmIdentifier, Date clearTime)  throws PlatFormAlarmException;
}
