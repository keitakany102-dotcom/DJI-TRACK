package com.Somagep.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class QrCodeGenerator {

    public String generateQrCode(String data, String fileName) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

            Path path = Paths.get("uploads/qr/" + fileName + ".png");
            Files.createDirectories(path.getParent());
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            return path.toString();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
