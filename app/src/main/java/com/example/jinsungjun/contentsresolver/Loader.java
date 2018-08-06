package com.example.jinsungjun.contentsresolver;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class Loader {

    public static List<Contacts> getData(Context context) {

        List<Contacts> data = new ArrayList<>();

        //Contents Resolver를 사용하는 단계

        //1. Content Resolver를 불러온다
        ContentResolver resolver = context.getContentResolver();

        //2. 데이터 컨텐츠 URI 정의 ->
        //컨텐츠 제공자측에서 미리 만들어놓은
        //주소를 URI 형태로 정의

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        //3. 가져올 데이터 컬럼명들을 정의

        String projections[] = {

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,

        };

        //4. Content Resolver로 데이터 요청 -> Query
        Cursor cursor = resolver.query(uri, projections, null, null, null);

        //5. Query에 담긴 데이터를 반복문을 통해 꺼낸다

        if (cursor != null) {

            while (cursor.moveToNext()) {

                Contacts contacts = new Contacts();

                //5.1 컬럼 인덱스를 먼저 가져온다
                int index = cursor.getColumnIndex(projections[0]);
                //5.2 컬럼 인덱스로 값을 가져온다
                String id = cursor.getString(index);
                contacts.id = id;

                index = cursor.getColumnIndex(projections[1]);
                String name = cursor.getString(index);
                contacts.name = name;

                index = cursor.getColumnIndex(projections[2]);
                String number = cursor.getString(index);
                contacts.number = number;

                data.add(contacts);
            }
        }
        return data;
    }
}
