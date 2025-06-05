package com.fabriciocurvello.apparmazenamentocacheanimauna;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText etMensagem;
    private Button btSalvar;
    private Button btExibir;
    private TextView tvExibeMensagem;

    private static final String FILE_NAME = "temp_message_cache.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etMensagem = findViewById(R.id.et_mensagem);
        btSalvar = findViewById(R.id.bt_salvar);
        btExibir = findViewById(R.id.bt_exibir);
        tvExibeMensagem = findViewById(R.id.tv_exibe_mensagem);
        
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarMensagemCache(etMensagem.getText().toString());
                etMensagem.setText("");
            }
        });
        
        btExibir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvExibeMensagem.setText(leiaMensagemCache());
            }
        });
    } // fim do onCreate()

    private void salvarMensagemCache(String mensagem) {
        File cacheFile = new File(getCacheDir(), FILE_NAME);//Cria o arquivo que vai salvar os dados.
        try (FileOutputStream fos = new FileOutputStream(cacheFile)) {
            fos.write(mensagem.getBytes()); // Salva os dados no arquivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String leiaMensagemCache() {
        File cacheFile = new File(getCacheDir(), FILE_NAME); //Acesso ao arquivo com os dados
        StringBuilder mensagem = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(cacheFile)) {
            int ch;
            while ((ch = fis.read()) != -1) { // leitura dos dados do arquivo
                mensagem.append((char) ch); // atribuição dos caracteres lidos ao objeto mensagem
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mensagem.toString(); // retorna todos os dados lidos convertidos para String.
    }
}