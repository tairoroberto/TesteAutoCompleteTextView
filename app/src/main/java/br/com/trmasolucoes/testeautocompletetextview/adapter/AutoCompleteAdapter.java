package br.com.trmasolucoes.testeautocompletetextview.adapter;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.trmasolucoes.testeautocompletetextview.MainActivity;
import br.com.trmasolucoes.testeautocompletetextview.R;
import br.com.trmasolucoes.testeautocompletetextview.connection.HttpConnection;
import br.com.trmasolucoes.testeautocompletetextview.domain.State;

public class AutoCompleteAdapter extends ArrayAdapter<State> implements Filterable {
	private Context context;
	private List<State> listFull;
	private List<State> listAux;
	private Filter filter;
	private LayoutInflater inflater;
	private int country;
	
	public AutoCompleteAdapter(Context context, int country, List<State> listFull) {
		super(context, 0, 0, listFull);
		this.context = context;
		this.country = country;
		this.listFull = listFull;
		this.listAux = new ArrayList<State>();
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	@Override
	public int getCount(){
		return(listAux.size());
	}
	
	
	@Override
	public State getItem(int position){
		return(listAux.get(position));
	}
	
	
	@Override
	public View getView(int position, View view, ViewGroup root){
		ViewHolder holder;
		
		if(view == null){
			view = inflater.inflate(R.layout.auto_complete_item, null);
			holder = new ViewHolder();
			view.setTag(holder);
			
			holder.ivFlag = (ImageView) view.findViewById(R.id.ivFlag);
			holder.tvState = (TextView) view.findViewById(R.id.tvState);
		}
		else{
			holder = (ViewHolder) view.getTag();
		}
		
		holder.ivFlag.setImageResource(listAux.get(position).getImgResource());
		holder.tvState.setText(listAux.get(position).getName());
		
		return(view);
	}
	
	
	static class ViewHolder{
		ImageView ivFlag;
		TextView tvState;
	}
	
	
	// FILTER
		@Override
		public Filter getFilter(){
			if(filter == null){
				filter = new ArrayFilter();
			}
			return(filter);
		}
		
		private class ArrayFilter extends Filter{

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();
				String constraintString = (constraint+"").toLowerCase();
				
				if(constraint == null || constraint.length() == 0){
					List<State> list = new ArrayList<State>(listFull);
					results.count = list.size();
					results.values = list;
				}
				else{
					int qtdConstraint = constraintString.length();
					
					//((MainActivity) context).id = 1;
					//ArrayList<State> newValues = (ArrayList<State>) HttpConnection.getStateListWeb("http://trmasolucoes.com.br/teste-autocompletetextview", country, constraintString);
					
					ArrayList<State> newValues = new ArrayList<State>(listFull.size());
					for (int i = 0; i < listFull.size(); i++) {
						String item = listFull.get(i).getName();
						item = item.replaceAll("[ç]", "c");
						item = item.replaceAll("[áàãâ]", "a");
						item = item.replaceAll("[ÁÀÃÂ]", "A");
						item = item.replaceAll("[éèê]", "e");
						item = item.replaceAll("[ÈÉÊ]", "E");
						item = item.replaceAll("[íì]", "i");
						item = item.replaceAll("[Íì]", "I");
						item = item.replaceAll("[óòôõ]", "o");
						item = item.replaceAll("[ÓÒÔÕ]", "O");
						item = item.replaceAll("[úù]", "u");
						item = item.replaceAll("[ÚÙ]", "U");
						item = item.toLowerCase();

						if(item.substring(0, qtdConstraint).equalsIgnoreCase(constraintString)
							|| listFull.get(i).getName().toLowerCase().substring(0, qtdConstraint).equalsIgnoreCase(constraintString)){
							newValues.add(listFull.get(i));
						}
					}
					results.count = newValues.size();
					results.values = newValues;
				}
				
				return(results);
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				if(results.values != null){ // && ((MainActivity) context).id > 0){
					listAux = (ArrayList<State>) results.values;
				}
				else{
					listAux = new ArrayList<State>();
				}
				
				if(results.count == 0){
					notifyDataSetInvalidated();
				}
				else{
					notifyDataSetChanged();
				}
			}
			
		}
}
