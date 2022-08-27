package enums;

public enum Responses {
    success(200),
    fileNotFound(404),
    requestNotFound(405),
    exception(500);
    private int i;
    Responses(int i) {
        this.i=i;
    }

    public int getI() {
        return i;
    }
}
