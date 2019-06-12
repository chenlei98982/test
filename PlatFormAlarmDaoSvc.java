package com.zznode.nsm.icp.alarm.daosvc;

import java.util.Date;

import com.zznode.nsm.icp.alarm.dao.PlatFormAlarmDao;
import com.zznode.nsm.icp.alarm.data.ClearPlatFormAlarm;
import com.zznode.nsm.icp.alarm.data.PlatFormAlarm;
import com.zznode.nsm.icp.alarm.exception.PlatFormAlarmException;

public class PlatFormAlarmDaoSvc implements IPlatFormAlarmDaoSvc {
	private PlatFormAlarmDao dao;
	
	public PlatFormAlarmDaoSvc() {
		super();
	}

	public void newAlarm(PlatFormAlarm alarm) throws PlatFormAlarmException {
		if(alarm.getAlarmIdentifier() == null) {
			throw new PlatFormAlarmException("alarmIdentifier是必要属性");
		}
		Long existsId = dao.getExistsAlarmId(alarm.getAlarmIdentifier());
		if(existsId != null) {
			dao.updateAlarm(alarm);
			return;
		}
		//在告警临时表里添加,0表示活动告警，1表示清除告警
		//dao.saveAlarm(alarm);
		Long alarmTempId = dao.getExistsTempAlarmId(alarm.getAlarmIdentifier());
		if(alarmTempId != null) {
			dao.updateAlarmTemp(alarm);
			return;
		}
		dao.saveTempAlarm(alarm);
	}
	
	public void clearAlarm(String alarmIdentifier, Date clearTime)  throws PlatFormAlarmException {
		/*Long alarmId = dao.getExistsAlarmId(alarmIdentifier);
		if(alarmId == null) {
			return;
		}
		dao.saveHistoryAlarm(alarmId, new ClearPlatFormAlarm(alarmIdentifier, clearTime));
		dao.deleteAlarm(alarmId);*/
		Long alarmId = dao.getExistsAlarmId(alarmIdentifier);
		if(alarmId != null) {
			dao.saveHistoryAlarm(alarmId, new ClearPlatFormAlarm(alarmIdentifier, clearTime));
			dao.deleteAlarm(alarmId);
		} else {
			//查询一下
			Long alarmTempId = dao.getExistsTempAlarmId(alarmIdentifier);
			if(alarmTempId == null) {
				return;
			} 
			dao.updateAlarmTempStatus(alarmTempId);
		}

		
	}

	public PlatFormAlarmDao getDao() {
		return dao;
	}

	public void setDao(PlatFormAlarmDao dao) {
		this.dao = dao;
	}
}
