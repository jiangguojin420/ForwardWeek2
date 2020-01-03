package com.bw.forwardweek2.database;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.bw.forwardweek2.model.dao.ClassifyTable;
import com.bw.forwardweek2.model.dao.CommodityTable;

import com.bw.forwardweek2.database.ClassifyTableDao;
import com.bw.forwardweek2.database.CommodityTableDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig classifyTableDaoConfig;
    private final DaoConfig commodityTableDaoConfig;

    private final ClassifyTableDao classifyTableDao;
    private final CommodityTableDao commodityTableDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        classifyTableDaoConfig = daoConfigMap.get(ClassifyTableDao.class).clone();
        classifyTableDaoConfig.initIdentityScope(type);

        commodityTableDaoConfig = daoConfigMap.get(CommodityTableDao.class).clone();
        commodityTableDaoConfig.initIdentityScope(type);

        classifyTableDao = new ClassifyTableDao(classifyTableDaoConfig, this);
        commodityTableDao = new CommodityTableDao(commodityTableDaoConfig, this);

        registerDao(ClassifyTable.class, classifyTableDao);
        registerDao(CommodityTable.class, commodityTableDao);
    }
    
    public void clear() {
        classifyTableDaoConfig.clearIdentityScope();
        commodityTableDaoConfig.clearIdentityScope();
    }

    public ClassifyTableDao getClassifyTableDao() {
        return classifyTableDao;
    }

    public CommodityTableDao getCommodityTableDao() {
        return commodityTableDao;
    }

}
