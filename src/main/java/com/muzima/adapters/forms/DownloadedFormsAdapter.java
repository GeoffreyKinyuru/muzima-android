package com.muzima.adapters.forms;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muzima.R;
import com.muzima.api.model.Form;
import com.muzima.api.model.Tag;
import com.muzima.controller.FormController;
import com.muzima.search.api.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class DownloadedFormsAdapter extends FormsAdapter {
    private static final String TAG = "NewFormsAdapter";

    public DownloadedFormsAdapter(Context context, int textViewResourceId, FormController formController) {
        super(context, textViewResourceId, formController);
    }

    @Override
    public void reloadData() {
        new BackgroundQueryTask().execute();
    }

    public class BackgroundQueryTask extends AsyncTask<Void, Void, List<Form>> {

        @Override
        protected List<Form> doInBackground(Void... voids) {
            List<Form> downloadedForms = null;
            try {
                downloadedForms = formController.getAllDownloadedForms();
                Log.i(TAG, "#Forms with templates: " + downloadedForms.size());
            } catch (FormController.FormFetchException e) {
                Log.w(TAG, "Exception occurred while fetching local forms " + e);
            }
            return downloadedForms;
        }

        @Override
        protected void onPostExecute(List<Form> forms) {
            DownloadedFormsAdapter.this.clear();
            for (Form form : forms) {
                add(form);
            }
            notifyDataSetChanged();
        }
    }
}
