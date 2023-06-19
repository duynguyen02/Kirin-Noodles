package bomoncntt.svk62.mssv2051067158.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import bomoncntt.svk62.mssv2051067158.databinding.DialogFoodOrderDetailBinding;
import bomoncntt.svk62.mssv2051067158.databinding.ListviewItemFoodOrderBinding;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.models.Order;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;
import bomoncntt.svk62.mssv2051067158.utils.CurrencyHelper;
import bomoncntt.svk62.mssv2051067158.utils.ViewHelper;

public class FoodsOrderedAdapter extends BaseAdapter {
    private ListviewItemFoodOrderBinding binding;
    private List<Dish> dishes;
    private Order order;
    private FragmentManager fragmentManager;
    private FragmentActivity fragmentActivity;
    List<Boolean> isChecked;

    public FoodsOrderedAdapter(FragmentActivity fragmentActivity, Order order, List<Dish> dishes) {
        this.fragmentActivity = fragmentActivity;
        this.order = order;
        this.dishes = dishes;
        this.fragmentManager = fragmentActivity.getSupportFragmentManager();
        this.isChecked = new ArrayList<>();
        for (int i = 0; i < dishes.size(); i++) {
            isChecked.add(false);
        }
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public int getCount() {
        return dishes.size();
    }

    @Override
    public Object getItem(int position) {
        return dishes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        binding = ListviewItemFoodOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        Dish _dish = dishes.get(position);

        binding.tvFoodLviFoodName.setText(_dish.getDishName());
        binding.tvFoodLviPrice.setText(CurrencyHelper.currencyConverter(_dish.getPrice()));
        ViewHelper.setImageForImageViewFromFile(binding.ivFoodLviFood, _dish.getImageLocation());

        binding.cbFoodLviChecked.setChecked(isChecked.get(position));

        binding.getRoot().setOnClickListener(v -> new FoodOrderDetailDialog(_dish,FoodsOrderedAdapter.this).show(fragmentManager,"FoodOrderDetailDialog"));

        return binding.getRoot();
    }

    public static class FoodOrderDetailDialog extends DialogFragment implements TextWatcher{
        private DialogFoodOrderDetailBinding binding;
        private final Dish dish;
        private final FoodsOrderedAdapter foodsOrderedAdapter;

        public FoodOrderDetailDialog(Dish dish, FoodsOrderedAdapter foodsOrderedAdapter){
            this.dish = dish;
            this.foodsOrderedAdapter = foodsOrderedAdapter;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            binding = DialogFoodOrderDetailBinding.inflate(getLayoutInflater());

            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
            builder.setView(binding.getRoot());

            viewsSetup();

            return builder.create();
        }

        private void viewsSetup() {
            binding.tvFoodOrderDetailDFoodName.setText(dish.getDishName());
            if(foodsOrderedAdapter.order.getOrderMap().containsKey(dish)){
                OrderedDish orderedDish = foodsOrderedAdapter.order.getOrderMap().get(dish);
                binding.tvFoodOrderDetailDPrice.setText(CurrencyHelper.currencyConverter((long) orderedDish.getQuantity() * dish.getPrice()));
                binding.etFoodOrderDetailDQuantity.setText(String.valueOf(orderedDish.getQuantity()));
                binding.etFoodOrderDetailDNote.setText(orderedDish.getNote());
            }
            else {
                binding.tvFoodOrderDetailDPrice.setText(CurrencyHelper.currencyConverter(dish.getPrice()));
                binding.etFoodOrderDetailDQuantity.setText("1");
            }
            binding.etFoodOrderDetailDQuantity.addTextChangedListener(this);
            binding.btnFoodOrderDetailDAdd.setOnClickListener(v -> addOrderedDish());
            binding.btnFoodOrderDetailDRemove.setOnClickListener(v -> removeOrderedDish());
        }

        private void addOrderedDish() {
            String quantity = binding.etFoodOrderDetailDQuantity.getText().toString().trim();
            String note = binding.etFoodOrderDetailDNote.getText().toString();

            if(quantity.isEmpty()){
                Toast.makeText(foodsOrderedAdapter.fragmentActivity, "Vui Lòng Nhập Số Lượng", Toast.LENGTH_SHORT).show();
                return;
            }

            foodsOrderedAdapter.order.getOrderMap().put(dish, new OrderedDish(
                    dish.getDishID(), Integer.parseInt(quantity), note, -1, dish.getPrice()
            ));

            foodsOrderedAdapter.isChecked.set(foodsOrderedAdapter.dishes.indexOf(dish), true);
            foodsOrderedAdapter.notifyDataSetChanged();
            FoodOrderDetailDialog.this.dismiss();
        }

        private void removeOrderedDish() {
            foodsOrderedAdapter.order.getOrderMap().remove(dish);
            foodsOrderedAdapter.isChecked.set(foodsOrderedAdapter.dishes.indexOf(dish), false);
            foodsOrderedAdapter.notifyDataSetChanged();
            FoodOrderDetailDialog.this.dismiss();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length()!=0){
                binding.tvFoodOrderDetailDPrice.setText(
                        CurrencyHelper.currencyConverter(
                                Long.parseLong(s.toString()) * dish.getPrice()
                        )
                );
            }
            else {
                binding.tvFoodOrderDetailDPrice.setText("0");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
