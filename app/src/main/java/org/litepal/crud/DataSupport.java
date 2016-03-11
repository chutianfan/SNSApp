package org.litepal.crud;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.util.BaseUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import org.litepal.exceptions.DataSupportException;
//import org.litepal.tablemanager.Connector;
//import org.litepal.util.BaseUtility;

public class DataSupport {
    private Map<String, Set<Long>> associatedModelsMapForJoinTable;
    private Map<String, Set<Long>> associatedModelsMapWithFK;
    private Map<String, Long> associatedModelsMapWithoutFK;
    private long baseObjId;
    private List<String> fieldsToSetToDefault;
    private List<String> listToClearAssociatedFK;
    private List<String> listToClearSelfFK;

//    public static synchronized ClusterQuery select(String... columns) {
//        ClusterQuery cQuery;
//        synchronized (DataSupport.class) {
//            cQuery = new ClusterQuery();
//            cQuery.mColumns = columns;
//        }
//        return cQuery;
//    }
//
//    public static synchronized ClusterQuery where(String... conditions) {
//        ClusterQuery cQuery;
//        synchronized (DataSupport.class) {
//            cQuery = new ClusterQuery();
//            cQuery.mConditions = conditions;
//        }
//        return cQuery;
//    }
//
//    public static synchronized ClusterQuery order(String column) {
//        ClusterQuery cQuery;
//        synchronized (DataSupport.class) {
//            cQuery = new ClusterQuery();
//            cQuery.mOrderBy = column;
//        }
//        return cQuery;
//    }
//
//    public static synchronized ClusterQuery limit(int value) {
//        ClusterQuery cQuery;
//        synchronized (DataSupport.class) {
//            cQuery = new ClusterQuery();
//            cQuery.mLimit = String.valueOf(value);
//        }
//        return cQuery;
//    }
//
//    public static synchronized ClusterQuery offset(int value) {
//        ClusterQuery cQuery;
//        synchronized (DataSupport.class) {
//            cQuery = new ClusterQuery();
//            cQuery.mOffset = String.valueOf(value);
//        }
//        return cQuery;
//    }
//
//    public static synchronized int count(Class<?> modelClass) {
//        int count;
//        synchronized (DataSupport.class) {
//            count = count(BaseUtility.changeCase(modelClass.getSimpleName()));
//        }
//        return count;
//    }
//
//    public static synchronized int count(String tableName) {
//        int count;
//        synchronized (DataSupport.class) {
//            count = new ClusterQuery().count(tableName);
//        }
//        return count;
//    }
//
//    public static synchronized double average(Class<?> modelClass, String column) {
//        double average;
//        synchronized (DataSupport.class) {
//            average = average(BaseUtility.changeCase(modelClass.getSimpleName()), column);
//        }
//        return average;
//    }
//
//    public static synchronized double average(String tableName, String column) {
//        double average;
//        synchronized (DataSupport.class) {
//            average = new ClusterQuery().average(tableName, column);
//        }
//        return average;
//    }
//
//    public static synchronized <T> T max(Class<?> modelClass, String columnName, Class<T> columnType) {
//        T max;
//        synchronized (DataSupport.class) {
//            max = max(BaseUtility.changeCase(modelClass.getSimpleName()), columnName, (Class) columnType);
//        }
//        return max;
//    }
//
//    public static synchronized <T> T max(String tableName, String columnName, Class<T> columnType) {
//        T max;
//        synchronized (DataSupport.class) {
//            max = new ClusterQuery().max(tableName, columnName, (Class) columnType);
//        }
//        return max;
//    }
//
//    public static synchronized <T> T min(Class<?> modelClass, String columnName, Class<T> columnType) {
//        T min;
//        synchronized (DataSupport.class) {
//            min = min(BaseUtility.changeCase(modelClass.getSimpleName()), columnName, (Class) columnType);
//        }
//        return min;
//    }
//
//    public static synchronized <T> T min(String tableName, String columnName, Class<T> columnType) {
//        T min;
//        synchronized (DataSupport.class) {
//            min = new ClusterQuery().min(tableName, columnName, (Class) columnType);
//        }
//        return min;
//    }
//
//    public static synchronized <T> T sum(Class<?> modelClass, String columnName, Class<T> columnType) {
//        T sum;
//        synchronized (DataSupport.class) {
//            sum = sum(BaseUtility.changeCase(modelClass.getSimpleName()), columnName, (Class) columnType);
//        }
//        return sum;
//    }
//
//    public static synchronized <T> T sum(String tableName, String columnName, Class<T> columnType) {
//        T sum;
//        synchronized (DataSupport.class) {
//            sum = new ClusterQuery().sum(tableName, columnName, (Class) columnType);
//        }
//        return sum;
//    }
//
//    public static synchronized <T> T find(Class<T> modelClass, long id) {
//        T find;
//        synchronized (DataSupport.class) {
//            find = find(modelClass, id, false);
//        }
//        return find;
//    }
//
//    public static synchronized <T> T find(Class<T> modelClass, long id, boolean isEager) {
//        T onFind;
//        synchronized (DataSupport.class) {
//            onFind = new QueryHandler(Connector.getDatabase()).onFind(modelClass, id, isEager);
//        }
//        return onFind;
//    }
//
//    public static synchronized <T> T findFirst(Class<T> modelClass) {
//        T findFirst;
//        synchronized (DataSupport.class) {
//            findFirst = findFirst(modelClass, false);
//        }
//        return findFirst;
//    }
//
//    public static synchronized <T> T findFirst(Class<T> modelClass, boolean isEager) {
//        T onFindFirst;
//        synchronized (DataSupport.class) {
//            onFindFirst = new QueryHandler(Connector.getDatabase()).onFindFirst(modelClass, isEager);
//        }
//        return onFindFirst;
//    }
//
//    public static synchronized <T> T findLast(Class<T> modelClass) {
//        T findLast;
//        synchronized (DataSupport.class) {
//            findLast = findLast(modelClass, false);
//        }
//        return findLast;
//    }
//
//    public static synchronized <T> T findLast(Class<T> modelClass, boolean isEager) {
//        T onFindLast;
//        synchronized (DataSupport.class) {
//            onFindLast = new QueryHandler(Connector.getDatabase()).onFindLast(modelClass, isEager);
//        }
//        return onFindLast;
//    }
//
//    public static synchronized <T> List<T> findAll(Class<T> modelClass, long... ids) {
//        List<T> findAll;
//        synchronized (DataSupport.class) {
//            findAll = findAll(modelClass, false, ids);
//        }
//        return findAll;
//    }
//
//    public static synchronized <T> List<T> findAll(Class<T> modelClass, boolean isEager, long... ids) {
//        List<T> onFindAll;
//        synchronized (DataSupport.class) {
//            onFindAll = new QueryHandler(Connector.getDatabase()).onFindAll(modelClass, isEager, ids);
//        }
//        return onFindAll;
//    }
//
//    public static synchronized Cursor findBySQL(String... sql) {
//        Cursor cursor = null;
//        synchronized (DataSupport.class) {
//            BaseUtility.checkConditionsCorrect(sql);
//            if (sql != null) {
//                if (sql.length > 0) {
//                    String[] selectionArgs;
//                    if (sql.length == 1) {
//                        selectionArgs = null;
//                    } else {
//                        selectionArgs = new String[(sql.length - 1)];
//                        System.arraycopy(sql, 1, selectionArgs, 0, sql.length - 1);
//                    }
//                    cursor = Connector.getDatabase().rawQuery(sql[0], selectionArgs);
//                }
//            }
//        }
//        return cursor;
//    }
//
//    public static synchronized int delete(Class<?> modelClass, long id) {
//        int rowsAffected;
//        synchronized (DataSupport.class) {
//            SQLiteDatabase db = Connector.getDatabase();
//            db.beginTransaction();
//            try {
//                rowsAffected = new DeleteHandler(db).onDelete(modelClass, id);
//                db.setTransactionSuccessful();
//                db.endTransaction();
//            } catch (Throwable th) {
//                db.endTransaction();
//            }
//        }
//        return rowsAffected;
//    }
//
    public static synchronized int deleteAll(Class<?> modelClass, String... conditions) {
        int deleteAll;
        synchronized (DataSupport.class) {
            deleteAll = deleteAll(BaseUtility.changeCase(modelClass.getSimpleName()), conditions);
        }
        return deleteAll;
    }

