package com.example.listadetarefas;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura o botão para mostrar um AlertDialog
        Button listButton = findViewById(R.id.addTaskButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        // Configura o botão para verificar a saída de áudio
        Button checkAudioButton = findViewById(R.id.button_check_audio);
        checkAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAudioOutput();
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Ação do Botão");
        builder.setMessage("Botão acionado!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ação ao clicar no botão OK, se necessário
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkAudioOutput() {
        AudioHelper audioHelper = new AudioHelper(this);

        boolean isSpeakerAvailable = audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BUILTIN_SPEAKER);
        boolean isBluetoothHeadsetConnected = audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP);

        // Mostra um Toast com os resultados
        Toast.makeText(this, "Alto-falante disponível: " + isSpeakerAvailable, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Fone de ouvido Bluetooth conectado: " + isBluetoothHeadsetConnected, Toast.LENGTH_SHORT).show();
    }

    class AudioHelper {
        private final AudioManager audioManager;
        private Context context;

        AudioHelper(Context context) {
            this.context = context;
            this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        }

        boolean audioOutputAvailable(int type) {
            if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
                return false;
            }

            AudioDeviceInfo[] devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
            for (AudioDeviceInfo device : devices) {
                if (device.getType() == type) {
                    return true; // Dispositivo encontrado, retorna true
                }
            }
            return false; // Nenhum dispositivo correspondente encontrado, retorna false
        }
    }

}
