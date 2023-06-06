package bomoncntt.svk62.mssv2051067158.presentation.login.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.Record;
import bomoncntt.svk62.mssv2051067158.data.remote.repository.KirinNoodlesBackupRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.databinding.DialogDataRecoveryBinding;
import bomoncntt.svk62.mssv2051067158.domain.repository.KirinNoodlesBackupRepository;
import bomoncntt.svk62.mssv2051067158.utils.DataRecoveryHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRecoveryDialogFragment extends DialogFragment {
    
    private DialogDataRecoveryBinding binding;
    private FragmentActivity fragmentActivity;
    private OnResultListener onResultListener;

    public interface OnResultListener{
        void OnResult(String value);
    }


    public DataRecoveryDialogFragment(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentActivity = requireActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivity = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        binding = DialogDataRecoveryBinding.inflate(inflater);
        builder.setView(binding.getRoot());

        binding.btnDataRecoverySubmit.setOnClickListener(v -> {
            String id = binding.etDataRecoveryDId.getText().toString().trim();
            if(id.isEmpty()){
                Toast.makeText(fragmentActivity, "Vui lòng nhập mã sao lưu!", Toast.LENGTH_SHORT).show();
                return;
            }

            DataRecoveryDialogFragment.this.dismiss();
            onResultListener.OnResult(id);

        });


        return builder.create();
    }


}
