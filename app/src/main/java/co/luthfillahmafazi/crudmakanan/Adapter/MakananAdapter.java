package co.luthfillahmafazi.crudmakanan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananData;
import co.luthfillahmafazi.crudmakanan.R;
import co.luthfillahmafazi.crudmakanan.UI.DetailMakanan.DetailMakananActivity;
import co.luthfillahmafazi.crudmakanan.UI.DetailMakananByUser.DetailMakananByUserActivity;
import co.luthfillahmafazi.crudmakanan.UI.MakananByKategori.MakananByCategoriActivity;
import co.luthfillahmafazi.crudmakanan.UI.UploadMakanan.UploadMakananActivity;
import co.luthfillahmafazi.crudmakanan.Utils.Constant;
import co.luthfillahmafazi.crudmakanan.Utils.StartSnapHelper;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {
    // TYPE 1 untuk makanan baru
    public static final int TYPE_1 = 1;
    // TYPE 2 untuk makanan populer
    public static final int TYPE_2 = 2;
    // TYPE 3 untuk kategory
    public static final int TYPE_3 = 3;
    // TYPE 4 untuk makanan by category
    public static final int TYPE_4 = 4;
    // TYPE 45 untuk makanan by user
    public static final int TYPE_5 = 5;

    Integer viewType;
    private final Context context;
    private final List<MakananData> makananItemList;

    SnapHelper startSnapHelper = new StartSnapHelper();

    public MakananAdapter(Integer viewType, Context context, List<MakananData> makananItemList) {
        this.viewType = viewType;
        this.context = context;
        this.makananItemList = makananItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case TYPE_1:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_news, null);
                return new FoodNewsViewHolder(view);
            case TYPE_2:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_populer, null);
                return new FoodPopulerViewHolder(view);
            case TYPE_3:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_kategori, null);
                return new FoodKategoriViewHolder(view);
            case TYPE_4:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_by_category, null);
                return new FoodNewsViewHolder(view);
            case TYPE_5:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_by_category, null);
                return new FoodByUserViewHolder(view);
            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MakananData makananItem = makananItemList.get(i);

        int mViewType = viewType;
        switch (mViewType) {
            case TYPE_1:
                // membuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder = (FoodNewsViewHolder) viewHolder;
                RequestOptions requestOptions = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananItem.getUrl_makanan()).apply(requestOptions).into(foodNewsViewHolder.imgMakanan);

                foodNewsViewHolder.txtTitle.setText(makananItem.getNama_makanan());
                foodNewsViewHolder.txtView.setText(makananItem.getView());

                foodNewsViewHolder.txtTime.setText(makananItem.getInsert_time());

                foodNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananItem.getId_makanan()));
                    }
                });
                break;

            case TYPE_2:
                FoodPopulerViewHolder foodPopulerViewHolder = (FoodPopulerViewHolder) viewHolder;
                RequestOptions requestOptions1 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananItem.getUrl_makanan()).apply(requestOptions1).into(foodPopulerViewHolder.imgMakanan);

                foodPopulerViewHolder.txtTitle.setText(makananItem.getNama_makanan());
                foodPopulerViewHolder.txtView.setText(makananItem.getView());

                foodPopulerViewHolder.txtTime.setText(makananItem.getInsert_time());


                foodPopulerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananItem.getId_makanan()));
                    }
                });
                break;
            case TYPE_3:
                FoodKategoriViewHolder foodKategoriViewHolder = (FoodKategoriViewHolder) viewHolder;
                RequestOptions requestOptions2 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananItem.getUrl_makanan()).apply(requestOptions2).into(foodKategoriViewHolder.image);

                foodKategoriViewHolder.txtNamaKategory.setText(makananItem.getNama_kategori());
                foodKategoriViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, MakananByCategoriActivity.class).putExtra(Constant.KEY_EXTRA_ID_CATEGORY, makananItem.getId_kategori()));
                    }
                });
                break;
            case TYPE_4:
                FoodNewsViewHolder foodNewsViewHolder2 = (FoodNewsViewHolder) viewHolder;
                RequestOptions requestOptions3 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananItem.getUrl_makanan()).apply(requestOptions3).into(foodNewsViewHolder2.imgMakanan);

                foodNewsViewHolder2.txtTitle.setText(makananItem.getNama_makanan());
                foodNewsViewHolder2.txtView.setText(makananItem.getView());
                foodNewsViewHolder2.txtTime.setText(makananItem.getInsert_time());

                foodNewsViewHolder2.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, MakananByCategoriActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananItem.getId_kategori()));
                    }
                });
                break;
            case TYPE_5:
                FoodByUserViewHolder foodByUserViewHolder = (FoodByUserViewHolder) viewHolder;
                RequestOptions requestOptions4 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananItem.getUrl_makanan()).apply(requestOptions4).into(foodByUserViewHolder.imgMakanan);

                foodByUserViewHolder.txtTitle.setText(makananItem.getNama_makanan());
                foodByUserViewHolder.txtView.setText(makananItem.getView());
                foodByUserViewHolder.txtTime.setText(makananItem.getInsert_time());

                foodByUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananByUserActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananItem.getId_makanan()));
                    }
                });
                break;
        }
    }

    private String newDate(String insert_time) {
        // membuat variable penampung data
        Date date = null;
        // membuat penampung data untuk format yg baru
        String newDate = insert_time;
        // mengubah tanggal yang dimilki menjadi tipe data
        try {
            // membuat date dengan dormat sesuai dengan tanggal yang sudah dimiliki
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(insert_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // kita cek format date yang kita miliki sesuai dengan yang kita inginkan
        if (date != null) {
            // mengubah date yang dimiliki menjadi format data yang baru
            newDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(date);
        }
        return newDate;
    }

    @Override
    public int getItemCount() {
        return makananItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public class FoodNewsViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;

        public FoodNewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FoodPopulerViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;

        public FoodPopulerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FoodKategoriViewHolder extends ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.txt_nama_kategory)
        TextView txtNamaKategory;

        public FoodKategoriViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FoodByUserViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;

        public FoodByUserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
