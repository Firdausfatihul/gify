package app.gify.co.id.modal;

public class MadolKado {
    private int harga;
    private String nama, kode, gambar;

    public MadolKado(String gambar, int harga, String nama, String kode) {
        this.gambar = gambar;
        this.harga = harga;
        this.nama = nama;
        this.kode = kode;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }
}
