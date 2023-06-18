package bomoncntt.svk62.mssv2051067158.utils;

import android.content.Context;

import java.util.List;
import java.util.stream.Collectors;

import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.data.local.SecurePasswordManager;
import bomoncntt.svk62.mssv2051067158.data.local.repository.KirinNoodlesRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.DishDto;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.InvoiceDto;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.KirinNoodlesBackup;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.OrderedDishDto;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.Record;
import bomoncntt.svk62.mssv2051067158.data.remote.mapper.TableLocationDto;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;
import bomoncntt.svk62.mssv2051067158.domain.repository.KirinNoodlesRepository;
import bomoncntt.svk62.mssv2051067158.presentation.login.LoginActivity;

public class DataRecoveryHelper {
    private static DataRecoveryHelper instance;
    private KirinNoodlesSQLiteHelper kirinNoodlesSQLiteHelper;
    private KirinNoodlesRepository kirinNoodlesRepository;

    public synchronized static DataRecoveryHelper getInstance(Context context){
        if(instance == null){
            instance = new DataRecoveryHelper(context);
        }
        return instance;
    }

    private DataRecoveryHelper(Context context){
        this.kirinNoodlesSQLiteHelper = KirinNoodlesSQLiteHelper.getInstance(context.getApplicationContext());
        this.kirinNoodlesRepository = KirinNoodlesRepositoryImpl.getInstance(context);
    }

    public void resetData(){
        kirinNoodlesSQLiteHelper.resetAll();
        SecurePasswordManager.removePassword();
        SecurePasswordManager.setLoginWithPassword(false);
    }

    public void recoveryData(Record record, Context context){
        resetData();
        SecurePasswordManager.setLoginWithPassword(true);
        SecurePasswordManager.savePassword(record.getKirinNoodlesBackup().getPassword());

        List<Dish> dishes = record.getKirinNoodlesBackup().getDishDtos().stream().map(dishDto -> dishDto.mapFromDto(context.getApplicationContext())).collect(Collectors.toList());
        List<TableLocation> tableLocations = record.getKirinNoodlesBackup().getTableLocationDtos().stream().map(TableLocationDto::mapFromDto).collect(Collectors.toList());
        List<Invoice> invoices = record.getKirinNoodlesBackup().getInvoiceDtos().stream().map(InvoiceDto::mapFromDto).collect(Collectors.toList());
        List<OrderedDish> orderedDishes = record.getKirinNoodlesBackup().getOrderedDishDtos().stream().map(OrderedDishDto::mapFromDto).collect(Collectors.toList());

        dishes.forEach(dish -> kirinNoodlesRepository.addDish(dish));
        tableLocations.forEach(tableLocation -> kirinNoodlesRepository.addTableLocation(tableLocation));
        invoices.forEach(invoice -> kirinNoodlesRepository.addInvoice(invoice));
        orderedDishes.forEach(orderedDish -> kirinNoodlesRepository.addOrderedDish(orderedDish));
    }

    public KirinNoodlesBackup getBackup(){
        List<DishDto> dishDtoList = kirinNoodlesRepository.getAllDishes().stream().map(DishDto::mapToDto).collect(Collectors.toList());
        List<TableLocationDto> tableLocationList = kirinNoodlesRepository.getAllTableLocations().stream().map(TableLocationDto::mapToDto).collect(Collectors.toList());
        List<InvoiceDto> invoiceDtoList = kirinNoodlesRepository.getAllInvoices().stream().map(InvoiceDto::mapToDto).collect(Collectors.toList());
        List<OrderedDishDto> orderedDishDtoList = kirinNoodlesRepository.getAllOrderedDishes().stream().map(OrderedDishDto::mapToDto).collect(Collectors.toList());

        return new KirinNoodlesBackup(
                SecurePasswordManager.getPassword(),
                dishDtoList,
                tableLocationList,
                invoiceDtoList,
                orderedDishDtoList
        );
    }

}
