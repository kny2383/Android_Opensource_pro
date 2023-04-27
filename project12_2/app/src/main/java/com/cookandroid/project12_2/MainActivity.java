package com.cookandroid.project12_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 위젯 변수 선언
    myDBHelper myHelper;
    EditText edtName, edtNumber, edtNameResult, edtNumberResult;
    Button btnInit, btnInsert, btnSelect;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 제목 설정
        setTitle("가수 그룹 관리 db");

        // 위젯에 접근하기위한 객체 선언
        edtName = (EditText) findViewById(R.id.edtName);
        edtNumber = (EditText) findViewById(R.id.edtNumber);
        edtNameResult = (EditText) findViewById(R.id.etdNameResult);
        edtNumberResult = (EditText) findViewById(R.id.edtNumberResult);
        btnInit = (Button) findViewById(R.id.btnInit);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnSelect = (Button) findViewById(R.id.btnSelect);

        // myDBHelper 인스턴스 생성 > 이 때 myDBHelper()의 생성자가 실행 되면서 groupDB 파일이 생성된다.
        myHelper = new myDBHelper(this);
        // 초기화 버튼 클릭
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getWritableDatabase();                     // groupDB를 write 전용 db로 열기
                myHelper.onUpgrade(sqlDB, 1, 2);      // onUpgrade() 메서드 호출
                sqlDB.close();
            }
        });

        // 입력 버튼 클릭
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getWritableDatabase();                     // groupDB를 write 전용 db로 열기
                sqlDB.execSQL( "INSERT INTO groupTBL VALUES ( '" + edtName.getText().toString() + "', " + edtNumber.getText().toString()+");");     // INSERT QUERY 작성
                sqlDB.close();
                Toast.makeText(getApplicationContext(),"입력됨", 0).show();            // 입력이 성공하면 "입력됨" 이라는 토스트 메시지가 뜸.
            }
        });



    }

    // SQLiteOpenHelper 클래스를 상속받는 myDBHelper 클래스를 정의
    public class myDBHelper extends SQLiteOpenHelper{
        // 생성자 정의, "groupDB"는 새로 생성될 db의 이름, 1 : db version 처음에는 1로 지정
        public myDBHelper(Context context) {
            super(context, "groupDB", null, 1);
        }

        // 추상 메서드 오버라이딩 - onCreate()
        @Override
        public void onCreate(SQLiteDatabase db) {
            // 테이블 생성 쿼리 작성
            db.execSQL( "CREATE TABLE groupTBL (gName CHAR(20) PRIMARY KEY, gNumber INTEGER); ");
        }

        // 추상 메서드 오버라이딩 - onUpgrade
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // 테이블 삭제 후 다시 생성하는 쿼리 (초기화 할 때 호출)
            db.execSQL( "DROP TABLE IF EXISTS groupTBL " );
            onCreate(db);

        }
    }
}