package com.hgs.ruralhealth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnPrint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPrint = (Button) findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPrint:
                printDocument();
                break;
        }
    }

    //Printer Functionality
    private void printDocument() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        String jobName = getString(R.string.app_name) + " Document";

        printManager.print(jobName, new MyPrintDocumentAdapter(this), null);
    }


    public class MyPrintDocumentAdapter extends PrintDocumentAdapter {
        public PdfDocument myPdfDocument;
        public int totalpages = 2;
        Context context;
        private int pageHeight;
        private int pageWidth;

        public MyPrintDocumentAdapter(Context context) {
            this.context = context;
        }


        @Override
        public void onLayout(PrintAttributes oldAttributes,
                             PrintAttributes newAttributes,
                             CancellationSignal cancellationSignal,
                             LayoutResultCallback callback,
                             Bundle metadata) {

            myPdfDocument = new PrintedPdfDocument(context, newAttributes);

            pageHeight = newAttributes.getMediaSize().getHeightMils() / 1000 * 72;
            pageWidth = newAttributes.getMediaSize().getWidthMils() / 1000 * 72;

            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }

            if (totalpages > 0) {
                PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                        .Builder("print_output.pdf")
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(totalpages);

                PrintDocumentInfo info = builder.build();
                callback.onLayoutFinished(info, true);
            } else {
                callback.onLayoutFailed("Page count is zero.");
            }
        }


        @Override
        public void onWrite(final PageRange[] pageRanges,
                            final ParcelFileDescriptor destination,
                            final CancellationSignal cancellationSignal,
                            final WriteResultCallback callback) {

            for (int i = 0; i < totalpages; i++) {
                if (pageInRange(pageRanges, i)) {
                    PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                            pageHeight, i).create();

                    PdfDocument.Page page =
                            myPdfDocument.startPage(newPage);

                    if (cancellationSignal.isCanceled()) {
                        callback.onWriteCancelled();
                        myPdfDocument.close();
                        myPdfDocument = null;
                        return;
                    }
                    drawPage(page, i);
                    myPdfDocument.finishPage(page);
                }
            }

            try {
                myPdfDocument.writeTo(new FileOutputStream(
                        destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                myPdfDocument.close();
                myPdfDocument = null;
            }

            callback.onWriteFinished(pageRanges);
        }

    }

    private void drawPage(PdfDocument.Page page,
                          int pagenumber) {
        Canvas canvas = page.getCanvas();

        pagenumber++; // Make sure page numbers start at 1

        int titleBaseLine = 72;
        int leftMargin = 54;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        canvas.drawText("Test Print Document Page " + pagenumber, leftMargin, titleBaseLine, paint);
        paint.setTextSize(14);
        String Date = "Date                             : 08-09-2016";
        canvas.drawText(Date, leftMargin, titleBaseLine + 35, paint);

        String Name ="Name                              : Ramesh Gundala";
        canvas.drawText(Name, leftMargin, titleBaseLine +70, paint);

        String swpNumber ="SWP Number               : MUM_TAB1_20160908_12345";
        canvas.drawText(swpNumber, leftMargin, titleBaseLine +105, paint);

        String Age ="Age                                : 30";
        canvas.drawText(Age, leftMargin, titleBaseLine +140, paint);

        String Gender ="Gender                          : Male";
        canvas.drawText(Gender, leftMargin, titleBaseLine +175, paint);

        String Village ="Village/Pada                   : Mumbai";
        canvas.drawText(Village, leftMargin, titleBaseLine +210, paint);

        String Height ="Height                          : 5.7 inchs";
        canvas.drawText(Height, leftMargin, titleBaseLine +245, paint);

        String Weight ="Weight                          : 65 kg";
        canvas.drawText(Weight, leftMargin, titleBaseLine +280, paint);

        String Blood ="Blood Pressure                   : Systolic";
        canvas.drawText(Blood, leftMargin, titleBaseLine +315, paint);

        String Contact ="Contact Number                 : 9123456789";
        canvas.drawText(Contact, leftMargin, titleBaseLine +350, paint);

        if (pagenumber % 2 == 0)
            paint.setColor(Color.RED);
        else
            paint.setColor(Color.GREEN);

        PdfDocument.PageInfo pageInfo = page.getInfo();


       /* canvas.drawCircle(pageInfo.getPageWidth() / 2,
                pageInfo.getPageHeight() / 2,
                150,
                paint);*/
    }


    private boolean pageInRange(PageRange[] pageRanges, int page) {
        for (int i = 0; i < pageRanges.length; i++) {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }
}
