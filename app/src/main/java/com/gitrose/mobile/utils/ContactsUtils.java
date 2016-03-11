package com.gitrose.mobile.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
//import com.google.gson.Gson;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.dao.ContactsDao;
import com.gitrose.mobile.model.ContactsAddressInfo;
import com.gitrose.mobile.model.ContactsDateInfo;
import com.gitrose.mobile.model.ContactsEmailInfo;
import com.gitrose.mobile.model.ContactsIMInfo;
import com.gitrose.mobile.model.ContactsLocalInfo;
import com.gitrose.mobile.model.ContactsPhoneInfo;
import com.gitrose.mobile.model.ContactsUrlInfo;
import com.gitrose.mobile.model.ContactsUserInfo;
//import io.rong.message.BuildConfig;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import org.json.JSONException;
//import shouji.gexing.framework.utils.DebugUtils;
//import shouji.gexing.framework.utils.SpUtils;
//import shouji.gexing.framework.utils.TimeUtils;

public class ContactsUtils {
    private static int counts;
    private static String my_tutu_id;
    protected static String uuid;

    static {
        my_tutu_id = MyApplication.getInstance().getUserinfo().getUid();
        counts = 1;
    }

    public static void setMy_tutu_id(String id) {
        my_tutu_id = id;
    }

