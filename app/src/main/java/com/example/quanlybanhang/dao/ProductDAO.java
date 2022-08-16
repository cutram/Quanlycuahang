package com.example.quanlybanhang.dao;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlybanhang.adapter.ProductAdapter;
import com.example.quanlybanhang.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductDAO {



    String msg="";


  public void add(DatabaseReference mDatabase,String name, String price, String mota,String avatar){

      //tao key
      String id= mDatabase.push().getKey();
      // tao doi tuong truyen
      Product p =  new Product(id,name,price,mota,avatar);
      //truyen value cho key
      mDatabase.child(id).setValue(p)
              //thong bao tra ve
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()){
                      msg= " Thêm thành công";
                     }else{
                         msg= "Thêm thất bại";
                     }
                  }
              });
      Log.d("=//",msg);
  }
  public void update(DatabaseReference mDatabase,String id,String name, String price, String mota,String avatar){
      mDatabase.child(id).setValue(new Product(id,name,price,mota,avatar))
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful()){
                          msg= " Cập nhật thành công";
                      }else{
                          msg= "Cập nhật thất bại";
                      }
                  }
              });
      Log.d("=//",msg);

  }
  public String delete(DatabaseReference mDatabase,String id){
      mDatabase.child(id).setValue(null)
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful()){
                          msg= " Xóa thành công ";
                      }else{
                          msg= " Xóa thất bại ";
                      }
                  }
              });
      return msg;
}
  public List read(DatabaseReference mDatabase, List listProduct,
                     Context context , RecyclerView rvProduct){
      mDatabase.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
               listProduct.clear();
               for(DataSnapshot data: snapshot.getChildren()){
                   listProduct.add(data.getValue(Product.class));
               }
               RecyclerView.LayoutManager manager = new GridLayoutManager(context,2);
               rvProduct.setLayoutManager(manager);
               //hien thi du lieu tu adapter len rv
              ProductAdapter adapter = new ProductAdapter(listProduct,context);
              rvProduct.setAdapter(adapter);

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }

      });
      return listProduct;
  }
}
