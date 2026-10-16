package com.example.estudo_firebaseconnection;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        usuario.createUserWithEmailAndPassword(
                "Eduardo@gmail.com", "Edu123456")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("CreateUser", "Sucesso ao cadastrar!");
                        } else {
                            Log.i("CreateUser", "Erro ao cadastrar");
                        }

                        if (usuario.getCurrentUser() != null) {
                            Log.i("CurrenUser", "Usuario logado!");
                        } else {
                            Log.i("CurrentUser", "Usuario não logado");
                        }
                    }
                });

        DatabaseReference usuarios = referencia.child("usuarios");
        DatabaseReference produtos = referencia.child("produtos");

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("FIREBASE", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Usuario usuario = new Usuario();
        usuario.setNome("Joaozinho");
        usuario.setSobrenome("pereira");
        usuario.setIdade(10);

        Produto produto = new Produto();
        produto.setDescricao("Chuteira");
        produto.setMarca("Adidas");
        produto.setPreco(100);

        usuarios.child("001").setValue(usuario);
        produtos.child("001").setValue(produto);

        usuarios.child("001").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                Log.i("FIREBASE", usuario.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        usuarios.child("001").child("nome").setValue("Joaozinho atualizado");

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("joaozinho atualizado novamente");
        usuarioAtualizado.setSobrenome("pereira segundo");
        usuarioAtualizado.setIdade(12);

        usuarios.child("001").setValue(usuarioAtualizado);

        usuarios.child("001").removeValue();
    }
}