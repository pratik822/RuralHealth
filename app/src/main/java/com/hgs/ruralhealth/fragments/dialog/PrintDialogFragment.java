package com.hgs.ruralhealth.fragments.dialog;

import android.app.DialogFragment;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.database.DBHelper;
import com.hgs.ruralhealth.model.db.AdviceInputData;
import com.hgs.ruralhealth.model.register.RegisterOutputData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rameshg on 11/24/2016.
 */
public class PrintDialogFragment extends DialogFragment implements View.OnClickListener{

    private View view;
    private AutoCompleteTextView autoSWPNo, autoPatientName;
    private Button btnCancel, btnPrintPage;

    List<RegisterOutputData> regDataList = new ArrayList<>();
    DBHelper db;
    RegisterOutputData regData;
    ArrayAdapter<String> patienttNameAdapter, swpNOAdapter;
    List<String> patientList, swpNoList;

    AdviceInputData advicePrintData = new AdviceInputData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        view = inflater.inflate(R.layout.advice_print_dialog, container, false);

        db = new DBHelper(getActivity());
        regDataList = db.getAllPatientNameSWPNo();
        Log.i("Reg Patients Size==>", regDataList.size()+"");
        setupUI();
        setPatientSWPAdapter();
        autoPatientName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String patientName = autoPatientName.getText().toString();
                String[] parts = patientName.split(" ");
                regData = db.getRegDataByUserandSwpNo(parts[0],"","");
                autoPatientName.setText(regData.getFirstName() + " " + regData.getLastName());
                autoSWPNo.setText(regData.getSwpNo());
                advicePrintData=db.printAdviceData(regData.getSwpNo());
                Log.i("Print data",""+advicePrintData.toString());
            }
        });

        autoSWPNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String swpNO = autoSWPNo.getText().toString();
                String[] parts = swpNO.split(" ");
                regData = db.getRegDataByUserandSwpNo("","", parts[0]);
                autoPatientName.setText(regData.getFirstName() + " " + regData.getLastName());
                autoSWPNo.setText(regData.getSwpNo());
                advicePrintData=db.printAdviceData(regData.getSwpNo());
                Log.i("Print data",""+advicePrintData.toString());
            }
        });

        return view;
    }

    private void setupUI() {
        autoSWPNo = (AutoCompleteTextView) view.findViewById(R.id.autoSWPNo);
        autoPatientName = (AutoCompleteTextView) view.findViewById(R.id.autoPatientName);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnPrintPage = (Button) view.findViewById(R.id.btnPrint);
        btnPrintPage.setOnClickListener(this);
    }

    private void setPatientSWPAdapter() {
        if (regDataList != null) {
            patientList = new ArrayList<>();
            swpNoList = new ArrayList<>();

            for (int i = 0; i < regDataList.size(); i++) {
                patientList.add(regDataList.get(i).getFirstName() + " " + regDataList.get(i).getLastName() + "(" + regDataList.get(i).getSwpNo() + ")");
                //+" " +register_pationtname.get(i).getLast_name()+"("+register_pationtname.get(i).getSwp_no()+")");
                swpNoList.add(regDataList.get(i).getSwpNo() + " " + regDataList.get(i).getFirstName() + " " + regDataList.get(i).getLastName());
                // +"("+register_pationtname.get(i).getFirst_name()+" " +register_pationtname.get(i).getLast_name()+")");
            }

            autoPatientName.setThreshold(2);
            autoSWPNo.setThreshold(2);

            patienttNameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, patientList);
            swpNOAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, swpNoList);
            autoPatientName.setAdapter(patienttNameAdapter);
            autoSWPNo.setAdapter(swpNOAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancel:
                getDialog().dismiss();
                break;

            case R.id.btnPrint:
                printDocument();
                break;
        }
    }


    //Printer Functionality
    private void printDocument() {
        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
        String jobName = getString(R.string.app_name) + " Document";

        printManager.print(jobName, new MyPrintDocumentAdapter(getActivity()), null);
    }


    public class MyPrintDocumentAdapter extends PrintDocumentAdapter {
        public PdfDocument myPdfDocument;
        public int totalpages = 1;
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
        canvas.drawText("Advice Print Document Page " + pagenumber, leftMargin, titleBaseLine, paint);
        paint.setTextSize(14);
        String Date = "Date            : "+advicePrintData.getCreatedDate();
        canvas.drawText(Date, leftMargin, titleBaseLine + 35, paint);

        String Name = "Name                              : "+advicePrintData.getPatientName();
        canvas.drawText(Name, leftMargin, titleBaseLine + 70, paint);

        String swpNumber = "SWP Number               : "+advicePrintData.getSwpNo();
        canvas.drawText(swpNumber, leftMargin, titleBaseLine + 105, paint);

        String Age = "Age                                : "+regData.getAge();
        canvas.drawText(Age, leftMargin, titleBaseLine + 140, paint);

        String Gender = "Gender                          : "+regData.getGender();
        canvas.drawText(Gender, leftMargin, titleBaseLine + 175, paint);

        String Village = "Village/Pada                   : "+regData.getVillage();
        canvas.drawText(Village, leftMargin, titleBaseLine + 210, paint);

        String Height = "Height                          : "+regData.getHeight()+" "+regData.getHeightUnit();
        canvas.drawText(Height, leftMargin, titleBaseLine + 245, paint);

        String Weight = "Weight                          : "+regData.getWeight()+" "+regData.getWeightUnit();
        canvas.drawText(Weight, leftMargin, titleBaseLine + 280, paint);

        String Blood = "Chief Complaints                   : "+advicePrintData.getChiefComplaints();
        canvas.drawText(Blood, leftMargin, titleBaseLine + 315, paint);

        String systemData=null;
        int j=0;
        for(String system : advicePrintData.getSystem()){
            if(j==0) {
                systemData = system;
            }
            else {
                systemData = systemData + " , " + system;
            }
            j++;
        }

        String Contact = "System                 : "+systemData;
        canvas.drawText(Contact, leftMargin, titleBaseLine + 350, paint);

        Log.i("Medicine List - print",""+advicePrintData.getMedicationPrescribed().size());
        int margin=0;
        for(int i=0;i<advicePrintData.getMedicationPrescribed().size();i++){
            String Medicine = "Medicie"+(i+1)+"                 : "+advicePrintData.getMedicationPrescribed().get(i).getMedicine()+"  "  +advicePrintData.getMedicationPrescribed().get(i).getFrequency()+" "+advicePrintData.getMedicationPrescribed().get(i).getDuration();
            canvas.drawText(Medicine, leftMargin, titleBaseLine + 400+margin, paint);
            margin=margin+40;
        }



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