    public static synchronized int deleteAll(String tableName, String... conditions) {
        int onDeleteAll = 0;
//        synchronized (DataSupport.class) {
//            onDeleteAll = new DeleteHandler(Connector.getDatabase()).onDeleteAll(tableName, conditions);
//        }
        return onDeleteAll;
    }
//
//    public static synchronized int update(Class<?> modelClass, ContentValues values, long id) {
//        int onUpdate;
//        synchronized (DataSupport.class) {
//            onUpdate = new UpdateHandler(Connector.getDatabase()).onUpdate(modelClass, id, values);
//        }
//        return onUpdate;
//    }
//
//    public static synchronized int updateAll(Class<?> modelClass, ContentValues values, String... conditions) {
//        int updateAll;
//        synchronized (DataSupport.class) {
//            updateAll = updateAll(BaseUtility.changeCase(modelClass.getSimpleName()), values, conditions);
//        }
//        return updateAll;
//    }
//
//    public static synchronized int updateAll(String tableName, ContentValues values, String... conditions) {
//        int onUpdateAll;
//        synchronized (DataSupport.class) {
//            onUpdateAll = new UpdateHandler(Connector.getDatabase()).onUpdateAll(tableName, values, conditions);
//        }
//        return onUpdateAll;
//    }
//
//    public static synchronized <T extends DataSupport> void saveAll(Collection<T> collection) {
//        synchronized (DataSupport.class) {
//            SQLiteDatabase db = Connector.getDatabase();
//            db.beginTransaction();
//            try {
//                new SaveHandler(db).onSaveAll(collection);
//                db.setTransactionSuccessful();
//                db.endTransaction();
//            } catch (Exception e) {
//                throw new DataSupportException(e.getMessage());
//            } catch (Throwable th) {
//                db.endTransaction();
//            }
//        }
//    }
//
//    public synchronized int delete() {
//        int rowsAffected;
//        SQLiteDatabase db = Connector.getDatabase();
//        db.beginTransaction();
//        try {
//            rowsAffected = new DeleteHandler(db).onDelete(this);
//            db.setTransactionSuccessful();
//            db.endTransaction();
//        } catch (Throwable th) {
//            db.endTransaction();
//        }
//        return rowsAffected;
//    }
//
//    public synchronized int update(long id) {
//        int rowsAffected;
//        try {
//            rowsAffected = new UpdateHandler(Connector.getDatabase()).onUpdate(this, id);
//            getFieldsToSetToDefault().clear();
//        } catch (Exception e) {
//            throw new DataSupportException(e.getMessage());
//        }
//        return rowsAffected;
//    }
//
//    public synchronized int updateAll(String... conditions) {
//        int rowsAffected;
//        try {
//            rowsAffected = new UpdateHandler(Connector.getDatabase()).onUpdateAll(this, conditions);
//            getFieldsToSetToDefault().clear();
//        } catch (Exception e) {
//            throw new DataSupportException(e.getMessage());
//        }
//        return rowsAffected;
//    }
//
//    public synchronized boolean save() {
//        boolean z;
//        try {
//            saveThrows();
//            z = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            z = false;
//        }
//        return z;
//    }
//
//    public synchronized void saveThrows() {
//        SQLiteDatabase db = Connector.getDatabase();
//        db.beginTransaction();
//        try {
//            new SaveHandler(db).onSave(this);
//            clearAssociatedData();
//            db.setTransactionSuccessful();
//            db.endTransaction();
//        } catch (Exception e) {
//            throw new DataSupportException(e.getMessage());
//        } catch (Throwable th) {
//            db.endTransaction();
//        }
//    }

