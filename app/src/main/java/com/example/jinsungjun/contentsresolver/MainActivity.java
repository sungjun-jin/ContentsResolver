package com.example.jinsungjun.contentsresolver;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //내가 사용할 주소데이터를 저장하는 저장소

    List<Contacts> data;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if 권한처리 Ok
        //init();
        //else
        //권한요청

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

            //마시멜로 이상에서 위험권한 처리

            permission_check();

        } else {

            init();
        }


    }

    public static final int REQ_PERM = 1;

    @TargetApi(Build.VERSION_CODES.M)
    private void permission_check() {

        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            String[] permArr = {

                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CONTACTS
            };

            requestPermissions(permArr, REQ_PERM);

        } else {

            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_PERM) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //둘다 조건에 대한 만족
                init();
            } else {
                Toast.makeText(this, "권한을 허용하시지 않으면 프로그램을 실행할 수 없습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void init() {

        //1. 데이터를 로딩
        data = Loader.getData(this);

        //2. 리사이클러 뷰 화면 연결
        recyclerView = findViewById(R.id.recyclerView);

        //3. 아답터 생성 및 아답터에 데이터 세팅
        Adapter adapter = new Adapter();
        adapter.setData(data);

        //4. 리사이클러 뷰와 아답터 연결
        recyclerView.setAdapter(adapter);
        //5. 레이아웃 매니저 연결
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
