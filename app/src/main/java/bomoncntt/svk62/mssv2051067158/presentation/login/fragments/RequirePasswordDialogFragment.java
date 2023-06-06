package bomoncntt.svk62.mssv2051067158.presentation.login.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import bomoncntt.svk62.mssv2051067158.data.local.SecurePasswordManager;
import bomoncntt.svk62.mssv2051067158.databinding.DialogRequirePasswordBinding;

public class RequirePasswordDialogFragment extends DialogFragment {

    private DialogRequirePasswordBinding binding;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        binding = DialogRequirePasswordBinding.inflate(inflater);
        builder.setView(binding.getRoot());

        setCancelable(false);

        binding.btnRequirePasswordDConfirm.setOnClickListener(v -> {
            String password = binding.etRequirePasswordDPassword.getText().toString();
            String passwordAgain = binding.etRequirePasswordDPasswordAgain.getText().toString();

            if(password.isEmpty() || passwordAgain.isEmpty() || !passwordAgain.equals(password)){
                Toast.makeText(requireActivity(), "Vui lòng nhập mật khẩu trùng khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            SecurePasswordManager.savePassword(password);
            Toast.makeText(requireActivity(), "Thiết lập thành công! Vui lòng đăng nhập để tiếp tục.", Toast.LENGTH_SHORT).show();
            RequirePasswordDialogFragment.this.dismiss();
        });


        return builder.create();
    }
}
