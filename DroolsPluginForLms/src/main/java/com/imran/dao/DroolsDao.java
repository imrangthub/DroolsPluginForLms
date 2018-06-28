package com.imran.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.imran.model.DroolsFileEntity;
import com.imran.common.BaseService;

@Repository
public class DroolsDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	BaseService baseService;
	

	
	@Transactional
    public void addDrools(DroolsFileEntity droolObj){
		sessionFactory.getCurrentSession().save(droolObj);
    }
	
	@Transactional
	public void updateDrool(DroolsFileEntity droolObj){
		sessionFactory.getCurrentSession().update(droolObj);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<DroolsFileEntity> listDrool() {
		return (List<DroolsFileEntity>) sessionFactory.getCurrentSession().createCriteria(DroolsFileEntity.class).list();
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public DroolsFileEntity getDroolById(long droolsId){
		return (DroolsFileEntity) sessionFactory.getCurrentSession().get(DroolsFileEntity.class, droolsId);
	}
	
	@Transactional
	public void activatedRules(long droolsId){
		List<DroolsFileEntity> rulesList= listDrool();
		for(DroolsFileEntity obj:rulesList){
			obj.setIsCurrentActiveFile(0);
			sessionFactory.getCurrentSession().update(obj);
		}
		DroolsFileEntity droolObj = getDroolById(droolsId);
		if(droolObj != null){
			droolObj.setIsCurrentActiveFile(1);
			sessionFactory.getCurrentSession().update(droolObj);
		}
	}


	@Transactional
	public void removeDrool(long droolsId){
		DroolsFileEntity droolObj = getDroolById(droolsId);
		if(droolObj != null){
			if(!droolObj.getDrools_file().equals(""))
				System.out.println("Delete fImg NFame:"+droolObj.getDrools_file());
			baseService.removeFile(droolObj.getDrools_file());
			sessionFactory.getCurrentSession().delete(droolObj);
		}
	}
	
	@Transactional
	public String getActivatedFileName(){
		String activatedFileName="1837141852_discount.xlsx";
		List<DroolsFileEntity> rulesList= listDrool();
		for(DroolsFileEntity obj:rulesList){
			if(obj.getIsCurrentActiveFile()==1){
				activatedFileName = obj.getDrools_file();
			}
		}
		System.out.println("Current Activated file name: "+activatedFileName);
		return activatedFileName;
	}

}
