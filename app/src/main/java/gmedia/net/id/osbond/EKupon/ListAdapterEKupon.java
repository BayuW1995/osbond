package gmedia.net.id.osbond.EKupon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gmedia.net.id.osbond.ConvertDate;
import gmedia.net.id.osbond.QRCode;
import gmedia.net.id.osbond.R;

public class ListAdapterEKupon extends ArrayAdapter {
    private Context context;
    private List<CustomListAdapterEKupon> listEKupon;

    public ListAdapterEKupon(Context context, List<CustomListAdapterEKupon> listEKupon) {
        super(context, R.layout.listview_kupon, listEKupon);
        this.context = context;
        this.listEKupon = listEKupon;
    }

    private static class ViewHolder {
        private String idCabang, jenis;
        private TextView kupon, lokasiKupon, dueDateKuponBaru, timeScankuponBaru, cabangKuponBaru;
        private RelativeLayout kuponBiasa, kuponBaru;
        private LinearLayout backgroundKuponBiasa;
    }

    public void addMoreData(List<CustomListAdapterEKupon> moreData) {

        listEKupon.addAll(moreData);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        convertView = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.listview_kupon, null);
            holder.kuponBiasa = (RelativeLayout) convertView.findViewById(R.id.kuponBiasa);
            holder.backgroundKuponBiasa = (LinearLayout) convertView.findViewById(R.id.backgroundKuponBiasa);
            holder.kupon = (TextView) convertView.findViewById(R.id.kupon);
            holder.lokasiKupon = (TextView) convertView.findViewById(R.id.lokasiKupon);
            holder.kuponBaru = (RelativeLayout) convertView.findViewById(R.id.kuponBaru);
            holder.dueDateKuponBaru = (TextView) convertView.findViewById(R.id.dueDateKuponBaru);
            holder.timeScankuponBaru = (TextView) convertView.findViewById(R.id.timeScanKuponBaru);
            holder.cabangKuponBaru = (TextView) convertView.findViewById(R.id.cabangKuponBaru);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CustomListAdapterEKupon customHistory = listEKupon.get(position);
        holder.idCabang = customHistory.getIdCabang();
        holder.kupon.setText(customHistory.getKupon());
        holder.lokasiKupon.setText(customHistory.getCabang());
        holder.jenis = customHistory.getJenis();
        if (holder.jenis.equals("1")) {
            if (holder.idCabang.equals("0")) {
                holder.kuponBiasa.setVisibility(View.VISIBLE);
                holder.backgroundKuponBiasa.setBackgroundResource(R.drawable.kupon_gold);
//                holder.kuponBaru.setVisibility(View.GONE);
            } else {
                holder.kuponBiasa.setVisibility(View.VISIBLE);
                holder.backgroundKuponBiasa.setBackgroundResource(R.drawable.kupon_silver);
//                holder.kuponBaru.setVisibility(View.GONE);
            }
        } else if (holder.jenis.equals("2")) {
//            holder.kuponBiasa.setVisibility(View.GONE);
            holder.kuponBaru.setVisibility(View.VISIBLE);
            holder.dueDateKuponBaru.setText(ConvertDate.convert("yyyy-MM-dd HH:mm:ss","dd MMM yyyy",customHistory.getDue_date()));
            holder.timeScankuponBaru.setText(customHistory.getJumlah_scan());
            holder.cabangKuponBaru.setText(customHistory.getCabang());
        }
        holder.kuponBiasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = customHistory.getId();
                Intent intent = new Intent(context, QRCode.class);
                intent.putExtra("id", id);
                ((Activity) context).startActivity(intent);
//                Toast.makeText(context,id,Toast.LENGTH_LONG).show();
            }
        });
        holder.kuponBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idBaru = customHistory.getId();
                Intent intent = new Intent(context, QRCode.class);
                intent.putExtra("id", idBaru);
                ((Activity) context).startActivity(intent);
//                Toast.makeText(context,idBaru,Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
