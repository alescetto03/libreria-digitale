package DAO;

public interface BookSaleResultInterface {
    StoreResultInterface getStore();
    BookResultInterface getBook();
    float getPrice();
    int getQuantity();
}
