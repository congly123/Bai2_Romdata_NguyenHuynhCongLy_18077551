package com.example.baitap2_rom_nguyenhuynhcongly_18077551;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private com.example.baitap2_rom_nguyenhuynhcongly_18077551.Adapter adt;
    private RecyclerView rec;
    private Database db;
    private UserDao userDao;
    private TextView tvEdit;
    private int idFlat;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rec = findViewById(R.id.recyView);
        tvEdit = findViewById(R.id.tvSua);
        btnSave = findViewById(R.id.btnLuu);
        btnCancel = findViewById(R.id.btnThoat);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser(new User());
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvEdit.setText("");
                idFlat = 0;
            }
        });

        db = Room.databaseBuilder(getApplicationContext(),
                Database.class, "database-name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        userDao = db.userDao();
 loadDataToList();
    }
    public void loadDataToList(){
        List<User> list = new ArrayList<>();
        list = db.userDao().getAll();
        adt = new com.example.baitap2_rom_nguyenhuynhcongly_18077551.Adapter(list, this);
        rec.setAdapter(adt);
        rec.setLayoutManager(new LinearLayoutManager(this));
    }

    public void xoaUser(User u){
        userDao.delete(u);
        loadDataToList();
    }

    public void setEdit(User u){
        tvEdit.setText(u.getName());
        idFlat = u.getUid();
    }

    public void saveUser(User u){
        if(idFlat == 0){
            userDao.insertAll(new User(tvEdit.getText().toString()));
            loadDataToList();
            tvEdit.setText("");
            idFlat = 0;
            return;
        }
        userDao.update(tvEdit.getText().toString(), idFlat);
        loadDataToList();
        tvEdit.setText("");
        idFlat = 0;
    }
}