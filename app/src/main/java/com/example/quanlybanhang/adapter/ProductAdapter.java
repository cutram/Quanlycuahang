package com.example.quanlybanhang.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.dao.ProductDAO;
import com.example.quanlybanhang.model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductVH> {

    List<Product>list;
    Context context;

    //thao tac lenh va du lieu
    ProductDAO dao;
    private DatabaseReference mDatabase;

    String strId,strName,strPrice,strMota,strAvatar;

    private TextView tvTen,tvGia,tvMota,tvTitle;
    private ImageView img;
    Button btnDelete,btnUpdate;



    public ProductAdapter(List<Product> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new ProductDAO();
        mDatabase = FirebaseDatabase.getInstance().getReference("products");
    }

    @NonNull
    @Override
    public ProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);

        return new ProductAdapter.ProductVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVH holder, int position) {
     Product product= list.get(position);

     //neu ko co du lieu
        if (product ==null) return;
      //neu co du lieu
        holder.ten.setText(product.getName());
        holder.gia.setText(product.getPrice());
        //load anh
        Picasso.get().load(product.getAvatar()).into(holder.imgArt);
        //hien thi chi tiet khi click vao item
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strId= product.getId();
                strName=product.getName();
                strPrice=product.getPrice();
                strMota=product.getMota();
                strAvatar=product.getAvatar();
                Log.d("//==Detail:",strName+" | "+strPrice);
            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.product_detail_dialog);
            dialog.setCanceledOnTouchOutside(true);
            tvTen=dialog.findViewById(R.id.tvName_detail);
            tvGia=dialog.findViewById(R.id.tvPrice_detail);
            tvMota=dialog.findViewById(R.id.tvDes_detail);
            img=dialog.findViewById(R.id.img_detail);
            btnDelete=dialog.findViewById(R.id.btnDeleteProduct);
            btnUpdate=dialog.findViewById(R.id.btnUpdateProduct);

            //Hien thi du lieu len detail
                {
                    tvTen.setText(strName);
                    tvGia.setText(strPrice);
                    tvMota.setText(strMota);
                    Picasso.get().load(strAvatar).into(img);
                }
                //Cap nhat va xoa
            btnUpdate.setOnClickListener(view1 ->{
                dialog.dismiss();
                openDialogUpdate(view1.getContext());
            });
            btnDelete.setOnClickListener(view1 ->{
                dialog.dismiss();
                openDialogDelete(view1.getContext());
            });
            dialog.show();
            }

        });
    }
    private void openDialogUpdate(final Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.product_add_dialog);
        // Khi chạm bên ngoài dialog sẽ ko đóng
        dialog.setCanceledOnTouchOutside(false);
        {
            EditText edTen, edGia, edMota, edAvatar;
            TextView tv_message,tvTitle;
            Button btnCancel, btnSave;
            tvTitle =dialog.findViewById(R.id.tvTitle);
            edTen = dialog.findViewById(R.id.edTen);
            edGia = dialog.findViewById(R.id.edGia);
            edMota = dialog.findViewById(R.id.edMota);
            edAvatar = dialog.findViewById(R.id.edAvatar);
            tv_message = dialog.findViewById(R.id.tv_message);
            btnCancel = dialog.findViewById(R.id.btnCancel);
            btnSave = dialog.findViewById(R.id.btnSave);

            //hien thi du lieu len lai khi update
            tvTitle.setText("Cập Nhật Sản Phẩm");
            edTen.setText(strName);
            edGia.setText(strPrice);
            edMota.setText(strMota);
            edAvatar.setText(strAvatar);

            btnSave.setOnClickListener(view -> {
                String id =strId;
                String ten = edTen.getText().toString();
                String gia = edGia.getText().toString();
                String mota = edMota.getText().toString();
                String avatar = edAvatar.getText().toString();

                if (TextUtils.isEmpty(ten) || TextUtils.isEmpty(gia) ||
                        TextUtils.isEmpty(mota) || TextUtils.isEmpty(avatar)) {
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Không được để trống");
                } else {
                    if (ten.equalsIgnoreCase(strName)&& gia.equalsIgnoreCase(strPrice)&&
                    mota.equalsIgnoreCase(strMota)&&strAvatar.equalsIgnoreCase(strAvatar))
                    {
                        tv_message.setVisibility(View.VISIBLE);
                        tv_message.setText("Bạn chưa thay đổi gì");
                        Toast.makeText(context, "Bạn chưa thay đổi gì", Toast.LENGTH_SHORT).show();
                    }else{

                    dao.update(mDatabase, id,ten,gia,mota,avatar);
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                }
            });
            btnCancel.setOnClickListener(view -> {
                dialog.dismiss();
            });
        }
        dialog.show();
    }

    private void openDialogDelete(final  Context context){
    Dialog dialog = new Dialog(context);
    dialog.setContentView(R.layout.delete_dialog);
    dialog.setCanceledOnTouchOutside(false);
        TextView tvDelete;
        Button btnYesDel, btnNoDel;
        tvDelete = dialog.findViewById(R.id.tvTen_Delete);
        btnYesDel = dialog.findViewById(R.id.btnYes);
        btnNoDel = dialog.findViewById(R.id.btnNo);

        tvDelete.setText( strName);

        btnNoDel.setOnClickListener(view -> {
            dialog.cancel();
        });
        btnYesDel.setOnClickListener(view -> {
            dao.delete(mDatabase, strId);
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();

    }
    @Override
    public int getItemCount() {
        if(list !=null){
            return list.size();

    }
        return 0;
    }

    public class ProductVH extends RecyclerView.ViewHolder{
        TextView ten,gia;
        ImageView imgArt;
        CardView detail;
        public ProductVH(View itemView){
            super(itemView);
          detail= itemView.findViewById(R.id.detail);
          ten= itemView.findViewById(R.id.ten);
          gia= itemView.findViewById(R.id.gia);
          imgArt= itemView.findViewById(R.id.imgArt);
        }
    }

}
