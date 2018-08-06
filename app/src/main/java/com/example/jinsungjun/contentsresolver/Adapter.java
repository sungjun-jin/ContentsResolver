package com.example.jinsungjun.contentsresolver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    //1. 사용할 데이터를 정의
    List<Contacts> data;

    public void setData(List<Contacts> data) {
        //1.1 데이터를 세팅해주는 함수
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        //3. 뷰 홀더 생성
        // 레이아웃 파일을 객체로 변환

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        //4. 데이터를 화면에 세팅
        //4.1 데이터를 꺼낸다
        holder.contacts = data.get(position);
        holder.setData();

    }

    @Override
    public int getItemCount() {
        //2. List의 크기 반환
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView textID,textName,textNum;
        Button btnCall;

        Contacts contacts = new Contacts();

        public Holder(final View itemView) {

            super(itemView);

            textID = itemView.findViewById(R.id.textID);
            textName = itemView.findViewById(R.id.textName);
            textNum = itemView.findViewById(R.id.textNum);
            btnCall = itemView.findViewById(R.id.btnCall);

            btnCall.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(Intent.ACTION_CALL);
                    //tel:과 조합한 후 Uri 파싱 진행
                    intent.setData(Uri.parse("tel:"+contacts.number));
                    //View에서 Context를 꺼낼 수 있다.
                    view.getContext().startActivity(intent);
                }
            });

        }

        public void setData() {

            setTextID();
            setTextName();
            setTextNum();
        }

        public void setTextID() {

            textID.setText(contacts.id);
        }

        public void setTextName() {

            textName.setText(contacts.name);
        }

        public void setTextNum() {

            textNum.setText(contacts.number);
        }
    }
}
