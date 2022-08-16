package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.adapter.ProductAdapter;
import com.example.quanlybanhang.dao.ProductDAO;
import com.example.quanlybanhang.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
RecyclerView rvProduct;
FloatingActionButton floatAdd;

private DatabaseReference mDatabase;
 List listProduct;
 ProductDAO dao;

private Dialog dialog;
private TextView tv_message;
private Button btnCancel,btnSave;
private EditText edTen,edGia,edMota,edAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mDatabase = FirebaseDatabase.getInstance().getReference("products");
        listProduct= new ArrayList();


        rvProduct=findViewById(R.id.rvProduct);
        floatAdd=findViewById(R.id.floatAdd);

        listProduct =new ArrayList();
        dao= new ProductDAO();
        dao.read(mDatabase,listProduct,this,rvProduct);

        floatAdd.setOnClickListener(view ->{
            openDialog(this);
        });

    }
    private  void openDialog(final Context contex){

        dialog=new Dialog(contex);
        dialog.setContentView(R.layout.product_add_dialog);
        tv_message =dialog.findViewById(R.id.tv_message);
        edTen=dialog.findViewById(R.id.edTen);
        edGia=dialog.findViewById(R.id.edGia);
        edMota=dialog.findViewById(R.id.edMota);
        edAvatar=dialog.findViewById(R.id.edAvatar);
        btnCancel=dialog.findViewById(R.id.btnCancel);
        btnSave=dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> {
            String ten = edTen.getText().toString();
            String gia = edGia.getText().toString();
            String mota = edMota.getText().toString();
            String avatar = edAvatar.getText().toString();

            Product product = new Product(ten,gia,mota,avatar);
            if (TextUtils.isEmpty(ten)||TextUtils.isEmpty(gia)||
                    TextUtils.isEmpty(mota)|| TextUtils.isEmpty(avatar)){
               tv_message.setVisibility(view.VISIBLE);
               tv_message.setText(" Không được để trống ");
            }else{
                dao.add(mDatabase,ten,gia,mota,avatar);
                Toast.makeText(contex, "Them thanh cong", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

        });

        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }


}