    public boolean isSaved() {
        return this.baseObjId > 0;
    }

    public void setToDefault(String fieldName) {
        getFieldsToSetToDefault().add(fieldName);
    }

    protected DataSupport() {
    }

    protected long getBaseObjId() {
        return this.baseObjId;
    }

    protected String getClassName() {
        return getClass().getName();
    }

//    protected String getTableName() {
//        return BaseUtility.changeCase(getClass().getSimpleName());
//    }

    List<String> getFieldsToSetToDefault() {
        if (this.fieldsToSetToDefault == null) {
            this.fieldsToSetToDefault = new ArrayList();
        }
        return this.fieldsToSetToDefault;
    }

    void addAssociatedModelWithFK(String associatedTableName, long associatedId) {
        Set<Long> associatedIdsWithFKSet = (Set) getAssociatedModelsMapWithFK().get(associatedTableName);
        if (associatedIdsWithFKSet == null) {
            associatedIdsWithFKSet = new HashSet();
            associatedIdsWithFKSet.add(Long.valueOf(associatedId));
            this.associatedModelsMapWithFK.put(associatedTableName, associatedIdsWithFKSet);
            return;
        }
        associatedIdsWithFKSet.add(Long.valueOf(associatedId));
    }

    Map<String, Set<Long>> getAssociatedModelsMapWithFK() {
        if (this.associatedModelsMapWithFK == null) {
            this.associatedModelsMapWithFK = new HashMap();
        }
        return this.associatedModelsMapWithFK;
    }

