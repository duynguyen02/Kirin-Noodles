package bomoncntt.svk62.mssv2051067158.presentation.main.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.data.local.repository.KirinNoodlesRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.OrderRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.databinding.FragmentOrderBinding;
import bomoncntt.svk62.mssv2051067158.domain.models.Order;
import bomoncntt.svk62.mssv2051067158.domain.repository.DishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.InvoiceRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.KirinNoodlesRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderedDishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.TableLocationRepository;
import bomoncntt.svk62.mssv2051067158.presentation.main.adapters.OrderAdapter;

public class OrderFragment extends Fragment {

    private FragmentOrderBinding binding;
    private OrderRepository orderRepository;
    private FragmentActivity fragmentActivity;
    private List<Order> orders;
    private OrderAdapter orderAdapter;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in);
        } else {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out);
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentActivity = requireActivity();
        orderRepository = OrderRepositoryImpl.getInstance(KirinNoodlesSQLiteHelper.getInstance(fragmentActivity));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fabOrderFAddOrder.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_a_fragments_holder, new AddOrderFragment()).commit());

        toolbarSetup();
        listviewSetup();

    }

    private void listviewSetup() {
        orders = orderRepository.getAllOrders();
        if(orders.isEmpty()){
            binding.group.setVisibility(View.VISIBLE);
        }
        else {
            binding.group.setVisibility(View.GONE);
        }
        orderAdapter = new OrderAdapter(fragmentActivity, orders, binding.flOrderFLoading);
        binding.lvOrderFOrders.setAdapter(orderAdapter);
    }

    private void toolbarSetup() {
        binding.tbOrderF.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.menu_order_delete_order) {
                openConfirmDeleteOrdersDialog();
            }

            return true;
        });
    }

    private void openConfirmDeleteOrdersDialog() {
        List<Order> _orders = orderAdapter.getCheckedOrders();
        if(_orders.isEmpty()){
            Toast.makeText(fragmentActivity, "Vui lòng chọn đơn hàng để xóa!", Toast.LENGTH_SHORT).show();
            return;
        }

        new MaterialAlertDialogBuilder(fragmentActivity)
                .setMessage("Bạn có chắc muốn xóa đơn hàng?")
                .setTitle("Thông Báo")
                .setPositiveButton("Xác Nhận", (dialog, which) -> {
                    deleteOrders(_orders);
                    listviewSetup();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void deleteOrders(List<Order> orders) {
        for (Order order : orders){
            orderRepository.deleteOrder(order);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
