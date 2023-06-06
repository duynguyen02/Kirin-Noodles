package bomoncntt.svk62.mssv2051067158.presentation.main.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import bomoncntt.svk62.mssv2051067158.data.local.SecurePasswordManager;
import bomoncntt.svk62.mssv2051067158.databinding.DialogChangePasswordBinding;

public class ChangePasswordDialogFragment extends DialogFragment {
    private DialogChangePasswordBinding binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        binding = DialogChangePasswordBinding.inflate(inflater);
        builder.setView(binding.getRoot());

        binding.btnChangePasswordDChange.setOnClickListener(v -> performPasswordChange());

        return builder.create();
    }

    private void performPasswordChange() {
        String oldPassword = binding.etChangePasswordDOldPassword.getText().toString();
        String newPassword = binding.etChangePasswordDNewPassword.getText().toString();
        String newPasswordConfirm = binding.etChangePasswordDNewPasswordAgain.getText().toString();

        if (oldPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()) {
            Toast.makeText(requireActivity(), "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPasswordConfirm.equals(newPassword)) {
            Toast.makeText(requireActivity(), "Mật khẩu xác nhận không trùng khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!oldPassword.equals(SecurePasswordManager.getPassword())) {
            Toast.makeText(requireActivity(), "Mật khẩu cũ không chính xác!", Toast.LENGTH_SHORT).show();
            return;
        }

        SecurePasswordManager.savePassword(newPassword);
        Toast.makeText(requireActivity(), "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
        ChangePasswordDialogFragment.this.dismiss();
    }
}
