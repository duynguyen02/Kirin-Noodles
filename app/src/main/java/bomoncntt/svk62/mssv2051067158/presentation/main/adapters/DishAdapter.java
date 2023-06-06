package bomoncntt.svk62.mssv2051067158.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bomoncntt.svk62.mssv2051067158.databinding.ListviewItemFoodBinding;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.repository.DishRepository;
import bomoncntt.svk62.mssv2051067158.presentation.main.fragments.FoodManagerDialogFragment;
import bomoncntt.svk62.mssv2051067158.utils.CurrencyHelper;
import bomoncntt.svk62.mssv2051067158.utils.OnItemChangedListener;
import bomoncntt.svk62.mssv2051067158.utils.ViewHelper;

public class DishAdapter extends ArrayAdapter<Dish> {
    private final Map<String, Dish> checkedItems = new HashMap<>();
    private ListviewItemFoodBinding binding;
    private FragmentManager fragmentManager;
    private DishRepository dishRepository;

    public DishAdapter(@NonNull Context context, List<Dish> dishes, FragmentManager fragmentManager, DishRepository dishRepository) {
        super(context, 0, dishes);
        this.fragmentManager = fragmentManager;
        this.dishRepository = dishRepository;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        binding = ListviewItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        Dish _dish = getItem(position);

        binding.tvFoodLviFoodName.setText(_dish.getDishName());
        binding.tvFoodLviFoodId.setText(Integer.toString(_dish.getDishID()));
        binding.tvFoodLviPrice.setText(CurrencyHelper.currencyConverter(_dish.getPrice()));
        ViewHelper.setImageForImageViewFromFile(binding.ivFoodLviFood, _dish.getImageLocation());

        binding.cbFoodLviChecked.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                checkedItems.put(Integer.toString(position), getItem(position));
            }
            else {
                checkedItems.remove(Integer.toString(position));
            }
        });

        binding.getRoot().setOnClickListener(v -> new FoodManagerDialogFragment(new OnItemChangedListener<Dish>() {
            @Override
            public void onItemAdded(Dish dish) {
                _dish.setDishName(dish.getDishName());
                _dish.setPrice(dish.getPrice());
                _dish.setImageLocation(dish.getImageLocation());
                notifyDataSetChanged();
            }

            @Override
            public void onError() {

            }
        }, _dish).show(fragmentManager, "FoodManagerDialogFragment"));

        return binding.getRoot();
    }

    public List<Dish> getChecked(){
        return new ArrayList<>(checkedItems.values());
    }

}
