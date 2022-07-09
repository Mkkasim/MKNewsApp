package com.mk.newsapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mk.newsapp.LoginActivity;
import com.mk.newsapp.MainActivity;
import com.mk.newsapp.R;
import com.mk.newsapp.databinding.FragmentSignUpBinding;
import com.mk.newsapp.models.SignUpUser;
import com.mk.newsapp.sessionmanager.SessionManagement;


public class SignUpFragment extends Fragment {

    FragmentSignUpBinding binding;

    float v=0;

    FirebaseAuth auth;
    DatabaseReference ref;



    public SignUpFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(getLayoutInflater(),container,false);

        auth = FirebaseAuth.getInstance();
        SessionManagement.init(requireContext());

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.nameEdt.setTranslationX(800);
        binding.emailEdt.setTranslationX(800);
        binding.passEdt.setTranslationX(800);
        binding.btnSignup.setTranslationX(800);

        binding.nameEdt.setAlpha(v);
        binding.emailEdt.setAlpha(v);
        binding.passEdt.setAlpha(v);
        binding.btnSignup.setAlpha(v);

        binding.nameEdt.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        binding.emailEdt.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        binding.passEdt.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        binding.btnSignup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        binding.btnSignup.setOnClickListener(view1 -> {
            signUpUser();
        });

    }

    private void signUpUser() {

        String name = binding.nameEdt.getText().toString();
        String email = binding.emailEdt.getText().toString();
        String phone = binding.phoneEdt.getText().toString();
        String pass = binding.passEdt.getText().toString();


        if (name.isEmpty() || email.isEmpty()|| phone.isEmpty()|| pass.isEmpty()){
            Toast.makeText(getContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
        }else {

            ref = FirebaseDatabase.getInstance().getReference("Users");
            String refMail = email.substring(0, email.length() - 10);
            String resultMail = refMail.replaceAll("[-+.^:@,]","");
            String refPass = pass.substring(0,4);
            String resultPass = refPass.replaceAll("[-+.^:@,]","");
            Log.d("TAG2", "signUpUser: "+refMail);
            Log.d("TAG2", "signUpUser: "+refPass);
            Log.d("TAG2", "signUpUser: "+resultMail);
            Log.d("TAG2", "signUpUser: "+resultPass);

            try {
                ref.child(resultMail+resultPass).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.child("email").getValue().equals(email)){
                            Toast.makeText(getContext(), "User Already Registered. Please Login.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }catch (Exception e){
                Log.d("TAG2", "signUpUser: "+e.getMessage());
            }


            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                SignUpUser signUpUser = new SignUpUser(name,email,phone,pass);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(resultMail+resultPass)
                                        .setValue(signUpUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(getContext(), "SignUp Successful. Please Login", Toast.LENGTH_SHORT).show();
                                                    SessionManagement.saveUserLoginID(resultMail+resultPass);
                                                    Intent in = new Intent(getContext(), LoginActivity.class);
                                                    startActivity(in);
                                                    requireActivity().finish();

                                                }else {
                                                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                            }else {
                                Toast.makeText(getContext(), "Please Try Again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}