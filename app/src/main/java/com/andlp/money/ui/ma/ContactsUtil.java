package com.andlp.money.ui.ma;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import org.xutils.x;

import java.util.Date;


/**
 * 717219917@qq.com      2017/12/18  16:25
 */

public class ContactsUtil {
    //要查询的Phone表字段
    private static final String[] PHONES_PROJECTION = new String[]{
            Phone.DISPLAY_NAME, Phone.NUMBER, ContactsContract.Contacts.Photo.PHOTO_ID, Phone.CONTACT_ID};


    public static void getAll() {
        Cursor phoneCursor = null;
        ContentResolver resolver = null;

        try {
            resolver = x.app().getApplicationContext().getContentResolver(); // 获取手机联系人



           /*
             * query(Uri uri,String[] project,String selection,
             *             String selectionArgs,String sortOrder)方法各参数含义
             * uri:要读取数据对应的Uri
             * projection:需要读取的字段
             * selection:数据检索的条件
             * selectionArgs:数据检索条件的参数
             * sortOrder:对查询结果排序所依据的字段
             * */

            phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    PHONES_PROJECTION, null, null, null);

            if (phoneCursor == null) {
                return;
            }

            if (phoneCursor.getCount() == 0) {
                phoneCursor.close();
                phoneCursor = null;
                return;
            }

            phoneCursor.moveToFirst();
            while (!phoneCursor.isAfterLast()) {
                // contactData必须是在while循环内用new创建出的，若把new操作移到while循环外，将会出现

                //得到对应数据的列索引
                int phonename = phoneCursor.getColumnIndex(Phone.DISPLAY_NAME); // 得到联系人名称
                int phoneNumberColumn = phoneCursor.getColumnIndex(Phone.NUMBER);
                int photoIdColumn = phoneCursor.getColumnIndex(Phone.PHOTO_ID); //得到联系人头像ID
                int contactIdColumn = phoneCursor.getColumnIndex(Phone.CONTACT_ID);// 得到联系人ID


                String phoneNum = phoneCursor.getString(phoneNumberColumn);// 得到手机号码
                String name = phoneCursor.getString(phonename);// 姓名
                String id = phoneCursor.getString(contactIdColumn);// id
                if (TextUtils.isEmpty(phoneNum)) continue;// 当手机号码为空的或者为空字段 跳过当前循环

                //photoId 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的头像
//                if(contactData.getPhotoId() > 0 ) {
//                    Uri uri = ContentUris.withAppendedId( ContactsContract.Contacts.CONTENT_URI,contactData.getContactId());
//                    InputStream input = ContactsContract.Contacts.
//                            openContactPhotoInputStream(resolver, uri);
//                    contactData.setContactPhoto(BitmapFactory.decodeStream(input));
//                }else {
//                    contactData.setContactPhoto(BitmapFactory.decodeResource(
//                            mContext.getResources(), R.drawable.ic_launcher));
//                }

//                contactDataList.add(contactData);//将这一次得到的联系人数据放进联系人数据动态数组ArrayList中
                Logger.i("name:" + name + ",phoneNum:" + phoneNum + ",contactIdColumn:" + id);

                phoneCursor.moveToNext();//Cursor移向下一行
            }

            phoneCursor.close();
            phoneCursor = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (phoneCursor != null) {
                phoneCursor.close();
                phoneCursor = null;
            }
        }

    }

    @SuppressLint("MissingPermission")
    public static void getLog() {
        Cursor cursor = x.app().getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                // 号码
                String number = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.NUMBER));
                // 呼叫类型
                String type;
                switch (Integer.parseInt(cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.TYPE)))) {
                    case CallLog.Calls.INCOMING_TYPE:
                        type = "呼入";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        type = "呼出";
                        break;
                    case CallLog.Calls.MISSED_TYPE:



                        type = "未接";
                        break;
                    default:
                        type = "挂断";// 应该是挂断.根据我手机类型判断出的
                        break;
                }
                SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))));
                // 呼叫时间
                String time = sfd.format(date);
                // 联系人
                String name = cursor.getString(cursor
                        .getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
                // 通话时间,单位:s
                String duration = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));
                Logger.i("num:" + number + ",type:" + type + ",duration:" + duration + ",s:" + time + ",name:" + name);
//                calllogList.add(new Calllog(number, type, duration + "s", time, name));
            } while (cursor.moveToNext());

        }
    }
}


