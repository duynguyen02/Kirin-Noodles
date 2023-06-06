package bomoncntt.svk62.mssv2051067158.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bomoncntt.svk62.mssv2051067158.data.local.repository.factory.LocalRepositoryFactory;
import bomoncntt.svk62.mssv2051067158.databinding.ListviewItemTableLocationBinding;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;
import bomoncntt.svk62.mssv2051067158.domain.repository.TableLocationRepository;
import bomoncntt.svk62.mssv2051067158.presentation.main.fragments.TableManagerDialogFragment;
import bomoncntt.svk62.mssv2051067158.utils.OnItemChangedListener;

public class TableLocationAdapter extends ArrayAdapter<TableLocation> {

    private final Set<TableLocation> checkedItems = new ArraySet<>();
    private ListviewItemTableLocationBinding binding;
    private TableLocationRepository tableLocationRepository;
    private FragmentManager fragmentManager;


    public TableLocationAdapter(@NonNull Context context, List<TableLocation> tableLocations, FragmentManager fragmentManager) {
        super(context, 0, tableLocations);
        this.tableLocationRepository = (TableLocationRepository) LocalRepositoryFactory.getInstance(LocalRepositoryFactory.RepositoryType.TABLE_LOCATION);
        this.fragmentManager = fragmentManager;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        binding = ListviewItemTableLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        TableLocation _tableLocation = getItem(position);

        binding.tvTableLocationLviName.setText(_tableLocation.getTableName());

        binding.cbTableLocationLviSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                checkedItems.add(_tableLocation);
            }
            else {
                checkedItems.remove(_tableLocation);
            }
        });

        binding.getRoot().setOnClickListener(v -> new TableManagerDialogFragment(new OnItemChangedListener<TableLocation>() {
            @Override
            public void onItemAdded(TableLocation tableLocation) {
                _tableLocation.setTableName(tableLocation.getTableName());
                notifyDataSetChanged();
            }

            @Override
            public void onError() {

            }
        }, _tableLocation).show(fragmentManager, "TableManagerDialogFragment"));

        return binding.getRoot();
    }

    public List<TableLocation> getChecked(){
        return new ArrayList<>(checkedItems);
    }

}
