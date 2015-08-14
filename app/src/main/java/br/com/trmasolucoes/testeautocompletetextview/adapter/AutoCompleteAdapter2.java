package br.com.trmasolucoes.testeautocompletetextview.adapter;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

import br.com.trmasolucoes.testeautocompletetextview.domain.State;

public class AutoCompleteAdapter2 extends ArrayAdapter<State> implements Filterable {
	public AutoCompleteAdapter2(Context context, int resourceLayout, int resourceTextView, List<State> listFull) {
		super(context, resourceLayout, resourceTextView, listFull);
	}
}
