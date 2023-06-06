package bomoncntt.svk62.mssv2051067158.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.data.local.repository.InvoiceRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.databinding.ListviewItemOrderBinding;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.Order;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;
import bomoncntt.svk62.mssv2051067158.domain.repository.InvoiceRepository;
import bomoncntt.svk62.mssv2051067158.utils.BillGenerator;
import bomoncntt.svk62.mssv2051067158.utils.CurrencyHelper;
import bomoncntt.svk62.mssv2051067158.utils.DateHelper;

public class OrderAdapter extends BaseAdapter {
    private ListviewItemOrderBinding binding;
    private List<Order> orderList;
    private FragmentActivity fragmentActivity;
    private Set<Order> checkedOrders = new HashSet<>();
    private InvoiceRepository invoiceRepository;
    private View loadingView;

    public OrderAdapter(FragmentActivity fragmentActivity, List<Order> orderList, View loadingView) {
        this.orderList = orderList;
        this.fragmentActivity = fragmentActivity;
        this.invoiceRepository = InvoiceRepositoryImpl.getInstance(KirinNoodlesSQLiteHelper.getInstance(fragmentActivity));
        this.loadingView = loadingView;
    }

    public List<Order> getCheckedOrders(){
        return new ArrayList<>(checkedOrders);
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        binding = ListviewItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        Order _order = orderList.get(position);
        Invoice _invoice = _order.getInvoice();
        TableLocation _tableLocation = _order.getTableLocation();
        binding.tvOrderLviInvoiceId.setText(String.valueOf(_invoice.getInvoiceID()));
        binding.tvOrderLviCreatedDate.setText(DateHelper.dataToStringConverter(_invoice.getOrderTime()));

        if(_tableLocation == null){
            binding.tvOrderLviTableLocation.setText("-");
        }
        else {
            binding.tvOrderLviTableLocation.setText(_tableLocation.getTableName());
        }

        binding.tvOrderLviPrice.setText(CurrencyHelper.currencyConverter(_invoice.getTotal()));

        switch (_invoice.getPaymentStatus()){
            case PREPARING:{
                binding.ivOrderLviPaymentStatus.setImageResource(R.drawable.hot_pot);
                break;
            }
            case WAITING_FOR_PAYMENT:{
                binding.ivOrderLviPaymentStatus.setImageResource(R.drawable.time);
                break;
            }
            case PAID:{
                binding.ivOrderLviPaymentStatus.setImageResource(R.drawable.money);
                break;
            }
        }

        binding.cbOrderLviChecked.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                checkedOrders.add(_order);
            }
            else {
                checkedOrders.remove(_order);
            }
        });
        binding.getRoot().setOnClickListener(v -> optionsDialog(_order));

        return binding.getRoot();
    }

    private void optionsDialog(Order _order) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(fragmentActivity);
        builder.setTitle("Tùy Chọn");
        builder.setItems(fragmentActivity.getResources().getStringArray(R.array.invoice_options), (dialog, which) -> {

            switch (which) {
                case 0:
                    showInvoiceDetail(_order);
                    break;
                case 1:
                    changePaymentStatus(_order);
                    break;
                case 2:
                    dialog.dismiss();
                    shareBill(_order);
                    break;
            }


        });
        builder.show();
    }

    private void shareBill(Order order) {
        loadingView.setVisibility(View.VISIBLE);
        CompletableFuture<File> completableFuture = CompletableFuture.supplyAsync(() -> BillGenerator.createBill(order, fragmentActivity));

        completableFuture.thenAccept((File filePath) -> {
            String authority = fragmentActivity.getPackageName() + ".provider";
            assert filePath != null;
            Uri uri = FileProvider.getUriForFile(fragmentActivity, authority, filePath);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fragmentActivity.startActivity(Intent.createChooser(shareIntent, "Share PDF"));
            loadingView.setVisibility(View.GONE);
        });


    }

    private void changePaymentStatus(Order _order) {
        Invoice invoice = _order.getInvoice();

        Invoice.PaymentStatus paymentStatus;
        if(invoice.getPaymentStatus() == Invoice.PaymentStatus.PREPARING){
            paymentStatus = Invoice.PaymentStatus.WAITING_FOR_PAYMENT;
        }
        else if (invoice.getPaymentStatus() == Invoice.PaymentStatus.WAITING_FOR_PAYMENT){
            paymentStatus = Invoice.PaymentStatus.PAID;
        }
        else {
            paymentStatus = Invoice.PaymentStatus.PREPARING;
        }
        invoice.setPaymentStatus(paymentStatus);
        invoiceRepository.updateInvoice(invoice);
        orderList.sort(Comparator.comparing(o -> o.getInvoice().getPaymentStatus()));
        notifyDataSetChanged();
    }

    private void showInvoiceDetail(Order order){
        Invoice invoice = order.getInvoice();

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

        String paymentStatus = "";
        switch (invoice.getPaymentStatus()){
            case PREPARING:{
                paymentStatus = "Đang chuẩn bị...";
                break;
            }
            case WAITING_FOR_PAYMENT:{
                paymentStatus = "Đang chờ thanh toán...";
                break;
            }
            case PAID:{
                paymentStatus = "Đã thanh toán!";
                break;
            }
        }


        TableLocation _tableLocation = order.getTableLocation();
        String finalBillString = "Bàn: " +
                (_tableLocation == null ? "Mang Đi" : _tableLocation.getTableName()) + "\n" +
                "Thời gian lên đơn: " +
                DateHelper.dataToStringConverter(invoice.getOrderTime()) + "\n" +
                "Trạng thái: " + paymentStatus +
                "\n\n" +
                foodStringBuilder + "Tổng: " + CurrencyHelper.currencyConverter(invoice.getTotal()) + "\n" +
                "Giá tổng hóa đơn có thể khác giá sản phẩm do giá sản phẩm đã thay đổi sau khi đã lên đơn."
                ;

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(fragmentActivity);
        builder.setTitle("Chi Tiết Hóa Đơn")
                .setMessage(finalBillString)
                .create()
                .show();
    }


}