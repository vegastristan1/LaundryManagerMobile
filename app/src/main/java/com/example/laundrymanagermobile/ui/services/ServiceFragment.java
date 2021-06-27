package com.example.laundrymanagermobile.ui.services;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.laundrymanagermobile.R;
import com.example.laundrymanagermobile.apiconnection.Api;
import com.example.laundrymanagermobile.apiconnection.Manager;
//import com.example.laundrymanagermobile.databinding.FragmentServiceBinding;
import com.example.laundrymanagermobile.databinding.FragmentServiceListBinding;
import com.example.laundrymanagermobile.ui.main.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ServiceFragment extends Fragment {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    TextView textViewManagerName, textViewManagerId, dialog_close_add;
    EditText editTextServiceId, editTextItemId, editTextCategoryId, editTextServiceName, editTextServicePrice, editTextServiceDesc, editTextStoreID;
    int store_id, service_id;
    FloatingActionButton floatingActionButton;
    ProgressBar progressBar;
    ListView listView;
    Spinner spinnerItem, spinnerCategories;
    Dialog dialog;
    List<com.example.laundrymanagermobile.apiconnection.Service> serviceList;

    private ServiceViewModel serviceViewModel;
    private FragmentServiceListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_list, container, false);

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_service);

        editTextServiceId = dialog.findViewById(R.id.editTextAddServiceId);
        editTextServiceName = dialog.findViewById(R.id.editTextAddServiceName);
        editTextServicePrice = dialog.findViewById(R.id.editTextAddServicePrice);
        editTextServiceDesc = dialog.findViewById(R.id.editTextAddServiceDesc);
        editTextItemId = dialog.findViewById(R.id.editTextAddServiceItemId);
        spinnerItem = dialog.findViewById(R.id.spinnerAddServiceItems);
        editTextCategoryId = dialog.findViewById(R.id.editTextAddServiceCategoryId);
        spinnerCategories = dialog.findViewById(R.id.spinnerAddServiceCategories);
        dialog_close_add = dialog.findViewById(R.id.textViewDialogCloseButton);
        textViewManagerId = view.findViewById(R.id.textViewId);
        textViewManagerName = view.findViewById(R.id.textViewNameOfStore);
        floatingActionButton = view.findViewById(R.id.fab2);

        //getting the current user
        Manager manager = SharedPrefManager.getInstance(getContext()).getUser();

        //setting the values to the textviews
        textViewManagerId.setText(String.valueOf(manager.getId()));
        textViewManagerName.setText(manager.getStore_name());

        /*productListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);

        binding = FragmentProductListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textProductList;
        final ListView listView = binding.listOfProductItem;

        productListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;*/

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        listView = (ListView) view.findViewById(R.id.listOfServiceItem);

        serviceList = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupActivity();
            }
        });

        dialog_close_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        store_id = Integer.parseInt(textViewManagerId.getText().toString());
        readServiceFromID(store_id);

        //readServices();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void myFancyMethod(View v){

    }

    private void createService() {
        //editTextServiceName give a null and also the editTextAddServiceName

        String servicename = editTextServiceName.getText().toString().trim();
        String serviceprice = editTextServicePrice.getText().toString().trim();
        String servicedesc = editTextServiceDesc.getText().toString().trim();

        String item = spinnerItem.getSelectedItem().toString();
        String category = spinnerCategories.getSelectedItem().toString();

        if (TextUtils.isEmpty(servicename)) {
            editTextServiceName.setError("Please enter service name");
            editTextServiceName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(serviceprice)) {
            editTextServicePrice.setError("Please enter service price");
            editTextServicePrice.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("item_name_type", item);
        params.put("category_name_type", category);
        params.put("services_name", servicename);
        params.put("services_price", serviceprice);
        params.put("services_desc", servicedesc);
        params.put("store_id", String.valueOf(store_id));

        com.example.laundrymanagermobile.ui.services.ServiceFragment.PerformNetworkRequest request = new com.example.laundrymanagermobile.ui.services.ServiceFragment.PerformNetworkRequest(Api.URL_CREATE_SERVICE_TO_ID, params, CODE_POST_REQUEST);
        request.execute();

        dialog.cancel();
    }

    private void readServiceFromID(int storeId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("store_id", String.valueOf(storeId));

        com.example.laundrymanagermobile.ui.services.ServiceFragment.PerformNetworkRequest request = new com.example.laundrymanagermobile.ui.services.ServiceFragment.PerformNetworkRequest(Api.URL_READ_SERVICE_TO_ID, params, CODE_POST_REQUEST);
        request.execute();
    }

    /*private void getServiceFrom() {
        //String store_id = textViewId.getText().toString().trim();
        int store_id = textViewId.getId();

        HashMap<String, String> params = new HashMap<>();
        params.put("store_id", String.valueOf(store_id));

        com.example.laundryshopmanagercapstone.ui.servicesList.ServiceListFragment.PerformNetworkRequest request = new com.example.laundryshopmanagercapstone.ui.servicesList.ServiceListFragment.PerformNetworkRequest(Api.URL_READ_SERVICE_TO_ID, params, CODE_POST_REQUEST);
        request.execute();
    }
*/
    private void readServices() {
        com.example.laundrymanagermobile.ui.services.ServiceFragment.PerformNetworkRequest request = new com.example.laundrymanagermobile.ui.services.ServiceFragment.PerformNetworkRequest(Api.URL_READ_SERVICE, null, CODE_GET_REQUEST);
        request.execute();
    }

    /*private void updateHero() {
        String id = editTextHeroId.getText().toString();
        String name = editTextName.getText().toString().trim();
        String realname = editTextRealname.getText().toString().trim();

        int rating = (int) ratingBar.getRating();

        String team = spinnerTeam.getSelectedItem().toString();


        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(realname)) {
            editTextRealname.setError("Please enter real name");
            editTextRealname.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("realname", realname);
        params.put("rating", String.valueOf(rating));
        params.put("teamaffiliation", team);


        com.example.laundryappcapstone.MainActivity.PerformNetworkRequest request = new com.example.laundryappcapstone.MainActivity.PerformNetworkRequest(com.example.laundryappcapstone.heroapiconnection.Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        editTextName.setText("");
        editTextRealname.setText("");
        ratingBar.setRating(0);
        spinnerTeam.setSelection(0);

        isUpdating = false;
    }

    private void deleteHero(int id) {
        com.example.laundryappcapstone.MainActivity.PerformNetworkRequest request = new com.example.laundryappcapstone.MainActivity.PerformNetworkRequest(com.example.laundryappcapstone.heroapiconnection.Api.URL_DELETE_HERO + id, null, CODE_GET_REQUEST);
        request.execute();
    }*/

    private void refreshServiceList(JSONArray services) throws JSONException {
        serviceList.clear();

        for (int i = 0; i < services.length(); i++) {
            JSONObject obj = services.getJSONObject(i);

            serviceList.add(new com.example.laundrymanagermobile.apiconnection.Service(
                    obj.getInt("id"),
                    obj.getString("services_name"),
                    obj.getString("services_price"),
                    obj.getString("services_desc"),
                    obj.getString("item_name_type"),
                    obj.getString("category_name_type"),
                    obj.getString("store_name")
            ));
        }

        com.example.laundrymanagermobile.ui.services.ServiceFragment.ServiceAdapter adapter = new com.example.laundrymanagermobile.ui.services.ServiceFragment.ServiceAdapter(serviceList);
        listView.setAdapter(adapter);
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshServiceList(object.getJSONArray("services"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            com.example.laundrymanagermobile.apiconnection.RequestHandler requestHandler = new com.example.laundrymanagermobile.apiconnection.RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    class ServiceAdapter extends ArrayAdapter<com.example.laundrymanagermobile.apiconnection.Service> {
        List<com.example.laundrymanagermobile.apiconnection.Service> serviceList;

        public ServiceAdapter(List<com.example.laundrymanagermobile.apiconnection.Service> serviceList) {
            super(listView.getContext(), R.layout.layout_service_item_list, serviceList);
            this.serviceList = serviceList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_service_item_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewServiceName);
            TextView textViewServicesDesc = listViewItem.findViewById(R.id.textViewServiceItemDesc);
            TextView textViewServicePrice = listViewItem.findViewById(R.id.textViewServiceItemCost);


            final com.example.laundrymanagermobile.apiconnection.Service service = serviceList.get(position);

            textViewName.setText(service.getServices_name());
            textViewServicesDesc.setText(service.getServices_desc());
            textViewServicePrice.setText(service.getServices_price());
            //add more here to make a visual of the variable name
            return listViewItem;
        }
    }

    public void popupActivity(){
        Button dialog_btn = dialog.findViewById(R.id.buttonAddService);
        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createService();
            }
        });
        dialog.show();

        //region DialogBox
        /*// Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(getContext());

        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit ?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                getActivity().finish();
                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();*/
        //endregion
    }
}