package com.mk.newsapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mk.newsapp.LoginActivity;
import com.mk.newsapp.MainActivity;
import com.mk.newsapp.databinding.FragmentLoginTabBinding;
import com.mk.newsapp.models.SignUpUser;
import com.mk.newsapp.sessionmanager.SessionManagement;

public class LoginTabFragment extends Fragment {

    FragmentLoginTabBinding binding;

    FirebaseAuth auth;
    DatabaseReference ref;

    String emailF,passF;
    float v=0;

    public LoginTabFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginTabBinding.inflate(getLayoutInflater(),container,false);

        SessionManagement.init(requireContext());
        if (SessionManagement.isLogged()){

            Intent in = new Intent(getContext(),MainActivity.class);
            startActivity(in);
            requireActivity().finish();

        }


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.emailEdt.setTranslationX(800);
        binding.passEdt.setTranslationX(800);
        binding.forget.setTranslationX(800);
        binding.btnLogin.setTranslationX(800);

        binding.emailEdt.setAlpha(v);
        binding.passEdt.setAlpha(v);
        binding.forget.setAlpha(v);
        binding.btnLogin.setAlpha(v);

        binding.emailEdt.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        binding.passEdt.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        binding.forget.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        binding.btnLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();




        binding.btnLogin.setOnClickListener(view1 -> {

            loginUser();

        });

        binding.forget.setOnClickListener(view1 -> {
            resetPass();
        });

    }

    private void resetPass() {

        EditText resetMail = new EditText(getContext());
        AlertDialog.Builder passResetDialog = new AlertDialog.Builder(getContext());
        passResetDialog.setTitle("Reset Password ?");
        passResetDialog.setMessage("Enter Your Email To Receive Reset Link.");
        passResetDialog.setView(resetMail);

        passResetDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            String mail = resetMail.getText().toString();

            if (mail.isEmpty()){
                Toast.makeText(getContext(), "Please Enter Email.", Toast.LENGTH_SHORT).show();
            }else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
                    .addOnSuccessListener(unused ->
                            Toast.makeText(getContext(), "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(getContext(), "Error ! Reset Link Is Not Sent "+e.getMessage(), Toast.LENGTH_SHORT).show());
            }

        }).setNegativeButton("No", (dialogInterface, i) -> {


        });

        passResetDialog.create().show();

    }


    private void loginUser() {

        String email = binding.emailEdt.getText().toString();
        String pass = binding.passEdt.getText().toString();

        if (email.isEmpty() || pass.isEmpty()){
            Toast.makeText(getContext(), "Please fill email & password", Toast.LENGTH_SHORT).show();
        }else {

             ref = FirebaseDatabase.getInstance().getReference("Users");

            //Log.d("TAG2", "onDataChange: "+auth.getUid());
//            Query query = ref.orderByChild("email").equalTo(email);
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()){
//
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });

            String refMail = email.substring(0, email.length() - 10);
            String resultMail = refMail.replaceAll("[-+.^:@,]","");
            String refPass = pass.substring(0,4);
            String resultPass = refPass.replaceAll("[-+.^:@,]","");
            Log.d("TAG2", "signUpUser: "+refMail);
            Log.d("TAG2", "signUpUser: "+refPass);

            try {

                ref.child(resultMail+resultPass).addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if (snapshot.exists() && snapshot.child("email").getValue().equals(email)){

                            passF = snapshot.child("pass").getValue().toString();
                            Log.d("TAG2", "onDataChange: "+passF);
                            // Log.d("TAG2", "onDataChange: "+auth.getUid());

                            if (passF.equals(pass)){
                                Toast.makeText(getContext(), "Succesfully Logged in.", Toast.LENGTH_SHORT).show();
                                SessionManagement.setLogin(true);
                                Intent in = new Intent(getContext(),MainActivity.class);
                                startActivity(in);
                                requireActivity().finish();
                            }else {
                                Toast.makeText(getContext(), "Password is incorrect", Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(getContext(), "No records found! Please signup.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }catch (Exception e){

                Log.d("TAG2", "loginUser: "+e.getMessage());

            }


        }

    }
}