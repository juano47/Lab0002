package padula.delaiglesia.dam.isi.frsf.lab0002;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Utils.ElementoMenu> miAdaptador;

    private Utils utils;
    private ListView miLista;
    private Utils.ElementoMenu[] elementos;
    private ArrayList<Utils.ElementoMenu> pedido;
    private Utils.ElementoMenu elementoSeleccionado;
    private int idRadioButtonSeleccionado;
    private Button buttonAgregar;
    private boolean pedidoConfirmado = false;
    private TextView txtPedido;
    private Button buttonConfirmarPedido;
    private Button buttonReiniciar;
    private String[] horarios = new String[] {"20.00", "20.30", "21.00", "21.30", "22.00"};
    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedido = new ArrayList<Utils.ElementoMenu>();
        txtPedido = (TextView) findViewById(R.id.txtPedido);
        buttonConfirmarPedido = (Button) findViewById(R.id.buttonConfirmarPedido);
        buttonReiniciar = (Button) findViewById(R.id.buttonReiniciar);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,horarios);
        spinner.setAdapter(spinnerAdapter);

        miLista = (ListView) findViewById(R.id.lista);
        buttonAgregar = (Button)findViewById(R.id.buttonAgregar);
        utils= new Utils();
        utils.iniciarListas();

        elementos= utils.getListaPlatos();
        ArrayList<Utils.ElementoMenu> lst = new ArrayList<Utils.ElementoMenu>(Arrays.asList(elementos));

        miAdaptador= new ArrayAdapter<Utils.ElementoMenu>(
                this,
                android.R.layout.simple_list_item_single_choice,
                lst);


        miLista.setAdapter( miAdaptador );

        RadioGroup radioGrupo = (RadioGroup)findViewById(R.id.visualRadioGrupo);
        idRadioButtonSeleccionado = radioGrupo.getCheckedRadioButtonId();
        radioGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int idRadioButtonSeleccionado) {
                switch (idRadioButtonSeleccionado){
                    case R.id.radioButtonPlato:
                            elementos = utils.getListaPlatos();
                            resetAdapterDataSet(elementos);

                        break;
                    case R.id.radioButtonBebida:

                        elementos= utils.getListaBebidas();
                        resetAdapterDataSet(elementos);
                        break;
                    case R.id.radioButtonPostre:

                        elementos= utils.getListaPostre();
                        resetAdapterDataSet(elementos);
                        break;
                }


            }
        });



        buttonConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidoConfirmado = true;
            }
        });

        buttonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciarPedido();
            }
        });

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(elementoSeleccionado != null && !pedidoConfirmado ){
                    pedido.add(elementoSeleccionado);
                    String prevText = (String) txtPedido.getText();

                    txtPedido.setText(prevText + "\n" +  elementoSeleccionado.toString());

                }
                else if (!pedidoConfirmado){
                    Toast.makeText(getApplicationContext(),"Debe seleccionar algo del menú", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"El pedido ya ha sido confirmado", Toast.LENGTH_LONG).show();
                }
            }
        });

        miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    elementoSeleccionado = (Utils.ElementoMenu)miLista.getItemAtPosition(i);
                    String itemText = elementoSeleccionado.toString();

            }
        });






    }

    private  void reiniciarPedido(){
        clearListViewSelection();
        pedido.clear();
        txtPedido.setText("");
    }

    private void clearListViewSelection(){
        elementoSeleccionado = null;
        miLista.clearChoices();
        miAdaptador.notifyDataSetChanged();
    }
    private void resetAdapterDataSet(Utils.ElementoMenu[] newDataSet) {

        miAdaptador.clear();
        clearListViewSelection();
        ArrayList<Utils.ElementoMenu> lst = new ArrayList<Utils.ElementoMenu>(Arrays.asList(newDataSet));
        miAdaptador.addAll(newDataSet);
        miAdaptador.notifyDataSetChanged();
    }


}
