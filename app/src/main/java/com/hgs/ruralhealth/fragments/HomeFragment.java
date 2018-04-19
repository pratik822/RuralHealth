package com.hgs.ruralhealth.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.utilities.Utililty;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by rameshg on 9/2/2016.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private LinearLayout btnRegsiration, btnAdvice, btnMSWActivity, btnPhysioActivity, btnOpthalData, btnReports,llDistribution;
    private Button btnPrint;
    Fragment fragment = null;
    private View fragmentView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.home_fragment, container, false);
        setupUI();
        return fragmentView;
    }


    @Override
    public void onResume() {
        super.onResume();
        Utililty.setTitle(getActivity(),"Home");
    }

    private void setupUI() {
        btnRegsiration = (LinearLayout) fragmentView.findViewById(R.id.llRegistration);
        btnRegsiration.setOnClickListener(this);

        btnAdvice = (LinearLayout) fragmentView.findViewById(R.id.llAdvice);
        btnAdvice.setOnClickListener(this);

        btnMSWActivity = (LinearLayout)  fragmentView.findViewById(R.id.llMSWActivity);
        btnMSWActivity.setOnClickListener(this);

        btnPhysioActivity = (LinearLayout)  fragmentView.findViewById(R.id.llPhysioActivity);
        btnPhysioActivity.setOnClickListener(this);

        btnOpthalData = (LinearLayout)  fragmentView.findViewById(R.id.llOphthalData);
        btnOpthalData.setOnClickListener(this);

        btnReports = (LinearLayout)  fragmentView.findViewById(R.id.llReports);
        btnReports.setOnClickListener(this);

        llDistribution=(LinearLayout)fragmentView.findViewById(R.id.llDistribution);
        llDistribution.setOnClickListener(this);

        btnPrint = (Button) fragmentView.findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llRegistration:
                fragment = new RegistrationFragment();
                openFragment(fragment);
                break;

            case R.id.llAdvice:
                fragment = new AdviceFragment();
                openFragment(fragment);
                break;

            case R.id.llMSWActivity:
                fragment = new MSWActiviyFragment();
                openFragment(fragment);
                break;

            case R.id.llPhysioActivity:
                fragment = new PhysiotheropistFragment();
                openFragment(fragment);
                break;

            case R.id.llOphthalData:
                fragment = new OpthalDataFragment();
                openFragment(fragment);
                break;

            case R.id.llReports:
                fragment = new ReportsFragment();
                openFragment(fragment);
                break;

            case R.id.llDistribution:
                fragment = new MSWDistributionActiviyFragment();
                openFragment(fragment);
                break;

            case R.id.btnPrint:
                printDocument();
                break;

        }

    }

    private void openFragment(Fragment fragment) {
        if(null!= fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Utililty.logout(getActivity());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // ++++++++++++++++++++++++  Printer Functionality Start +++++++++++++++++++++++++++++++//

    private void printDocument() {
        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
        String jobName = getString(R.string.app_name) + " Document";

        printManager.print(jobName, new MyPrintDocumentAdapter(getActivity()), null);
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

        /*public void printDocument()
        {
            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
            String jobName = getString(R.string.app_name) +" Document";

            printManager.print(jobName, new MyPrintDocumentAdapter(getApplicationContext()),null);
        }*/

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
        canvas.drawText(
                "Test Print Document Page " + pagenumber,
                leftMargin,
                titleBaseLine,
                paint);

        paint.setTextSize(14);
        canvas.drawText("This is some test content to verify that custom document printing works", leftMargin, titleBaseLine + 35, paint);

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
    // ++++++++++++++++++++++++  Printer Functionality End +++++++++++++++++++++++++++++++//
}
