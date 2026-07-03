package com.Somagep.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.Somagep.entity.Facture;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class PdfGenerator {

    public byte[] genererFacturePDF(Facture facture) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("FACTURE").setBold().setFontSize(20));
        document.add(new Paragraph("N° " + facture.getNumeroFacture()));
        document.add(new Paragraph("Date : " + facture.getDateEmission()));
        document.add(new Paragraph("Client : " + facture.getClient().getNom() + " " + facture.getClient().getPrenom()));
        document.add(new Paragraph("Adresse : " + facture.getClient().getAdresse()));
        document.add(new Paragraph("Consommation : " + facture.getConsommation() + " m3"));
        document.add(new Paragraph("Montant HT : " + facture.getMontantTotal() + " FCFA"));
        document.add(new Paragraph("Taxes : 18%"));
        document.add(new Paragraph("Montant TTC : " + facture.getMontantTTC() + " FCFA"));
        document.add(new Paragraph("Date d'échéance : " + facture.getDateEcheance()));
        document.add(new Paragraph("Statut : " + facture.getStatut()));

        document.close();

        return baos.toByteArray();
    }
}