package skaipal.example.com.tracker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import skaipal.example.com.tracker.R;
import skaipal.example.com.tracker.model.EmployeeTaskDetails;

/**
 * Created by HP on 10-07-2018.
 */

public class EmployeeTaskAdapter extends RecyclerView.Adapter<EmployeeTaskAdapter.EmployeeTaskViewHolder>{

    private List<EmployeeTaskDetails> employeeTaskDetailsList;


    public EmployeeTaskAdapter(List<EmployeeTaskDetails> employeeTaskDetailsList) {
        this.employeeTaskDetailsList = employeeTaskDetailsList;
    }

    @Override
    public EmployeeTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.employeetaskrows,parent,false);
        EmployeeTaskViewHolder employeeTaskViewHolder=new EmployeeTaskViewHolder(view);
        return employeeTaskViewHolder;
    }

    @Override
    public void onBindViewHolder(EmployeeTaskViewHolder holder, int position) {
        EmployeeTaskDetails employeeTaskDetails=employeeTaskDetailsList.get(position);
        holder.tvempid.setText(employeeTaskDetails.getEmpId());
        holder.tvtask.setText(employeeTaskDetails.getTask());
        holder.tvproject.setText(employeeTaskDetails.getProject());
        holder.tvfromtime.setText(employeeTaskDetails.getFromtime());
        holder.tvtotime.setText(employeeTaskDetails.getTotime());


    }

    @Override
    public int getItemCount() {
        return employeeTaskDetailsList.size();
    }

    public class EmployeeTaskViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvempid,tvtask,tvfromtime,tvtotime,tvproject;
        public EmployeeTaskViewHolder(View itemView) {
            super(itemView);
            tvempid=(TextView)itemView.findViewById(R.id.empid);
            tvtask=(TextView)itemView.findViewById(R.id.task);
            tvproject=(TextView)itemView.findViewById(R.id.project);
            tvfromtime=(TextView)itemView.findViewById(R.id.fromtime);
            tvtotime=(TextView)itemView.findViewById(R.id.totime);

        }
    }
}
