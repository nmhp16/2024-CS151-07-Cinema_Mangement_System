public interface Billable {
    void processTransaction(Transaction transaction);
    void printReceipt(Transaction transaction);
    void printReceipt();
}
