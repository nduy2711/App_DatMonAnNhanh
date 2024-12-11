package com.example.nhom10.View;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nhom10.Control.DatabaseHandler;
import com.example.nhom10.Control.LoginHandler;
import com.example.nhom10.R;

public class Login_Activity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    LoginHandler loginHandler;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        loginHandler = new LoginHandler(this);
        DatabaseHandler db = new DatabaseHandler(this);
        db.open();
        db.initData();

//        loginHandler = new LoginHandler(this, DB_NAME, null, DB_VERSION);
//        loginHandler.onCreate(sqLiteDatabase);
//        loginHandler.initData();

        addEvents();

    }

    void addControls() {
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btn_Login);
    }

    void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (loginHandler.checkLogin(username, password)) {
                    Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login_Activity.this, Item_Activity.class);
                    intent.putExtra("load_order_fragment", true); // Set flag to load order fragment
                    startActivity(intent);
                } else {
                    Toast.makeText(Login_Activity.this, "Tên tài khoản hoac mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}