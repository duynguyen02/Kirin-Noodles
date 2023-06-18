package bomoncntt.svk62.mssv2051067158.presentation.main.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.data.local.repository.DishRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.InvoiceRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.OrderRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.TableLocationRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.factory.LocalRepositoryFactory;
import bomoncntt.svk62.mssv2051067158.databinding.FragmentAddOrderBinding;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.Order;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;
import bomoncntt.svk62.mssv2051067158.domain.repository.DishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.InvoiceRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderedDishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.TableLocationRepository;
import bomoncntt.svk62.mssv2051067158.presentation.main.adapters.FoodsOrderedAdapter;
import bomoncntt.svk62.mssv2051067158.utils.CurrencyHelper;
import bomoncntt.svk62.mssv2051067158.utils.DateHelper;

public class AddOrderFragment  extends Fragment {
    private FragmentAddOrderBinding binding;
    private TableLocationRepository tableLocationRepository;
    private DishRepository dishRepository;
    private InvoiceRepository invoiceRepository;
    private OrderRepository orderRepository;
    private FoodsOrderedAdapter foodsOrderedAdapter;
    private ArrayAdapter<CharSequence> tableLocationsAdapter;
    private List<TableLocation> tableLocations;
    private List<Dish> dishes;
    private Order order;
    private Invoice invoice;
    private FragmentActivity fragmentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getDependenciesInstance();
    }

    private void getDependenciesInstance(){
        fragmentActivity = requireActivity();

        dishRepository = (DishRepository) LocalRepositoryFactory.getInstance(LocalRepositoryFactory.RepositoryType.DISH);
        tableLocationRepository = (TableLocationRepository) LocalRepositoryFactory.getInstance(LocalRepositoryFactory.RepositoryType.TABLE_LOCATION);
        invoiceRepository = (InvoiceRepository) LocalRepositoryFactory.getInstance(LocalRepositoryFactory.RepositoryType.INVOICE);
        orderRepository = (OrderRepository) LocalRepositoryFactory.getInstance(LocalRepositoryFactory.RepositoryType.ORDER);

        int invoiceMaxId = invoiceRepository.getMaxId();
        invoice = new Invoice(invoiceMaxId == -1 ? 0 : invoiceMaxId + 1, 0, new Date(), -1, Invoice.PaymentStatus.PREPARING);
        order = new Order(invoice, null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddOrderBinding.inflate(inflater, container, false);

        binding.sprAddOrderFChooseTable.setEnabled(false);

        binding.cbAddOrderDineIn.setOnCheckedChangeListener((buttonView, isChecked) -> addOrRemoveTableLocationFromOrder(isChecked));

        binding.sprAddOrderFChooseTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addOrRemoveTableLocationFromOrder(binding.cbAddOrderDineIn.isChecked());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        binding.tbAddOrderF.setOnMenuItemClickListener(item -> {
            openOrderFragment();
            return true;
        });

        binding.btnAddOrderFAddOrder.setOnClickListener(v -> addOrderConfirmDialog());

        binding.btnAddOrderFSeeBill.setOnClickListener(v -> showBill());

        tableLocationSpinnerSetup();
        listviewSetup();

        return binding.getRoot();
    }

    private void openOrderFragment() {
        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_a_fragments_holder, new OrderFragment()).commit();
    }

    private void addOrderConfirmDialog() {
        if(isEmptyOrderedDishes()){
            return;
        }

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(fragmentActivity);

        builder.setTitle("Xác nhận đơn")
                .setMessage("Bạn có chắc thêm đơn hàng?")
                .setPositiveButton("Xác Nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addOrder();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss()).create().show();

    }

    private void addOrder() {
        order.getInvoice().setTotal(order.getOrderTotalPrice());
        orderRepository.addOrder(order);
        Toast.makeText(fragmentActivity, "Thêm đơn thành công!", Toast.LENGTH_SHORT).show();
        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_a_fragments_holder, new OrderFragment()).commit();
    }

    private void tableLocationSpinnerSetup() {
        tableLocations = tableLocationRepository.getAllTableLocations();
        tableLocationsAdapter = new ArrayAdapter<>(
                fragmentActivity,
                android.R.layout.simple_spinner_item,
                tableLocations
                        .stream()
                        .map(TableLocation::getTableName)
                        .collect(Collectors.toList())
        );

        tableLocationsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.sprAddOrderFChooseTable.setAdapter(tableLocationsAdapter);
    }

    private void addOrRemoveTableLocationFromOrder(boolean isChecked) {
        binding.sprAddOrderFChooseTable.setEnabled(isChecked);

        if(isChecked && !tableLocations.isEmpty()){
            order.setTableLocation(tableLocations.get(binding.sprAddOrderFChooseTable.getSelectedItemPosition()));
            invoice.setTableID(order.getTableLocation().getTableID());
        }else {
            order.setTableLocation(null);
            invoice.setTableID(-1);
        }
    }

    private void showBill() {
        
        if(isEmptyOrderedDishes()){
            return;
        }

        StringBuilder foodStringBuilder = new StringBuilder();
        for (Map.Entry<Dish, OrderedDish> entry : order.getOrderMap().entrySet()) {
            Dish dish = entry.getKey();
            OrderedDish orderedDish = entry.getValue();

            foodStringBuilder.append(dish.getDishName())
                    .append("\n")
                    .append("Số lượng: ")
                    .append(orderedDish.getQuantity())
                    .append("\n")
                    .append("Giá: ")
                    .append(CurrencyHelper.currencyConverter((double)orderedDish.getQuantity() * dish.getPrice()))
                    .append("\n")
                    .append("Ghi chú: ")
                    .append(orderedDish.getNote())
                    .append("\n\n");
        }


        TableLocation _tableLocation = order.getTableLocation();
        String finalBillString = "Bàn: " +
                (_tableLocation == null ? "Mang Đi" : _tableLocation.getTableName()) + "\n" +
                "Thời gian lên đơn: " +
                DateHelper.dataToStringConverter(invoice.getOrderTime()) +
                "\n\n" +
                foodStringBuilder + "Tổng: " + CurrencyHelper.currencyConverter(order.getOrderTotalPrice());

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle("Chi Tiết Hóa Đơn")
                .setMessage(finalBillString)
                .create()
                .show();
    }

    private boolean isEmptyOrderedDishes() {
        if(order.getOrderMap().size() == 0){
            Toast.makeText(requireContext(), "Vui lòng chọn món!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void listviewSetup() {
        dishes = dishRepository.getAllDishes();
        foodsOrderedAdapter = new FoodsOrderedAdapter(fragmentActivity, order, dishes);
        binding.lvAddOrderFFoods.setAdapter(foodsOrderedAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