    void addAssociatedModelForJoinTable(String associatedModelName, long associatedId) {
        Set<Long> associatedIdsM2MSet = (Set) getAssociatedModelsMapForJoinTable().get(associatedModelName);
        if (associatedIdsM2MSet == null) {
            associatedIdsM2MSet = new HashSet();
            associatedIdsM2MSet.add(Long.valueOf(associatedId));
            this.associatedModelsMapForJoinTable.put(associatedModelName, associatedIdsM2MSet);
            return;
        }
        associatedIdsM2MSet.add(Long.valueOf(associatedId));
    }

    void addEmptyModelForJoinTable(String associatedModelName) {
        if (((Set) getAssociatedModelsMapForJoinTable().get(associatedModelName)) == null) {
            this.associatedModelsMapForJoinTable.put(associatedModelName, new HashSet());
        }
    }

    Map<String, Set<Long>> getAssociatedModelsMapForJoinTable() {
        if (this.associatedModelsMapForJoinTable == null) {
            this.associatedModelsMapForJoinTable = new HashMap();
        }
        return this.associatedModelsMapForJoinTable;
    }

    void addAssociatedModelWithoutFK(String associatedTableName, long associatedId) {
        getAssociatedModelsMapWithoutFK().put(associatedTableName, Long.valueOf(associatedId));
    }

    Map<String, Long> getAssociatedModelsMapWithoutFK() {
        if (this.associatedModelsMapWithoutFK == null) {
            this.associatedModelsMapWithoutFK = new HashMap();
        }
        return this.associatedModelsMapWithoutFK;
    }

    void addFKNameToClearSelf(String foreignKeyName) {
        List<String> list = getListToClearSelfFK();
        if (!list.contains(foreignKeyName)) {
            list.add(foreignKeyName);
        }
    }

    List<String> getListToClearSelfFK() {
        if (this.listToClearSelfFK == null) {
            this.listToClearSelfFK = new ArrayList();
        }
        return this.listToClearSelfFK;
    }

    void addAssociatedTableNameToClearFK(String associatedTableName) {
        List<String> list = getListToClearAssociatedFK();
        if (!list.contains(associatedTableName)) {
            list.add(associatedTableName);
        }
    }

    List<String> getListToClearAssociatedFK() {
        if (this.listToClearAssociatedFK == null) {
            this.listToClearAssociatedFK = new ArrayList();
        }
        return this.listToClearAssociatedFK;
    }

    void clearAssociatedData() {
        clearIdOfModelWithFK();
        clearIdOfModelWithoutFK();
        clearIdOfModelForJoinTable();
        clearFKNameList();
    }

    private void clearIdOfModelWithFK() {
        for (String associatedModelName : getAssociatedModelsMapWithFK().keySet()) {
            ((Set) this.associatedModelsMapWithFK.get(associatedModelName)).clear();
        }
        this.associatedModelsMapWithFK.clear();
    }

    private void clearIdOfModelWithoutFK() {
        getAssociatedModelsMapWithoutFK().clear();
    }

    private void clearIdOfModelForJoinTable() {
        for (String associatedModelName : getAssociatedModelsMapForJoinTable().keySet()) {
            ((Set) this.associatedModelsMapForJoinTable.get(associatedModelName)).clear();
        }
        this.associatedModelsMapForJoinTable.clear();
    }

    private void clearFKNameList() {
        getListToClearSelfFK().clear();
        getListToClearAssociatedFK().clear();
    }
}
