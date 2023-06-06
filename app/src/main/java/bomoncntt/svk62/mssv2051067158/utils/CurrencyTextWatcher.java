package bomoncntt.svk62.mssv2051067158.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class CurrencyTextWatcher implements TextWatcher {
    private final EditText editText;
    private final DecimalFormat decimalFormat;

    public CurrencyTextWatcher(EditText editText) {
        this.editText = editText;
        this.decimalFormat = new DecimalFormat("#,###");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);



        String amount = s.toString().replace(",", "");
        if (!amount.equals("")){
            String formattedAmount = decimalFormat.format(Double.parseDouble(amount));
            editText.setText(formattedAmount);
            editText.setSelection(formattedAmount.length());
        }


        editText.addTextChangedListener(this);
    }
}
