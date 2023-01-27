package ftmk.bitp3453.helloclass;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Vector;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder>
{
    private final LayoutInflater layoutInflater;
    private final List<Student> students;

    public StudentAdapter(LayoutInflater layoutInflater, List<Student> students){
        this.layoutInflater = layoutInflater;
        this.students = students;
    }

    /*public StudentAdapter(LayoutInflater layoutInflater, List<Student> students, LayoutInflater layoutInflater1, Vector<Student> students1) {
        this.layoutInflater = layoutInflater1;
        this.students = students1;
    }

    public StudentAdapter(LayoutInflater layoutInflater, Vector<Student> students, LayoutInflater layoutInflater1) {
        this.layoutInflater = layoutInflater1;
        this.students = null;
    }*/

    public StudentViewHolder onCreateViewHolder( ViewGroup parent, int viewType){
        return new StudentViewHolder(layoutInflater.inflate(R.layout.item_student,parent,false));

    }

    public void onBindViewHolder(StudentViewHolder holder, int position){
        holder.setStudent(students.get(position));
    }

    public int getItemCount(){
        return students.size();
    }
}