package bomoncntt.svk62.mssv2051067158.presentation.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.data.local.repository.factory.LocalRepositoryFactory;
import bomoncntt.svk62.mssv2051067158.databinding.FragmentResourcesManagementBinding;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;
import bomoncntt.svk62.mssv2051067158.domain.repository.DishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.InvoiceRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderedDishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.TableLocationRepository;
import bomoncntt.svk62.mssv2051067158.presentation.main.adapters.DishAdapter;
import bomoncntt.svk62.mssv2051067158.presentation.main.adapters.TableLocationAdapter;
import bomoncntt.svk62.mssv2051067158.utils.FileIOHelper;
import bomoncntt.svk62.mssv2051067158.utils.OnItemChangedListener;

public class ResourcesManagementFragment extends Fragment {
    private FragmentResourcesManagementBinding binding;
    private ArrayAdapter<CharSequence> kirinNoodlesResourcesSpinnerAdapter;
    private DishAdapter dishAdapter;
    private TableLocationAdapter tableLocationAdapter;
    private DishRepository dishRepository;
    private OrderedDishRepository orderedDishRepository;
    private TableLocationRepository tableLocationRepository;
    private InvoiceRepository invoiceRepository;
    private List<Dish> dishes = null;
    private List<TableLocation> tableLocations = null;
    private FragmentActivity fragmentActivity;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in);
        } else {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentResourcesManagementBinding.inflate(inflater, container, false);
        spinnerSetup();
        toolbarSetup();
        searchSetup();
        foodsListviewSetup();
        buttonSetup();

        return binding.getRoot();
    }

    private void buttonSetup() {
        binding.fabResManFAddRes.setOnClickListener(v -> openAddDialog());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getDependenciesInstance();
    }

    private void getDependenciesInstance() {
        fragmentActivity = requireActivity();

        dishRepository = (DishRepository) LocalRepositoryFactory.getInstance(LocalRepositoryFactory.RepositoryType.DISH);
        tableLocationRepository = (TableLocationRepository) LocalRepositoryFactory.getInstance(LocalRepositoryFactory.RepositoryType.TABLE_LOCATION);
        orderedDishRepository = (OrderedDishRepository) LocalRepositoryFactory.getInstance(LocalRepositoryFactory.RepositoryType.ORDERED_DISH);
        invoiceRepository = (InvoiceRepository) LocalRepositoryFactory.getInstance(LocalRepositoryFactory.RepositoryType.INVOICE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivity = null;
    }

    private void searchSetup() {
        binding.etResManFSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textLength = s.length();
                int index = getSpinnerIndex();
                ArrayAdapter adapter = null;
                if (index == 0) {
                    ArrayList<Dish> dishesTemp = new ArrayList<>();

                    for (Dish dish : dishes) {
                        if (textLength <= dish.getDishName().length()) {
                            if (dish.getDishName().toLowerCase().contains(s.toString().toLowerCase())) {
                                dishesTemp.add(dish);
                            }
                        }
                    }

                    adapter = new DishAdapter(fragmentActivity, dishesTemp, getChildFragmentManager());
                    dishAdapter = (DishAdapter) adapter;

                } else if (index == 1) {
                    ArrayList<TableLocation> tableLocationsTemp = new ArrayList<>();

                    for (TableLocation tableLocation : tableLocations) {
                        if (textLength <= tableLocation.getTableName().length()) {
                            if (tableLocation.getTableName().toLowerCase().contains(s.toString().toLowerCase())) {
                                tableLocationsTemp.add(tableLocation);
                            }
                        }
                    }

                    adapter = new TableLocationAdapter(fragmentActivity, tableLocationsTemp, getChildFragmentManager());
                    tableLocationAdapter = (TableLocationAdapter) adapter;
                }
                binding.lvResManFResTypes.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void toolbarSetup() {
        binding.tbResManF.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_res_man_delete_item) {
                confirmDelete();
            }
            return true;
        });
    }

    private void confirmDelete() {
        int index = getSpinnerIndex();
        List<Dish> dishes = null;
        List<TableLocation> tableLocations = null;

        boolean validCheck = false;
        if (index == 0) {
            dishes = dishAdapter.getChecked();
            validCheck = dishes.size() > 0;
        } else if (index == 1) {
            tableLocations = tableLocationAdapter.getChecked();
            validCheck = tableLocations.size() > 0;
        }

        if (!validCheck) {
            Toast.makeText(fragmentActivity, "Vui Lòng Chọn Đối Tượng Cần Xóa.", Toast.LENGTH_SHORT).show();
            return;
        }


        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(fragmentActivity);
        List<Dish> finalDishes = dishes;
        List<TableLocation> finalTableLocations = tableLocations;
        builder.setMessage("Bạn có muốn xóa các mục đã chọn?")
                .setPositiveButton("Có", (dialog, which) -> {
                    if (index == 0) {
                        deleteDishes(finalDishes);
                    } else {
                        deleteTableLocations(finalTableLocations);
                    }
                })
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void deleteDishes(List<Dish> finalDishes) {
        boolean wasSuccessful;
        FileIOHelper fileIOHelper = FileIOHelper.getInstance(fragmentActivity);
        for (Dish dish : finalDishes) {
            if (orderedDishRepository.isDishExist(dish.getDishID())) {
                wasSuccessful = false;
            } else {
                wasSuccessful = dishRepository.deleteDish(dish.getDishID());
            }
            if (wasSuccessful) {
                fileIOHelper.deleteFile(dish.getImageLocation());
                dishAdapter.remove(dish);
                dishAdapter.notifyDataSetChanged();
            }
        }
    }

    private void deleteTableLocations(List<TableLocation> finalTableLocations) {
        for (TableLocation tableLocation : finalTableLocations) {
            boolean wasSuccessful = false;
            if (!invoiceRepository.isTableLocationExist(tableLocation)) {
                wasSuccessful = tableLocationRepository.deleteTableLocation(tableLocation.getTableID());
            }
            if(wasSuccessful){
                tableLocationAdapter.remove(tableLocation);
                tableLocationAdapter.notifyDataSetChanged();
            }
        }
    }


    private void openAddDialog() {
        int index = getSpinnerIndex();
        if (index == 0) {

            new FoodManagerDialogFragment(new OnItemChangedListener<Dish>() {
                @Override
                public void onItemAdded(Dish newDish) {
//                    dishAdapter.add(newDish);
//                    dishAdapter.notifyDataSetChanged();
                    foodsListviewSetup();
                }

                @Override
                public void onError() {

                }
            }, null).show(requireActivity().getSupportFragmentManager(), "AddFoodDialogFragment");


        } else if (index == 1) {
            new TableManagerDialogFragment(new OnItemChangedListener<TableLocation>() {
                @Override
                public void onItemAdded(TableLocation tableLocation) {
//                    tableLocationAdapter.add(tableLocation);
//                    tableLocationAdapter.notifyDataSetChanged();
                    tableLocationSetup();
                }

                @Override
                public void onError() {

                }
            }, null).show(requireActivity().getSupportFragmentManager(), "AddTableDialogFragment");
        }

    }

    private void spinnerSetup() {
        kirinNoodlesResourcesSpinnerAdapter = ArrayAdapter.createFromResource(
                fragmentActivity,
                R.array.kirin_noodles_resources,
                android.R.layout.simple_spinner_item
        );
        kirinNoodlesResourcesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sprResManFResTypes.setAdapter(kirinNoodlesResourcesSpinnerAdapter);

        binding.sprResManFResTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = getSpinnerIndex();
                if (index == 0) {
                    foodsListviewSetup();
                } else if (index == 1) {
                    tableLocationSetup();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private int getSpinnerIndex() {
        String spinnerSelectedItem = (String) binding.sprResManFResTypes.getSelectedItem();
        return kirinNoodlesResourcesSpinnerAdapter.getPosition(spinnerSelectedItem);
    }

    private void tableLocationSetup() {
        tableLocations = tableLocationRepository.getAllTableLocations();
        tableLocationAdapter = new TableLocationAdapter(fragmentActivity, tableLocations, getChildFragmentManager());
        binding.lvResManFResTypes.setAdapter(tableLocationAdapter);
    }

    private void foodsListviewSetup() {
        dishes = dishRepository.getAllDishes();
        dishAdapter = new DishAdapter(fragmentActivity, dishes, getChildFragmentManager());
        binding.lvResManFResTypes.setAdapter(dishAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
