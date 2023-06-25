package com.example.chaserunner2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class adapterForRecViewCharacterDetails extends RecyclerView.Adapter<adapterForRecViewCharacterDetails.ViewHolder> {


    Context context;
    ArrayList<recViewModel> arrCharacterDetail;


    public adapterForRecViewCharacterDetails(ArrayList<recViewModel> arrCharacterDetail, Context context) {
        this.context = context;
        this.arrCharacterDetail = arrCharacterDetail;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.characters_rec_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(arrCharacterDetail.get(position).getImgUrl()).into(holder.characterImage);
        holder.characterNameTv.setText(arrCharacterDetail.get(position).getName());
        holder.characterDescriptionTV.setText(arrCharacterDetail.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return arrCharacterDetail.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView characterNameTv, characterDescriptionTV;
        private final ImageView characterImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            characterNameTv = itemView.findViewById(R.id.characterNameTv);
            characterDescriptionTV = itemView.findViewById(R.id.characterDescriptionTV);
            characterImage = itemView.findViewById(R.id.characterImage);
        }
    }
}
