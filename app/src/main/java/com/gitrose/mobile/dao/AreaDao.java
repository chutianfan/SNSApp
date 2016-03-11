package com.gitrose.mobile.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.gitrose.mobile.db.AssetsDatabaseManager;
import com.gitrose.mobile.model.City;
import com.gitrose.mobile.model.Province;
import com.gitrose.mobile.model.Zone;
import java.util.ArrayList;
import java.util.List;

public class AreaDao {
    private static final String DB_NAME = "china_Province_city_zone.db";
    public static final String T_CITY = "T_City";
    public static final String T_PROVINCE = "T_Province";
    public static final String T_ZONE = "T_Zone";
    private SQLiteDatabase db;
    private AssetsDatabaseManager mg;

    public AreaDao(Context context) {
        AssetsDatabaseManager.initManager(context);
        this.mg = AssetsDatabaseManager.getManager();
        this.db = this.mg.getDatabase(DB_NAME);
    }

    public List<Province> getAllProvinces() {
        List<Province> provinces = new ArrayList();
        Cursor cursor = this.db.rawQuery("select * from T_Province", null);
        while (cursor.moveToNext()) {
            Province province = new Province();
            province.setProName(cursor.getString(cursor.getColumnIndex("ProName")));
            province.setProSort(cursor.getInt(cursor.getColumnIndex("ProSort")));
            province.setProRemark(cursor.getString(cursor.getColumnIndex("ProRemark")));
            provinces.add(province);
        }
        if (cursor != null) {
            cursor.close();
        }
        return provinces;
    }

    public List<City> getAllCityByProId(int proId) {
        List<City> citys = new ArrayList();
        Cursor cursor = this.db.rawQuery("select * from T_City where ProID=?", new String[]{new StringBuilder(String.valueOf(proId)).toString()});
        while (cursor.moveToNext()) {
            City city = new City();
            city.setCityName(cursor.getString(cursor.getColumnIndex("CityName")));
            city.setCitySort(cursor.getInt(cursor.getColumnIndex("CitySort")));
            city.setProID(proId);
            citys.add(city);
        }
        if (cursor != null) {
            cursor.close();
        }
        return citys;
    }

    public List<Zone> getAllZoneByCityId(int cityId) {
        List<Zone> zones = new ArrayList();
        Cursor cursor = this.db.rawQuery("select * from T_Zone where CityID=?", new String[]{new StringBuilder(String.valueOf(cityId)).toString()});
        while (cursor.moveToNext()) {
            Zone zone = new Zone();
            zone.setZoneName(cursor.getString(cursor.getColumnIndex("ZoneName")));
            zone.setZoneID(cursor.getInt(cursor.getColumnIndex("ZoneID")));
            zone.setCityID(cityId);
            zones.add(zone);
        }
        if (cursor != null) {
            cursor.close();
        }
        return zones;
    }
}
