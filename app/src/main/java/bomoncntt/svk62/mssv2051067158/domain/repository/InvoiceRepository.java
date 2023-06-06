package bomoncntt.svk62.mssv2051067158.domain.repository;

import java.util.List;

import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;

public interface InvoiceRepository extends Repository{
    boolean addInvoice(Invoice invoice);

    Invoice getInvoiceByID(int invoiceID);

    List<Invoice> getAllInvoices();

    Invoice getInvoicesByTableID(int tableID);

    boolean updateInvoice(Invoice invoice);

    boolean deleteInvoice(int invoiceID);
    int getMaxId();
    boolean isTableLocationExist(TableLocation tableLocation);

}
