package bomoncntt.svk62.mssv2051067158.presentation.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.databinding.ActivityMainBinding;
import bomoncntt.svk62.mssv2051067158.presentation.main.fragments.OrderFragment;
import bomoncntt.svk62.mssv2051067158.presentation.main.fragments.ResourcesManagementFragment;
import bomoncntt.svk62.mssv2051067158.presentation.main.fragments.SettingsFragment;
import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private long lastBackPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initComponents();
    }

    private void initComponents() {
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
            changeFragment(i);
            return true;
        });
    }

    private void changeFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        Fragment selectedFragment = null;

        if (index == 0) {
            selectedFragment = new OrderFragment();
        } else if (index == 1) {
            selectedFragment = new ResourcesManagementFragment();
        } else if (index == 2) {
            selectedFragment = new SettingsFragment();
        }

        if (selectedFragment != null) {
            transaction.replace(binding.flMainAFragmentsHolder.getId(), selectedFragment);
            transaction.commit();
        }
    }

}