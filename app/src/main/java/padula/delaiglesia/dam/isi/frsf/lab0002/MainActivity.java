package padula.delaiglesia.dam.isi.frsf.lab0002;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Utils.ElementoMenu> miAdaptador;

    private Utils utils;
    private ListView miLista;
    private Utils.ElementoMenu[] elementos;
    private int idRadioButtonSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miLista = (ListView) findViewById(R.id.lista);
        utils= new Utils();
        utils.iniciarListas();

        elementos= utils.getListaPlatos();

        miAdaptador= new ArrayAdapter<Utils.ElementoMenu>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                elementos);


        miLista.setAdapter( miAdaptador );

        RadioGroup radioGrupo = (RadioGroup)findViewById(R.id.visualRadioGrupo);
        idRadioButtonSeleccionado = radioGrupo.getCheckedRadioButtonId();
        radioGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int idRadioButtonSeleccionado) {
                switch (idRadioButtonSeleccionado){
                    case R.id.radioButtonPlato:

                        elementos= utils.getListaPlatos();
                        //miAdaptador.clear();
                       // miAdaptador.addAll(elementos);
                        

                        break;
                    case R.id.radioButtonBebida:

                        elementos= utils.getListaBebidas();

                       // miAdaptador.clear();
                        break;
                    case R.id.radioButtonPostre:

                        elementos= utils.getListaPostre();
                       // miAdaptador.clear();
                        //miAdaptador.addAll(elementos);
                        miAdaptador.notifyDataSetChanged();
                        break;
                }


            }
        });






    }
}
