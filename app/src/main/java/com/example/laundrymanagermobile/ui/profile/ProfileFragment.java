package com.example.laundrymanagermobile.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.laundrymanagermobile.LoginActivity;
import com.example.laundrymanagermobile.R;
import com.example.laundrymanagermobile.apiconnection.Manager;
import com.example.laundrymanagermobile.databinding.FragmentProfileBinding;
import com.example.laundrymanagermobile.ui.main.SharedPrefManager;

public class ProfileFragment extends Fragment {
    TextView textViewUsername, textViewEmailAddress, textViewName, textViewOwner, textViewContactNumber, textViewAddress;

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        if (!SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
            //finish();
            getActivity().finish();
            startActivity(new Intent(getContext(), LoginActivity.class));
        }

        textViewUsername = view.findViewById(R.id.textViewProfileStoreUsername);
        textViewEmailAddress = view.findViewById(R.id.textViewProfileStoreEmail);
        textViewName = view.findViewById(R.id.textViewProfileStoreName);
        textViewOwner = view.findViewById(R.id.textViewProfileStoreOwner);
        textViewContactNumber = view.findViewById(R.id.textViewProfileContactNumber);
        textViewAddress = view.findViewById(R.id.textViewProfileStoreAddress);

        //getting the current user
        Manager manager = SharedPrefManager.getInstance(getContext()).getUser();

        //setting the values to the textviews
        textViewUsername.setText(String.valueOf(manager.getStore_username()));
        textViewEmailAddress.setText(manager.getStore_email_address());
        textViewName.setText(manager.getStore_name());
        textViewOwner.setText(String.valueOf(manager.getStore_owner()));
        textViewContactNumber.setText(manager.getStore_contact_number());
        textViewAddress.setText(manager.getStore_address());

        //when the user presses logout button
        //calling the logout method
        view.findViewById(R.id.buttonProfileSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                getActivity().finish();
                SharedPrefManager.getInstance(getContext().getApplicationContext()).logout();
            }
        });

        /*profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
*/
        /*binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProfile;
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        //return root;
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}