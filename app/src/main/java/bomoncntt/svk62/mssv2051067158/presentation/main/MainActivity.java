package bomoncntt.svk62.mssv2051067158.presentation.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.data.local.repository.DishRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.InvoiceRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.OrderedDishRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.TableLocationRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.KirinNoodlesBackup;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.Record;
import bomoncntt.svk62.mssv2051067158.data.remote.repository.KirinNoodlesBackupRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.databinding.ActivityMainBinding;
import bomoncntt.svk62.mssv2051067158.domain.repository.DishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.InvoiceRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.KirinNoodlesBackupRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderedDishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.TableLocationRepository;
import bomoncntt.svk62.mssv2051067158.presentation.main.fragments.OrderFragment;
import bomoncntt.svk62.mssv2051067158.presentation.main.fragments.ResourcesManagementFragment;
import bomoncntt.svk62.mssv2051067158.presentation.main.fragments.SettingsFragment;
import me.ibrahimsn.lib.OnItemSelectedListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private long lastBackPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentsSetup();

    }


    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackPressedTime > 2000) {
            Toast.makeText(this, "Nhấn một lần nữa để thoát", Toast.LENGTH_SHORT).show();
            lastBackPressedTime = currentTime;
        } else {
            super.onBackPressed();
        }
    }


    private void fragmentsSetup() {
        getSupportFragmentManager().beginTransaction().replace(binding.flMainAFragmentsHolder.getId(), new OrderFragment()).commit();

        binding.bnvMainAMain.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
            Fragment selectedFragment = null;

            if (i == 0) {
                selectedFragment = new OrderFragment();
            } else if (i == 1) {
                selectedFragment = new ResourcesManagementFragment();
            } else if (i == 2) {
                selectedFragment = new SettingsFragment();
            }

            if (selectedFragment != null) {
                transaction.replace(binding.flMainAFragmentsHolder.getId(), selectedFragment);
                transaction.commit();
            }

            return true;
        });
//        binding.bnvMainAMain.setOnItemSelectedListener(item -> {
//
//            Fragment selectedFragment = null;
//
//            int id = item.getItemId();
//
//            if (id == R.id.menu_main_order) {
//                selectedFragment = new OrderFragment();
//            } else if (id == R.id.menu_main_management) {
//                selectedFragment = new ResourcesManagementFragment();
//            } else if (id == R.id.menu_main_settings) {
//                selectedFragment = new SettingsFragment();
//            }
//
//            if (selectedFragment != null) {
//                getSupportFragmentManager().beginTransaction().replace(binding.flMainAFragmentsHolder.getId(), selectedFragment).commit();
//            }
//
//            return true;
//        });
    }

}