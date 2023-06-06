package bomoncntt.svk62.mssv2051067158.presentation.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.stream.Collectors;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.data.local.SecurePasswordManager;
import bomoncntt.svk62.mssv2051067158.data.local.repository.KirinNoodlesRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.DishDto;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.InvoiceDto;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.OrderedDishDto;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.Record;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.TableLocationDto;
import bomoncntt.svk62.mssv2051067158.data.remote.repository.KirinNoodlesBackupRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.databinding.ActivityLoginBinding;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;
import bomoncntt.svk62.mssv2051067158.domain.repository.KirinNoodlesBackupRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.KirinNoodlesRepository;
import bomoncntt.svk62.mssv2051067158.presentation.login.fragments.DataRecoveryDialogFragment;
import bomoncntt.svk62.mssv2051067158.presentation.login.fragments.RequirePasswordDialogFragment;
import bomoncntt.svk62.mssv2051067158.presentation.main.MainActivity;
import bomoncntt.svk62.mssv2051067158.presentation.slash.SplashActivity;
import bomoncntt.svk62.mssv2051067158.utils.DataRecoveryHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private KirinNoodlesBackupRepository kirinNoodlesBackupRepository;
    private KirinNoodlesRepository kirinNoodlesRepository;
    private DataRecoveryHelper dataRecoveryHelper;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponents();

    }


    private void initComponents(){
        getDependenciesInstance();
        setInitialPassword();
        viewSetup();
    }

    private void viewSetup() {
        binding.btnLoginALogin.setOnClickListener(v -> login());
        binding.tvLoginAGetBackup.setOnClickListener(v -> dataRecoveryConfirm());
    }

    private void setInitialPassword(){
        if(SecurePasswordManager.getPassword()==null){
            new RequirePasswordDialogFragment().show(getSupportFragmentManager(), "RequirePasswordDialogFragment");
        }
    }

    private void getDependenciesInstance(){
        kirinNoodlesBackupRepository = KirinNoodlesBackupRepositoryImpl.getInstance();
        kirinNoodlesRepository = KirinNoodlesRepositoryImpl.getInstance(this);
        dataRecoveryHelper = DataRecoveryHelper.getInstance(this);
    }

    private void dataRecoveryConfirm() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Bạn có chắc khôi phục lại dữ liệu?")
                .setMessage("Tất cả dữ liệu hiện tại sẽ bị xóa")
                .setPositiveButton("Có", (dialog, which) -> openDataRecoveryDialog())
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss()).create().show();
    }

    private void openDataRecoveryDialog() {
        new DataRecoveryDialogFragment(value -> {
            binding.flLoginALoading.setVisibility(View.VISIBLE);
            Call<Record> backupResultCall = kirinNoodlesBackupRepository.getBackUp(value);
            backupResultCall.enqueue(new Callback<Record>() {
                @Override
                public void onResponse(@NonNull Call<Record> call, @NonNull Response<Record> response) {
                    binding.flLoginALoading.setVisibility(View.GONE);
                    Record record = response.body();
                    if (record!=null){
                        recoveryData(record);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Record> call, @NonNull Throwable t) {
                    binding.flLoginALoading.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            });
        }).show(getSupportFragmentManager(), "DataRecoveryDialogFragment");

    }

    private void recoveryData(Record record) {
        dataRecoveryHelper.recoveryData(record);
        Toast.makeText(LoginActivity.this,"Khôi phục thành công!", Toast.LENGTH_SHORT).show();

    }

    private void login() {
        String password = binding.etLoginAPassword.getText().toString();
        if(password.equals(SecurePasswordManager.getPassword())){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(LoginActivity.this, "Mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
        }
    }
}