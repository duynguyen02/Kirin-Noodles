package bomoncntt.svk62.mssv2051067158.presentation.main.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.data.local.repository.TableLocationRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.factory.LocalRepositoryFactory;
import bomoncntt.svk62.mssv2051067158.databinding.DialogAddTableBinding;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;
import bomoncntt.svk62.mssv2051067158.domain.repository.TableLocationRepository;
import bomoncntt.svk62.mssv2051067158.utils.OnItemChangedListener;

public class TableManagerDialogFragment extends DialogFragment {
    private DialogAddTableBinding binding;
    private TableLocationRepository tableLocationRepository;
    private OnItemChangedListener<TableLocation> onItemChangedListener;
    private TableLocation tableLocation;
    private FragmentActivity fragmentActivity;

    public TableManagerDialogFragment(OnItemChangedListener<TableLocation> onItemChangedListener, TableLocation tableLocation){
        this.onItemChangedListener = onItemChangedListener;
        this.tableLocation= tableLocation;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentActivity = requireActivity();
        tableLocationRepository = (TableLocationRepository) LocalRepositoryFactory.getInstance(LocalRepositoryFactory.RepositoryType.TABLE_LOCATION);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        binding = DialogAddTableBinding.inflate(inflater);
        builder.setView(binding.getRoot());

        viewsSetup();
        binding.btnAddTableDAdd.setOnClickListener(v -> saveTable());

        return builder.create();
    }

    private void saveTable() {
        String tableName = binding.etAddTableDTableName.getText().toString().trim();

        if(tableName.equals("")){
            Toast.makeText(requireActivity(), "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tableLocation == null){
            addTableLocationToDatabase(tableName);
        }
        else {
            updateTableLocation(tableName);
        }
    }

    private void updateTableLocation(String tableName) {
        tableLocation.setTableName(tableName);
        if(tableLocationRepository.updateTableLocation(tableLocation)){
            Toast.makeText(requireActivity(), "Sửa thành công!", Toast.LENGTH_SHORT).show();
            onItemChangedListener.onItemAdded(tableLocation);
            TableManagerDialogFragment.this.dismiss();

        }
        else {
            Toast.makeText(requireActivity(), "Sửa thất bại!", Toast.LENGTH_SHORT).show();
            onItemChangedListener.onError();
        }
    }

    private void addTableLocationToDatabase(String tableName) {
        TableLocation tableLocation = new TableLocation(-1, tableName);

        if(tableLocationRepository.addTableLocation(tableLocation)){
            Toast.makeText(requireActivity(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
            onItemChangedListener.onItemAdded(tableLocation);
            resetViews();

        }
        else {
            Toast.makeText(requireActivity(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
            onItemChangedListener.onError();
        }
    }

    private void viewsSetup() {
        if (tableLocation != null){
            binding.etAddTableDTableName.setText(tableLocation.getTableName());
            binding.btnAddTableDAdd.setText(R.string.edit);
        }
    }

    private void resetViews() {
        binding.etAddTableDTableName.setText("");
    }
}
