package bomoncntt.svk62.mssv2051067158.presentation.main.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.data.local.SecurePasswordManager;
import bomoncntt.svk62.mssv2051067158.data.local.repository.KirinNoodlesRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.remote.api.service.KirinNoodlesBackupService;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.BackupResult;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.DishDto;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.InvoiceDto;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.KirinNoodlesBackup;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.OrderedDishDto;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.TableLocationDto;
import bomoncntt.svk62.mssv2051067158.data.remote.repository.KirinNoodlesBackupRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.databinding.FragmentOrderBinding;
import bomoncntt.svk62.mssv2051067158.databinding.FragmentSettingsBinding;
import bomoncntt.svk62.mssv2051067158.domain.repository.KirinNoodlesBackupRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.KirinNoodlesRepository;
import bomoncntt.svk62.mssv2051067158.presentation.login.LoginActivity;
import bomoncntt.svk62.mssv2051067158.presentation.slash.SplashActivity;
import bomoncntt.svk62.mssv2051067158.utils.DataRecoveryHelper;
import bomoncntt.svk62.mssv2051067158.utils.FileIOHelper;
import bomoncntt.svk62.mssv2051067158.utils.ViewHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment  extends Fragment {

    private FragmentSettingsBinding binding;
    private FragmentActivity fragmentActivity;
    private DataRecoveryHelper dataRecoveryHelper;

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
        getDependenciesInstance();
    }

    private void getDependenciesInstance(){
        fragmentActivity = requireActivity();
        dataRecoveryHelper = DataRecoveryHelper.getInstance(fragmentActivity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewsSetup();

    }

    private void viewsSetup() {
        binding.swtSettingsFRequirePassword.setChecked(SecurePasswordManager.isLoginWithPassword());

        binding.swtSettingsFRequirePassword.setOnCheckedChangeListener((buttonView, isChecked) -> SecurePasswordManager.setLoginWithPassword(isChecked));

        binding.btnSettingsFChangePassword.setOnClickListener(v -> new ChangePasswordDialogFragment().show(fragmentActivity.getSupportFragmentManager(),"ChangePasswordDialogFragment"));

        binding.btnSettingsFLogout.setOnClickListener(v -> {
            SecurePasswordManager.setLoginWithPassword(true);
            Intent intent = new Intent(getContext(), SplashActivity.class);
            fragmentActivity.finish();
            startActivity(intent);
        });

        binding.btnSettingsFResetData.setOnClickListener(v -> resetDataConfirm());
        binding.btnSettingsFBackup.setOnClickListener(v -> openConfirmBackup());
        binding.btnSettingsFDeleteCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfirmDeleteCache();
            }
        });
    }

    private void openConfirmDeleteCache() {
        new MaterialAlertDialogBuilder(fragmentActivity)
                .setTitle("Xác Nhận")
                .setMessage("Bạn chắc có muốn xóa bộ nhớ đệm? (Dữ liệu sẽ không bị mất)")
                .setPositiveButton("Có", (dialog, which) -> {
                    deleteCache();
                })
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss()).create().show();
    }

    private void deleteCache() {
        binding.flSettingsFLoading.setVisibility(View.VISIBLE);
        FileIOHelper.getInstance(fragmentActivity).deleteCache();
        binding.flSettingsFLoading.setVisibility(View.GONE);
        Toast.makeText(fragmentActivity, "Xóa bộ nhớ đệm thành công!", Toast.LENGTH_SHORT).show();;
    }

    private void openConfirmBackup() {
        new MaterialAlertDialogBuilder(fragmentActivity)
                .setTitle("Xác Nhận")
                .setMessage("Bạn chắc có muốn sao lưu dữ liệu")
                .setPositiveButton("Có", (dialog, which) -> {
                    backup();
                })
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss()).create().show();
    }

    private void backup() {
        binding.flSettingsFLoading.setVisibility(View.VISIBLE);

        KirinNoodlesBackup kirinNoodlesBackup = dataRecoveryHelper.getBackup();

        KirinNoodlesBackupRepository kirinNoodlesBackupRepository = KirinNoodlesBackupRepositoryImpl.getInstance();
        kirinNoodlesBackupRepository.postBackup(kirinNoodlesBackup).enqueue(new Callback<BackupResult>() {
            @Override
            public void onResponse(@NonNull Call<BackupResult> call, @NonNull Response<BackupResult> response) {
                binding.flSettingsFLoading.setVisibility(View.GONE);
                BackupResult backupResult = response.body();

                if(backupResult != null){
                    showSuccessBackupDialog(backupResult);
                }
                else {
                    Toast.makeText(fragmentActivity, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BackupResult> call, @NonNull Throwable t) {
                binding.flSettingsFLoading.setVisibility(View.GONE);
                Toast.makeText(fragmentActivity, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSuccessBackupDialog(BackupResult backupResult) {
        new MaterialAlertDialogBuilder(fragmentActivity)
                .setTitle("Thông Báo")
                .setMessage("Sao Lưu Thành Công!\nMã Sao Lưu Của Bạn Là: " +
                        backupResult.getMetadata().getId() +
                        "\nNhấn Tiếp Tục Để Chép Vào Bộ Nhớ Tạm"
                )
                .setPositiveButton("Tiếp Tục", (dialog, which) -> {
                    ClipboardManager clipboard = (ClipboardManager) fragmentActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("id", backupResult.getMetadata().getId());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(fragmentActivity, "Đã Chép Vào Bộ Nhớ Tạm", Toast.LENGTH_SHORT).show();
                })
                .setCancelable(false)
                .create().show();
    }

    private void resetDataConfirm() {
        new MaterialAlertDialogBuilder(fragmentActivity)
                .setTitle("Bạn có chắc thiết đặt lại dữ liệu?")
                .setMessage("Tất cả dữ liệu hiện tại sẽ bị xóa")
                .setPositiveButton("Có", (dialog, which) -> {
                    resetData();
                    fragmentActivity.finish();
                    startActivity(new Intent(fragmentActivity, SplashActivity.class));
                })
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss()).create().show();
    }

    private void resetData() {
        dataRecoveryHelper.resetData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