    public static List<ContactsUserInfo> getContactInfo(Context context) throws JSONException {
        //DebugUtils.error("\u4f18\u5316\u540e\u65b9\u6cd5--start");
        //Long start_time = Long.valueOf(TimeUtils.getNow().getTime());
        List<ContactsUserInfo> userList = new ArrayList();
        //String mimetype = BuildConfig.FLAVOR;
        String mimetype = "";
        HashMap<String, ContactsUserInfo> contactsHashMap = new HashMap();
        try {
            Cursor cursor = context.getContentResolver().query(Data.CONTENT_URI, null, null, null, "raw_contact_id");
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                Editor editor = context.getSharedPreferences("local_version", 0).edit();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    mimetype = cursor.getString(cursor.getColumnIndex("mimetype"));
                    String contactId = cursor.getString(cursor.getColumnIndex("raw_contact_id"));
                    ContactsUserInfo contactsInfo = (ContactsUserInfo) contactsHashMap.get(contactId);
                    if (contactsInfo == null) {
                        contactsInfo = new ContactsUserInfo();
                        ArrayList<ContactsAddressInfo> addList = new ArrayList();
                        ArrayList<ContactsEmailInfo> emailList = new ArrayList();
                        ArrayList<ContactsIMInfo> imList = new ArrayList();
                        ArrayList<ContactsUrlInfo> urlList = new ArrayList();
                        ArrayList<ContactsPhoneInfo> phoneList = new ArrayList();
                        ArrayList<ContactsDateInfo> dateList = new ArrayList();
                        contactsInfo.setAdd(addList);
                        contactsInfo.setEmail(emailList);
                        contactsInfo.setIm(imList);
                        contactsInfo.setUrl(urlList);
                        contactsInfo.setPhone(phoneList);
                        contactsInfo.setDate(dateList);
                    }
                    String localId = cursor.getString(cursor.getColumnIndex("_id"));
                    contactsInfo.setLocal_id(localId);
                    editor.putInt(localId, cursor.getInt(cursor.getColumnIndex("version")));
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        contactsInfo.setName(cursor.getString(cursor.getColumnIndex("data1")));
                    }
                    if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                        ContactsPhoneInfo cpi = new ContactsPhoneInfo();
                        int phoneType = cursor.getInt(cursor.getColumnIndex("data2"));
                        cpi.setKind(context.getString(Phone.getTypeLabelResource(phoneType)));
                        if (phoneType == 2) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 1) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 3) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 4) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 5) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 6) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 8) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 10) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 9) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 11) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 12) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 14) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 15) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 16) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 17) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 18) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 19) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (phoneType == 20) {
                            cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        contactsInfo.getPhone().add(cpi);
                    }
                    if ("vnd.android.cursor.item/email_v2".equals(mimetype)) {
                        ContactsEmailInfo contactsEmailInfo = new ContactsEmailInfo();
                        int emailType = cursor.getInt(cursor.getColumnIndex("data2"));
                        contactsEmailInfo.setKind(context.getString(Email.getTypeLabelResource(emailType)));
                        if (emailType == 0) {
                            contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        } else if (emailType == 1) {
                            contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (emailType == 0) {
                            contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        } else if (emailType == 2) {
                            contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (emailType == 0) {
                            contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        } else if (emailType == 4) {
                            contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        contactsInfo.getEmail().add(contactsEmailInfo);
                    }
                    if ("vnd.android.cursor.item/contact_event".equals(mimetype)) {
                        ContactsDateInfo contactsEventInfo = new ContactsDateInfo();
                        int eventType = cursor.getInt(cursor.getColumnIndex("data2"));
                        contactsEventInfo.setKind(context.getString(Event.getTypeResource(Integer.valueOf(eventType))));
                        if (eventType == 3) {
                            contactsEventInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (eventType == 1) {
                            contactsEventInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        contactsInfo.getDate().add(contactsEventInfo);
                    }
                    if ("vnd.android.cursor.item/im".equals(mimetype)) {
                        ContactsIMInfo contactsIMInfo = new ContactsIMInfo();
                        int protocal = cursor.getInt(cursor.getColumnIndex("data5"));
                        int columnIndex = cursor.getColumnIndex("data2");
                        contactsIMInfo.setKind(context.getString(Im.getTypeLabelResource(cursor.getInt(columnIndex))));
                        if (protocal == 0) {
                            contactsIMInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        } else if (1 == protocal) {
                            contactsIMInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        if (4 == protocal) {
                            contactsIMInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                        }
                        contactsInfo.getIm().add(contactsIMInfo);
                    }
                    if ("vnd.android.cursor.item/note".equals(mimetype)) {
                        contactsInfo.setNote(cursor.getString(cursor.getColumnIndex("data1")));
                    }
                    if ("vnd.android.cursor.item/nickname".equals(mimetype)) {
                        contactsInfo.setNote(cursor.getString(cursor.getColumnIndex("data1")));
                    }
                    if ("vnd.android.cursor.item/postal-address_v2".equals(mimetype)) {
                        ContactsAddressInfo contactsAddressInfo = new ContactsAddressInfo();
                        int postalType = cursor.getInt(cursor.getColumnIndex("data2"));
                        if (postalType == 2) {
                            String street = cursor.getString(cursor.getColumnIndex("data4"));
                            String city = cursor.getString(cursor.getColumnIndex("data7"));
                            String box = cursor.getString(cursor.getColumnIndex("data5"));
                            String area = cursor.getString(cursor.getColumnIndex("data6"));
                            String state = cursor.getString(cursor.getColumnIndex("data8"));
                            String zip = cursor.getString(cursor.getColumnIndex("data9"));
                            String country = cursor.getString(cursor.getColumnIndex("data10"));
                            contactsAddressInfo.setCity(city);
                            contactsAddressInfo.setCy(country);
                            contactsAddressInfo.setPc(zip);
                            contactsAddressInfo.setPro(state);
                        }
                        if (postalType == 1) {
                            String homeStreet = cursor.getString(cursor.getColumnIndex("data4"));
                            String homeCity = cursor.getString(cursor.getColumnIndex("data7"));
                            String homeBox = cursor.getString(cursor.getColumnIndex("data5"));
                            String homeArea = cursor.getString(cursor.getColumnIndex("data6"));
                            String homeState = cursor.getString(cursor.getColumnIndex("data8"));
                            String homeZip = cursor.getString(cursor.getColumnIndex("data9"));
                            String homeCountry = cursor.getString(cursor.getColumnIndex("data10"));
                            contactsAddressInfo.setCity(homeCity);
                            contactsAddressInfo.setCy(homeCountry);
                            contactsAddressInfo.setPc(homeZip);
                            contactsAddressInfo.setPro(homeState);
                        }
                        if (postalType == 3) {
                            String otherStreet = cursor.getString(cursor.getColumnIndex("data4"));
                            String otherCity = cursor.getString(cursor.getColumnIndex("data7"));
                            String otherBox = cursor.getString(cursor.getColumnIndex("data5"));
                            String otherArea = cursor.getString(cursor.getColumnIndex("data6"));
                            String otherState = cursor.getString(cursor.getColumnIndex("data8"));
                            String otherZip = cursor.getString(cursor.getColumnIndex("data9"));
                            String otherCountry = cursor.getString(cursor.getColumnIndex("data10"));
                            contactsAddressInfo.setCity(otherCity);
                            contactsAddressInfo.setCy(otherCountry);
                            contactsAddressInfo.setPc(otherZip);
                            contactsAddressInfo.setPro(otherState);
                        }
                        contactsInfo.getAdd().add(contactsAddressInfo);
                    }
                    contactsHashMap.put(contactId, contactsInfo);
                }
                editor.commit();
            }
            for (Entry<String, ContactsUserInfo> info : contactsHashMap.entrySet()) {
                userList.add((ContactsUserInfo) info.getValue());
            }
            if (!(cursor == null || cursor.isClosed())) {
                try {
                    cursor.close();
                } catch (Exception e) {
                }
            }
//            DebugUtils.error("\u65f6\u95f4\u5dee\u503c2:" + (Long.valueOf(TimeUtils.getNow().getTime()).longValue() - start_time.longValue()));
//            DebugUtils.error(new Gson().toJson((Object) userList));
            return userList;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static List<ContactsUserInfo> getExtraContactInfo(Context context) throws JSONException {
//        DebugUtils.error("getExtraContactInfo--start");
//        Long start_time = Long.valueOf(TimeUtils.getNow().getTime());
        List<ContactsUserInfo> userList = new ArrayList();
//        String mimetype = BuildConfig.FLAVOR;
        String mimetype = "";
        HashMap<String, ContactsUserInfo> contactsHashMap = new HashMap();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(Data.CONTENT_URI, null, null, null, "raw_contact_id");
        } catch (Exception e) {
        }
        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        }
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            mimetype = cursor.getString(cursor.getColumnIndex("mimetype"));
            String contactId = cursor.getString(cursor.getColumnIndex("raw_contact_id"));
            ContactsUserInfo contactsInfo = (ContactsUserInfo) contactsHashMap.get(contactId);
            if (contactsInfo == null) {
                contactsInfo = new ContactsUserInfo();
                ArrayList<ContactsAddressInfo> addList = new ArrayList();
                ArrayList<ContactsEmailInfo> emailList = new ArrayList();
                ArrayList<ContactsIMInfo> imList = new ArrayList();
                ArrayList<ContactsUrlInfo> urlList = new ArrayList();
                ArrayList<ContactsPhoneInfo> phoneList = new ArrayList();
                ArrayList<ContactsDateInfo> dateList = new ArrayList();
                contactsInfo.setAdd(addList);
                contactsInfo.setEmail(emailList);
                contactsInfo.setIm(imList);
                contactsInfo.setUrl(urlList);
                contactsInfo.setPhone(phoneList);
                contactsInfo.setDate(dateList);
            }
            String localId = cursor.getString(cursor.getColumnIndex("_id"));
            contactsInfo.setLocal_id(localId);
            contactsInfo.setHead(getHeadByID(context, localId));
            contactsInfo.setVersion(cursor.getInt(cursor.getColumnIndex("version")));
            if ("vnd.android.cursor.item/name".equals(mimetype)) {
                contactsInfo.setName(cursor.getString(cursor.getColumnIndex("data1")));
            }
            if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                ContactsPhoneInfo cpi = new ContactsPhoneInfo();
                int phoneType = cursor.getInt(cursor.getColumnIndex("data2"));
                cpi.setKind(context.getString(Phone.getTypeLabelResource(phoneType)));
                if (phoneType == 2) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 1) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 3) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 4) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 5) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 6) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 8) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 10) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 9) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 11) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 12) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 14) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 15) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 16) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 17) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 18) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 19) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (phoneType == 20) {
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                contactsInfo.getPhone().add(cpi);
            }
            if ("vnd.android.cursor.item/email_v2".equals(mimetype)) {
                ContactsEmailInfo contactsEmailInfo = new ContactsEmailInfo();
                int emailType = cursor.getInt(cursor.getColumnIndex("data2"));
                contactsEmailInfo.setKind(context.getString(Email.getTypeLabelResource(emailType)));
                if (emailType == 0) {
                    contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                } else if (emailType == 1) {
                    contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (emailType == 0) {
                    contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                } else if (emailType == 2) {
                    contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (emailType == 0) {
                    contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                } else if (emailType == 4) {
                    contactsEmailInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                contactsInfo.getEmail().add(contactsEmailInfo);
            }
            if ("vnd.android.cursor.item/contact_event".equals(mimetype)) {
                ContactsDateInfo contactsEventInfo = new ContactsDateInfo();
                int eventType = cursor.getInt(cursor.getColumnIndex("data2"));
                contactsEventInfo.setKind(context.getString(Event.getTypeResource(Integer.valueOf(eventType))));
                if (eventType == 3) {
                    contactsEventInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (eventType == 1) {
                    contactsEventInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                contactsInfo.getDate().add(contactsEventInfo);
            }
            if ("vnd.android.cursor.item/im".equals(mimetype)) {
                ContactsIMInfo contactsIMInfo = new ContactsIMInfo();
                int protocal = cursor.getInt(cursor.getColumnIndex("data5"));
                int columnIndex = cursor.getColumnIndex("data2");
                contactsIMInfo.setKind(context.getString(Im.getTypeLabelResource(cursor.getInt(columnIndex))));
                if (protocal == 0) {
                    contactsIMInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                } else if (1 == protocal) {
                    contactsIMInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if (4 == protocal) {
                    contactsIMInfo.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                }
                contactsInfo.getIm().add(contactsIMInfo);
            }
            if ("vnd.android.cursor.item/note".equals(mimetype)) {
                contactsInfo.setNote(cursor.getString(cursor.getColumnIndex("data1")));
            }
            if ("vnd.android.cursor.item/nickname".equals(mimetype)) {
                contactsInfo.setNote(cursor.getString(cursor.getColumnIndex("data1")));
            }
            if ("vnd.android.cursor.item/postal-address_v2".equals(mimetype)) {
                ContactsAddressInfo contactsAddressInfo = new ContactsAddressInfo();
                int postalType = cursor.getInt(cursor.getColumnIndex("data2"));
                if (postalType == 2) {
                    String street = cursor.getString(cursor.getColumnIndex("data4"));
                    String city = cursor.getString(cursor.getColumnIndex("data7"));
                    String box = cursor.getString(cursor.getColumnIndex("data5"));
                    String area = cursor.getString(cursor.getColumnIndex("data6"));
                    String state = cursor.getString(cursor.getColumnIndex("data8"));
                    String zip = cursor.getString(cursor.getColumnIndex("data9"));
                    String country = cursor.getString(cursor.getColumnIndex("data10"));
                    contactsAddressInfo.setCity(city);
                    contactsAddressInfo.setCy(country);
                    contactsAddressInfo.setPc(zip);
                    contactsAddressInfo.setPro(state);
                }
                if (postalType == 1) {
                    String homeStreet = cursor.getString(cursor.getColumnIndex("data4"));
                    String homeCity = cursor.getString(cursor.getColumnIndex("data7"));
                    String homeBox = cursor.getString(cursor.getColumnIndex("data5"));
                    String homeArea = cursor.getString(cursor.getColumnIndex("data6"));
                    String homeState = cursor.getString(cursor.getColumnIndex("data8"));
                    String homeZip = cursor.getString(cursor.getColumnIndex("data9"));
                    String homeCountry = cursor.getString(cursor.getColumnIndex("data10"));
                    contactsAddressInfo.setCity(homeCity);
                    contactsAddressInfo.setCy(homeCountry);
                    contactsAddressInfo.setPc(homeZip);
                    contactsAddressInfo.setPro(homeState);
                }
                if (postalType == 3) {
                    String otherStreet = cursor.getString(cursor.getColumnIndex("data4"));
                    String otherCity = cursor.getString(cursor.getColumnIndex("data7"));
                    String otherBox = cursor.getString(cursor.getColumnIndex("data5"));
                    String otherArea = cursor.getString(cursor.getColumnIndex("data6"));
                    String otherState = cursor.getString(cursor.getColumnIndex("data8"));
                    String otherZip = cursor.getString(cursor.getColumnIndex("data9"));
                    String otherCountry = cursor.getString(cursor.getColumnIndex("data10"));
                    contactsAddressInfo.setCity(otherCity);
                    contactsAddressInfo.setCy(otherCountry);
                    contactsAddressInfo.setPc(otherZip);
                    contactsAddressInfo.setPro(otherState);
                }
                contactsInfo.getAdd().add(contactsAddressInfo);
            }
            contactsHashMap.put(contactId, contactsInfo);
        }
        Editor editor = context.getSharedPreferences("local_version", 0).edit();
        for (Entry<String, ContactsUserInfo> info : contactsHashMap.entrySet()) {
//            int local_version = ((Integer) SpUtils.getFromLocal(context, "local_version", ((ContactsUserInfo) info.getValue()).getLocal_id(), Integer.valueOf(-1))).intValue();
//            if (local_version == -1) {
//                userList.add((ContactsUserInfo) info.getValue());
//                editor.putInt(((ContactsUserInfo) info.getValue()).getLocal_id(), ((ContactsUserInfo) info.getValue()).getVersion());
//            } else if (((ContactsUserInfo) info.getValue()).getVersion() > local_version) {
//                userList.add((ContactsUserInfo) info.getValue());
//                editor.putInt(((ContactsUserInfo) info.getValue()).getLocal_id(), ((ContactsUserInfo) info.getValue()).getVersion());
//            }
            editor.commit();
        }
        if (!(cursor == null || cursor.isClosed())) {
            try {
                cursor.close();
            } catch (Exception e2) {
            }
        }
//        DebugUtils.error("\u65f6\u95f4\u5dee\u503c2:" + (Long.valueOf(TimeUtils.getNow().getTime()).longValue() - start_time.longValue()));
//        DebugUtils.error(new Gson().toJson((Object) userList));
        return userList;
    }

    public static String switchNumber(List<ContactsPhoneInfo> phoneList, List<ContactsLocalInfo> localList) {
        //String number = BuildConfig.FLAVOR;
        String number = "";
        for (int i = 0; i < phoneList.size(); i++) {
            int relation = -1;
            number = ((ContactsPhoneInfo) phoneList.get(i)).getDetail();
            int j = 0;
            while (i < localList.size()) {
                ContactsLocalInfo cli = (ContactsLocalInfo) localList.get(0);
                if (cli != null && cli.getPhonenumber() != null && number.trim().equals(cli.getPhonenumber().trim()) && Integer.parseInt(cli.getRelation()) > relation) {
                    relation = Integer.parseInt(cli.getRelation());
                    number = cli.getPhonenumber();
                }
                j++;
            }
        }
        return number;
    }

    public static List<ContactsUserInfo> getLocalContacts(Context context) {
        //DebugUtils.error("\u4f18\u5316\u540e\u65b9\u6cd5--start");
//        Long start_time = Long.valueOf(TimeUtils.getNow().getTime());
        List<ContactsUserInfo> userList = new ArrayList();
//        String mimetype = BuildConfig.FLAVOR;
        String mimetype = "";
        HashMap<String, ContactsUserInfo> contactsHashMap = new HashMap();
        Cursor cursor = context.getContentResolver().query(Data.CONTENT_URI, null, null, null, "raw_contact_id");
        List<ContactsLocalInfo> all = new ContactsDao(context).getAllLocalInfo(getUDID(context), MyApplication.getInstance().getUserinfo().getUid());
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                mimetype = cursor.getString(cursor.getColumnIndex("mimetype"));
                String contactId = cursor.getString(cursor.getColumnIndex("raw_contact_id"));
                ContactsUserInfo contactsInfo = (ContactsUserInfo) contactsHashMap.get(contactId);
                if (contactsInfo == null) {
                    contactsInfo = new ContactsUserInfo();
                    contactsInfo.setPhone(new ArrayList());
                }
                contactsInfo.setLocal_id(cursor.getString(cursor.getColumnIndex("_id")));
                if ("vnd.android.cursor.item/name".equals(mimetype)) {
                    contactsInfo.setName(cursor.getString(cursor.getColumnIndex("data1")));
                }
                if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                    ContactsPhoneInfo cpi = new ContactsPhoneInfo();
                    cpi.setKind(context.getString(Phone.getTypeLabelResource(cursor.getInt(cursor.getColumnIndex("data2")))));
                    cpi.setDetail(cursor.getString(cursor.getColumnIndex("data1")));
                    contactsInfo.getPhone().add(cpi);
                }
                contactsHashMap.put(contactId, contactsInfo);
            }
        }
        for (Entry<String, ContactsUserInfo> info : contactsHashMap.entrySet()) {
            ((ContactsUserInfo) info.getValue()).setPhonenumber(switchNumber(((ContactsUserInfo) info.getValue()).getPhone(), all));
            userList.add((ContactsUserInfo) info.getValue());
        }
        if (!(cursor == null || cursor.isClosed())) {
            try {
                cursor.close();
            } catch (Exception e) {
            }
        }
        //DebugUtils.error("\u65f6\u95f4\u5dee\u503c:" + (Long.valueOf(TimeUtils.getNow().getTime()).longValue() - start_time.longValue()));
        return userList;
    }

    public static List<ContactsUserInfo> readContacts2(Context context) {
        //Long start_time = Long.valueOf(TimeUtils.getNow().getTime());
        List<ContactsUserInfo> groupmembers = new ArrayList();
        Cursor phone = context.getContentResolver().query(Phone.CONTENT_URI, new String[]{"_id", "contact_id", "display_name", "data1", "data1"}, null, null, null);
        List<ContactsLocalInfo> all = new ContactsDao(context).getAllLocalInfo(getUDID(context), MyApplication.getInstance().getUserinfo().getUid());
        while (phone != null && phone.moveToNext()) {
            ContactsUserInfo member = new ContactsUserInfo();
            String contactId = phone.getString(phone.getColumnIndex("_id"));
            String displayName = phone.getString(phone.getColumnIndex("display_name"));
            String PhoneNumber = phone.getString(phone.getColumnIndex("data1"));
            int i = 0;
            while (i < all.size()) {
                ContactsLocalInfo cli = (ContactsLocalInfo) all.get(i);
//                if (cli.getPhonenumber() != null && PhoneNumber.replace(" ", BuildConfig.FLAVOR).equals(cli.getPhonenumber().replace(" ", BuildConfig.FLAVOR)) && my_tutu_id.equals(cli.getMy_tutu_id())) {
//                    String relation = cli.getRelation();
//                    String tutuid = cli.getTutuid();
//                    if (relation == null || member.getRelation() == null) {
//                        member.setRelation(relation);
//                        member.setTutuid(tutuid);
//                        if (!(PhoneNumber == null || PhoneNumber.trim().equals(BuildConfig.FLAVOR))) {
//                            member.setPhonenumber(PhoneNumber.replace(" ", BuildConfig.FLAVOR));
//                        }
//                        member.setName(displayName);
//                        member.setLocal_id(contactId);
//                        member.setHead(getHeadByID(context, contactId));
//                        groupmembers.add(member);
//                    } else {
//                        if (Integer.parseInt(relation) > Integer.parseInt(member.getRelation())) {
//                            member.setRelation(relation);
//                            member.setTutuid(tutuid);
//                        }
//                        member.setPhonenumber(PhoneNumber.replace(" ", BuildConfig.FLAVOR));
//                        member.setName(displayName);
//                        member.setLocal_id(contactId);
//                        member.setHead(getHeadByID(context, contactId));
//                        groupmembers.add(member);
//                    }
//                } else {
//                    i++;
//                }
            }
            //member.setPhonenumber(PhoneNumber.replace(" ", BuildConfig.FLAVOR));
            member.setName(displayName);
            member.setLocal_id(contactId);
            member.setHead(getHeadByID(context, contactId));
            groupmembers.add(member);
        }
        if (!(phone == null || phone.isClosed())) {
            phone.close();
        }
        //DebugUtils.error("\u65f6\u95f4\u5dee\u503c2:" + (Long.valueOf(TimeUtils.getNow().getTime()).longValue() - start_time.longValue()));
        return groupmembers;
    }

    public static String getHeadByID(Context context, String localid) {
//        if (localid == null || BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
//            return null;
//        }
        return ContentUris.withAppendedId(Contacts.CONTENT_URI, Long.parseLong(localid)).toString();
    }

    public static String getUDID(Context context) {
        if (uuid == null && uuid == null) {
            String androidId = Secure.getString(context.getContentResolver(), "android_id");
            try {
                if ("9774d56d682e549c".equals(androidId)) {
                    String uuid;
                    String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    if (deviceId != null) {
                        uuid = UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString();
                    } else {
                        uuid = UUID.randomUUID().toString();
                    }
                    uuid = uuid;
                } else {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return uuid;
    }
}
