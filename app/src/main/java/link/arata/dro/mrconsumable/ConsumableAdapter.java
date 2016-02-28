package link.arata.dro.mrconsumable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import link.arata.dro.mrconsumable.entity.Consumable;

public class ConsumableAdapter extends ArrayAdapter<Consumable> {
    private LayoutInflater layoutInflater;

    public ConsumableAdapter(Context context, int resource, List<Consumable> objects) {
        super(context, resource, objects);
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.consumable_row, parent, false);
        }

        Consumable consumable = (Consumable) getItem(position);

        AppCompatTextView consumableName =
            (AppCompatTextView) convertView.findViewById(R.id.consumableName);
        consumableName.setText(consumable.getConsumableName());

        AppCompatTextView consumableDate =
            (AppCompatTextView) convertView.findViewById(R.id.consumableDate);
        consumableDate.setText(consumable.getConsumableDate());

        AppCompatTextView consumablePrice =
            (AppCompatTextView) convertView.findViewById(R.id.consumablePrice);
        consumablePrice.setText(consumable.getConsumablePrice() + "");

        return convertView;
    }
}
