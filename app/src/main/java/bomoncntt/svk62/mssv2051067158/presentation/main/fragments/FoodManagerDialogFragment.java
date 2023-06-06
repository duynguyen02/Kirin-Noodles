package bomoncntt.svk62.mssv2051067158.presentation.main.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.IOException;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.data.local.repository.DishRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.factory.LocalRepositoryFactory;
import bomoncntt.svk62.mssv2051067158.databinding.DialogAddFoodBinding;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.repository.DishRepository;
import bomoncntt.svk62.mssv2051067158.utils.Constraints;
import bomoncntt.svk62.mssv2051067158.utils.CurrencyHelper;
import bomoncntt.svk62.mssv2051067158.utils.CurrencyTextWatcher;
import bomoncntt.svk62.mssv2051067158.utils.FileIOHelper;
import bomoncntt.svk62.mssv2051067158.utils.OnItemChangedListener;
import bomoncntt.svk62.mssv2051067158.utils.ViewHelper;


public class FoodManagerDialogFragment extends DialogFragment {
    private DialogAddFoodBinding binding;
    private DishRepository dishRepository;
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private ActivityResultLauncher<Uri> captureImageLauncher;
    private File photoFile;
    private Uri photoUri;
    private FileIOHelper fileIOHelper;
    private final OnItemChangedListener<Dish> onItemChangedListener;
    private Dish dish;
    private FragmentActivity fragmentActivity;


    public FoodManagerDialogFragment(OnItemChangedListener<Dish> onItemChangedListener, Dish dish){
        this.onItemChangedListener = onItemChangedListener;
        this.dish = dish;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentActivity = requireActivity();
        fileIOHelper = FileIOHelper.getInstance(fragmentActivity);
        dishRepository = (DishRepository) LocalRepositoryFactory.getInstance(LocalRepositoryFactory.RepositoryType.DISH);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivity = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(fragmentActivity);
        LayoutInflater inflater = fragmentActivity.getLayoutInflater();
        binding = DialogAddFoodBinding.inflate(inflater);
        builder.setView(binding.getRoot());


        viewsSetup();
        eventsSetup();
        launcherSetup();
        return builder.create();
    }

    private void viewsSetup() {
        if (dish != null){
            binding.etAddFoodDFoodName.setText(dish.getDishName());
            binding.etAddFoodDFoodPrice.setText(CurrencyHelper.currencyConverter(dish.getPrice()));
            ViewHelper.setImageForImageViewFromFile(binding.ivAddFoodDFoodImage, dish.getImageLocation());
            binding.btnAddFoodDAdd.setText(R.string.edit);
        }
    }

    private void eventsSetup() {

        binding.etAddFoodDFoodPrice.addTextChangedListener(new CurrencyTextWatcher(binding.etAddFoodDFoodPrice));

        binding.btnAddFoodDAdd.setOnClickListener(v -> saveFood());

        binding.ivAddFoodDFoodImage.setOnClickListener(v -> showImageSelectionDialog());

    }

    private void saveFood() {
        Dish dish = getDishFromUserInput();
        if(dish == null){
            Toast.makeText(fragmentActivity, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.dish == null){
            addDishToDatabase(dish);
        }
        else {
            updateDish(this.dish, dish);
        }
    }

    private void updateDish(Dish oldDish, Dish newDish) {
        newDish.setDishID(oldDish.getDishID());
        if(dishRepository.updateDish(newDish)){
            Toast.makeText(fragmentActivity, "Sửa thành công!", Toast.LENGTH_SHORT).show();
            FileIOHelper.getInstance(requireContext()).deleteFile(oldDish.getImageLocation());
            this.dish = newDish;
            FoodManagerDialogFragment.this.dismiss();
            onItemChangedListener.onItemAdded(newDish);

        }
        else {
            Toast.makeText(fragmentActivity, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
            FileIOHelper.getInstance(requireContext()).deleteFile(newDish.getImageLocation());
            onItemChangedListener.onError();
        }
    }

    private void addDishToDatabase(Dish dish) {
        if(dishRepository.addDish(dish)){
            Toast.makeText(fragmentActivity, "Thêm thành công!", Toast.LENGTH_SHORT).show();
            resetViews();
            onItemChangedListener.onItemAdded(dish);

        }
        else {
            Toast.makeText(fragmentActivity, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
            onItemChangedListener.onError();
        }
    }

    private void resetViews() {
        binding.etAddFoodDFoodName.setText("");
        binding.etAddFoodDFoodPrice.setText("0");
        binding.ivAddFoodDFoodImage.setImageResource(R.drawable.food_example);
    }

    private Dish getDishFromUserInput(){
        String foodName = binding.etAddFoodDFoodName.getText().toString().trim();
        String priceString = binding.etAddFoodDFoodPrice.getText().toString().replace(",", "").trim();

        if (foodName.equals("") || priceString.equals("")) {
            return null;
        }
        long price = Long.parseLong(priceString);
        Bitmap bitmap = ViewHelper.getBitmapFromView(binding.ivAddFoodDFoodImage);
        File imagePath = fileIOHelper.copyImageToApplication(bitmap, Constraints.FOOD_IMAGES_DIR);

        if(imagePath == null){
            return null;
        }

        return new Dish(
                -1,
                foodName,
                price,
                imagePath.toString()
        );

    }


    private void showImageSelectionDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(fragmentActivity);
        builder.setTitle("Chọn Ảnh");
        builder.setItems(new CharSequence[]{"Chọn từ thư viện", "Chụp ảnh"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent;
                switch (which) {
                    case 0:
                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickImageLauncher.launch(intent);
                        break;
                    case 1:

                        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(fragmentActivity, new String[]{android.Manifest.permission.CAMERA}, 4444);
                        } else {
                            try {
                                File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                                photoFile = File.createTempFile("IMG_", ".jpg", storageDir);
                                photoUri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".provider", photoFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            captureImageLauncher.launch(photoUri);
                        }

                        break;
                }


            }
        });
        builder.show();
    }

    private void launcherSetup() {
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getResultCode() == Activity.RESULT_OK) {

                if (result.getData() != null) {
                    binding.ivAddFoodDFoodImage.setImageURI(result.getData().getData());
                }
            }

        });

        captureImageLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
            if (result) {
                binding.ivAddFoodDFoodImage.setImageURI(photoUri);
            }
        });
    }

}
