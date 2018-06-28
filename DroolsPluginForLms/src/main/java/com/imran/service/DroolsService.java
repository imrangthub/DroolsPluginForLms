package com.imran.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imran.common.BaseService;
import com.imran.dao.DroolsDao;
import com.imran.model.DroolsFileEntity;


@Service
@Transactional
public class DroolsService extends BaseService{
	
	
	@Autowired
	DroolsDao droolsDao;

	
    public void createOrUpdateDrools(DroolsFileEntity droolsObj){
    	droolsDao.addDrools(droolsObj);
    }
	
	public void updateDrools(DroolsFileEntity droolsObj){
		droolsDao.updateDrool(droolsObj);
	}
	
	public List<DroolsFileEntity> droolsList(){
		return droolsDao.listDrool();
	}
	
	public DroolsFileEntity findByDroolstId(long droolsId){
		return droolsDao.getDroolById(droolsId);
	}
	
	public void removeDrools(long droolsId){
		droolsDao.removeDrool(droolsId);
	}
	
	public void activatedRules(long droolsId){
		droolsDao.activatedRules(droolsId);
	}
	
	public String getActivatedFileName(){
		return droolsDao.getActivatedFileName();
	}

}
