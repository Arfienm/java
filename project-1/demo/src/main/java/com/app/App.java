package com.app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * JavaFX App
 */
public class App extends Application {

    public static class DataAbsen {
        private final String id;
        private final String nama;
        private final String status;
        private final String waktu;

        public DataAbsen(String id, String nama, String status, String waktu) {
            this.id = id;
            this.nama = nama;
            this.status = status;
            this.waktu = waktu;
        }

        public String getId() { return id; }
        public String getNama() { return nama; }
        public String getStatus() { return status; }
        public String getWaktu() { return waktu; }
    }

    private final ObservableList<DataAbsen> daftarAbsen =
            FXCollections.observableArrayList();

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void start(Stage stage) {

        TextField tfId = new TextField();
        tfId.setPromptText("ID / NIM / NIP");

        TextField tfNama = new TextField();
        tfNama.setPromptText("Nama Lengkap");

        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("Hadir", "Izin", "Sakit");
        cbStatus.setValue("Hadir");

        Button btnAbsen = new Button("Catat Absen");
        Button btnHapus = new Button("Hapus Terpilih");

        Label lblPesan = new Label();

        TableView<DataAbsen> table = new TableView<>(daftarAbsen);

        TableColumn<DataAbsen, String> colId =
                new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<DataAbsen, String> colNama =
                new TableColumn<>("Nama");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<DataAbsen, String> colStatus =
                new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<DataAbsen, String> colWaktu =
                new TableColumn<>("Waktu Absen");
        colWaktu.setCellValueFactory(new PropertyValueFactory<>("waktu"));

        table.getColumns().addAll(colId, colNama, colStatus, colWaktu);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        btnAbsen.setOnAction(e -> {
            String id = tfId.getText().trim();
            String nama = tfNama.getText().trim();
            String status = cbStatus.getValue();

            if (id.isEmpty() || nama.isEmpty()) {
                lblPesan.setText("ID dan Nama wajib diisi.");
                return;
            }

            String waktu = LocalDateTime.now().format(formatter);
            daftarAbsen.add(new DataAbsen(id, nama, status, waktu));

            tfId.clear();
            tfNama.clear();
            lblPesan.setText("Absensi berhasil dicatat.");
        });

        btnHapus.setOnAction(e -> {
            DataAbsen terpilih =
                    table.getSelectionModel().getSelectedItem();

            if (terpilih != null) {
                daftarAbsen.remove(terpilih);
                lblPesan.setText("Data terpilih dihapus.");
            }
        });

        HBox formBox = new HBox(10, tfId, tfNama, cbStatus, btnAbsen, btnHapus);
        VBox root = new VBox(12, formBox, lblPesan, table);
        root.setPadding(new Insets(16));

        Scene scene = new Scene(root, 720, 420);
        stage.setTitle("Aplikasi Absensi JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}