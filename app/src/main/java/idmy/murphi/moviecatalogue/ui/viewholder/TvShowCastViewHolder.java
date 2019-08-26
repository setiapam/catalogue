package idmy.murphi.moviecatalogue.ui.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.data.local.model.TvShowCast;
import idmy.murphi.moviecatalogue.databinding.ItemCastBinding;
import idmy.murphi.moviecatalogue.utils.Constants;
import idmy.murphi.moviecatalogue.utils.GlideApp;

public class TvShowCastViewHolder extends RecyclerView.ViewHolder {

    private ItemCastBinding binding;

    private Context context;

    private TvShowCastViewHolder(@NonNull ItemCastBinding binding, Context context) {
        super(binding.getRoot());

        this.binding = binding;
        this.context = context;
    }

    public void bindTo(final TvShowCast cast) {
        String profileImage =
                Constants.IMAGE_URL_PREFIX + Constants.IMAGE_SIZE_W185 + cast.getProfileImagePath();
        GlideApp.with(context)
                .load(profileImage)
                .placeholder(R.color.md_grey_200)
                .dontAnimate()
                .into(binding.imageCastProfile);

        binding.textCastName.setText(cast.getActorName());

        binding.executePendingBindings();
    }

    public static TvShowCastViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCastBinding binding =
                ItemCastBinding.inflate(layoutInflater, parent, false);
        return new TvShowCastViewHolder(binding, parent.getContext());
    }
}
